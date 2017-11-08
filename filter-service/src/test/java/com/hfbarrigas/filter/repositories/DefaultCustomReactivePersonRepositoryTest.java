package com.hfbarrigas.filter.repositories;

import com.hfbarrigas.filter.model.internal.Person;
import com.hfbarrigas.filter.utils.Constants;
import org.junit.*;
import org.mockito.*;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import reactor.core.publisher.Flux;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;
import static org.mockito.ArgumentMatchers.any;
import static org.springframework.data.mongodb.core.query.Query.query;

//@RunWith(SpringJUnit4ClassRunner.class)
public class DefaultCustomReactivePersonRepositoryTest {

    @Mock
    private ReactiveMongoTemplate reactiveMongoTemplate;

    @InjectMocks
    private DefaultCustomReactivePersonRepository defaultCustomReactivePersonRepository;

    @Before
    public void beforeEach() {
        MockitoAnnotations.initMocks(this);
    }

    @BeforeClass
    public static void beforeTestSuite() {
    }

    @After
    public void afterEach() {
    }

    @AfterClass
    public static void afterTestSuite() {
    }

    @Test
    public void successfullRetrievalOfPeople() {
        //prepare
        Person p = new Person();
        p.setDisplayName("Person1");
        Person p2 = new Person();
        p2.setDisplayName("Person2");

        List<Criteria> expectedCriterias = Stream.of(new Criteria(Constants.COMPATIBILITY_SCORE).gte(0.0f),
                new Criteria(Constants.COMPATIBILITY_SCORE).lte(1.0f),
                new Criteria(Constants.AGE).gte(18),
                new Criteria(Constants.AGE).lte(40),
                new Criteria(Constants.HEIGHT_IN_CM).gte(130),
                new Criteria(Constants.HEIGHT_IN_CM).lte(190))
                .collect(toList());

        Mockito.when(reactiveMongoTemplate.find(any(Query.class), any())).thenReturn(Flux.just(p, p2));

        //act
        Flux<Person> personList = defaultCustomReactivePersonRepository.findBy(null, null, null, 0.0f, 1.0f, 18, 40, 130, 190, null, null, null, null);

        //assert
        java.util.List<Person> list = personList
                .collectList()
                .block();

        Mockito.verify(reactiveMongoTemplate, Mockito.times(1)).find(query(new Criteria().andOperator(expectedCriterias.toArray(new Criteria[expectedCriterias.size()]))), Person.class);
        Assert.assertEquals(p, list.get(0));
        Assert.assertEquals(p2, list.get(1));

    }

    @Test(expected = NullPointerException.class)
    public void errorDueToNullParameters() {
        //prepare
        Person p = new Person();
        p.setDisplayName("Person1");
        Person p2 = new Person();
        p2.setDisplayName("Person2");
        Mockito.when(reactiveMongoTemplate.find(any(Query.class), any())).thenReturn(Flux.just(p, p2));

        //act
        defaultCustomReactivePersonRepository.findBy(null, null, null, null, null, 18, 40, 130, 190, null, null, null, null)
                .subscribe();

        //assert
        Mockito.verify(reactiveMongoTemplate, Mockito.times(0)).find(any(), any());
    }

}
