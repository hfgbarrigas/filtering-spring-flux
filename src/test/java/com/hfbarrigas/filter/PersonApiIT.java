package com.hfbarrigas.filter;

import com.hfbarrigas.filter.model.api.Person;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.assertj.core.util.Strings;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.Timeout;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.reactive.server.EntityExchangeResult;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import java.util.stream.Collectors;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@ActiveProfiles("it")
public class PersonApiIT {

    @Rule
    public Timeout timeout = Timeout.millis(2000);

    @Autowired
    private WebTestClient client;

    @Autowired
    private ObjectMapper objectMapper;

    private List<Person> dataset;

    @Before
    public void setup() {
        try {
            Map<String, List<Person>> data = objectMapper.readValue(PersonApiIT.class.getClassLoader().getResourceAsStream("database/data.json"), new TypeReference<Map<String, List<Person>>>() {
            });
            dataset = new ArrayList<>(data.get("matches"));
        } catch (IOException e) {
            throw new Error(e);
        }
    }

    @Test
    public void getUnfilteredMatches() {
        expectSuccess("", 25, result -> Assert.assertTrue(dataset.equals(result.getResponseBody())));
    }

    //favourite
    @Test
    public void getFavouriteMatches() {
        expectSuccess("favourite=true", 6, result -> Assert.assertTrue(result.getResponseBody().equals(
                dataset
                        .stream()
                        .filter(Person::getFavourite)
                        .collect(Collectors.toList())
        )));
    }

    @Test
    public void getNonFavouriteMatches() {
        expectSuccess("favourite=false", 19, result -> Assert.assertTrue(result.getResponseBody().equals(
                dataset
                        .stream()
                        .filter(dp -> !dp.getFavourite())
                        .collect(Collectors.toList())
        )));
    }

    @Test
    public void getFavouriteMatchesErrorInvalidInput() {
        this.client
                .get()
                .uri("/persons?favourite=x")
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus()
                .is5xxServerError();
    }

    //inContact
    @Test
    public void getInContactMatches() {
        expectSuccess("inContact=true", 12, result -> Assert.assertTrue(result.getResponseBody().equals(
                dataset
                        .stream()
                        .filter(pd -> pd.getContactsExchanged() != 0)
                        .collect(Collectors.toList())
        )));
    }

    @Test
    public void getNotInContactMatches() {
        expectSuccess("inContact=false", 13, result -> Assert.assertTrue(result.getResponseBody().equals(
                dataset
                        .stream()
                        .filter(pd -> pd.getContactsExchanged() == 0)
                        .collect(Collectors.toList())
        )));
    }

    //hasPhoto
    @Test
    public void geMatchesWithPhoto() {
        expectSuccess("hasPhoto=true", 22, result -> Assert.assertTrue(result.getResponseBody().equals(
                dataset
                        .stream()
                        .filter(pd -> !Strings.isNullOrEmpty(pd.getMainPhoto()))
                        .collect(Collectors.toList())
        )));
    }

    @Test
    public void geMatchesWithoutPhoto() {
        expectSuccess("hasPhoto=false", 3, result -> Assert.assertTrue(result.getResponseBody().equals(dataset.stream()
                .filter(pd -> Strings.isNullOrEmpty(pd.getMainPhoto()))
                .collect(Collectors.toList()))));
    }

    //compatibilityScore
    @Test
    public void geMatchesWithCompatibilityScoreWithinSpecifiedInterval() {
        expectSuccess("maxCompatibilityScore=0.9&minCompatibilityScore=0.5", 17,
                result -> Assert.assertTrue(result.getResponseBody().equals(
                        dataset
                                .stream()
                                .filter(pd -> 0.5F <= pd.getCompatibilityScore() && pd.getCompatibilityScore() <= 0.9F)
                                .collect(Collectors.toList()))));
    }

    @Test
    public void validateCompatibilityScoreRanges() {
        expectBadRequest("maxCompatibilityScore=-0.1");
        expectBadRequest("maxCompatibilityScore=1.1");
        expectBadRequest("minCompatibilityScore=-0.1");
        expectBadRequest("minCompatibilityScore=1.1");
        expectBadRequest("maxCompatibilityScore=0.5&minCompatibilityScore=0.8");
    }

    //age
    @Test
    public void validateAgeRanges() {
        expectBadRequest("maxAge=100");
        expectBadRequest("maxAge=15");
        expectBadRequest("minAge=99");
        expectBadRequest("minAge=17");
        expectBadRequest("maxAge=20&minAge=30");
    }

    @Test
    public void geMatchesWithAgeLessOrEqual() {
        expectSuccess("maxAge=28", 1, result -> Assert.assertTrue(result.getResponseBody().equals(
                dataset
                        .stream()
                        .filter(pd -> pd.getAge() <= 28)
                        .collect(Collectors.toList())
        )));
    }

    @Test
    public void geMatchesWithAgeHigherOrEqual() {
        expectSuccess("minAge=28", 24, result -> Assert.assertTrue(result.getResponseBody().equals(
                dataset
                        .stream()
                        .filter(pd -> pd.getAge() >= 28)
                        .collect(Collectors.toList())
        )));
    }

    @Test
    public void geMatchesWithAgeWithinSpecifiedInterval() {
        expectSuccess("maxAge=28&minAge=25", 1,
                result -> Assert.assertTrue(result.getResponseBody().equals(
                        dataset
                                .stream()
                                .filter(pd -> 25 <= pd.getAge() && pd.getAge() <= 28)
                                .collect(Collectors.toList()))));
    }

    //height
    @Test
    public void validateHeightRanges() {
        expectBadRequest("maxHeight=220");
        expectBadRequest("minHeight=220");
        expectBadRequest("maxHeight=100");
        expectBadRequest("minHeight=100");
        expectBadRequest("maxHeight=150&minHeight=180");
    }

    @Test
    public void geMatchesWithHeightWithinSpecifiedInterval() {
        expectSuccess("maxHeight=180&minHeight=150", 19,
                result -> Assert.assertTrue(result.getResponseBody().equals(
                        dataset
                                .stream()
                                .filter(pd -> 150 <= pd.getHeightInCm() && pd.getHeightInCm() <= 180)
                                .collect(Collectors.toList()))));
    }

    @Test
    public void geMatchesWithHeightLessOrEqual() {
        expectSuccess("maxHeight=180", 25, result -> Assert.assertTrue(result.getResponseBody().equals(
                dataset
                        .stream()
                        .filter(pd -> pd.getHeightInCm() <= 180)
                        .collect(Collectors.toList())
        )));
    }

    @Test
    public void geMatchesWithHeightHigherOrEqual() {
        expectSuccess("minHeight=160", 12, result -> Assert.assertTrue(result.getResponseBody().equals(
                dataset
                        .stream()
                        .filter(pd -> pd.getHeightInCm() >= 160)
                        .collect(Collectors.toList())
        )));
    }

    //distance
    @Test
    public void validateDistanceConstraints() {
        expectBadRequest("distance=-10");
        expectBadRequest("distance=10");
        expectBadRequest("distance=10&distanceUnit=X");
        expectBadRequest("distance=10&distanceUnit=km");
        expectBadRequest("distance=10&distanceUnit=km&lat=50.0123");
        expectBadRequest("distance=10&distanceUnit=km&lon=50.0123");
    }

    @Test
    public void getMatchesWithinSpecifiedDistance() {
        expectSuccess("distance=10&distanceUnit=km&lat=53.801277&lon=-1.548567", 2, result -> Assert.assertNotNull(result.getResponseBody()));
    }

    @Test
    public void getMatchesWithinDistanceAndAgeLessOrEqual() {
        expectSuccess("distance=10&distanceUnit=km&lat=53.801277&lon=-1.548567&maxAge=30", 0,
                result -> Assert.assertTrue(result.getResponseBody().equals(new ArrayList<>())));
    }

    public void expectBadRequest(String condition) {
        this.client
                .get()
                .uri("/persons?" + condition)
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus()
                .isBadRequest();
    }

    private void expectSuccess(String parameters, int size, Consumer<EntityExchangeResult<List<Person>>> consumer) {
        this.client
                .get()
                .uri("/persons?" + parameters)
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus()
                .isOk()
                .expectBodyList(Person.class)
                .consumeWith(consumer)
                .hasSize(size);
    }
}
