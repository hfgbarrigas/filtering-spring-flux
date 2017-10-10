package com.hfbarrigas.filter.api;

import com.hfbarrigas.filter.exceptions.InvalidRequestException;
import com.hfbarrigas.filter.logger.Loggable;
import com.hfbarrigas.filter.mapper.ApiMapper;
import com.hfbarrigas.filter.model.api.Person;
import com.hfbarrigas.filter.repositories.CustomReactivePersonRepository;
import com.hfbarrigas.filter.repositories.ReactivePersonRepository;
import com.hfbarrigas.filter.utils.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

import javax.annotation.Nullable;
import javax.validation.constraints.*;
import java.util.stream.Stream;

@RestController
@Validated
public class PersonApi implements Loggable {

    private ReactivePersonRepository personRepository;
    private CustomReactivePersonRepository customReactivePersonRepository;
    private ApiMapper apiMapper;

    private static final String DISTANCE_UNIT_REGEX = "(km|mi)";

    @Autowired
    public PersonApi(ReactivePersonRepository personRepository,
                     CustomReactivePersonRepository customReactivePersonRepository,
                     ApiMapper apiMapper) {
        this.personRepository = personRepository;
        this.customReactivePersonRepository = customReactivePersonRepository;
        this.apiMapper = apiMapper;
    }

    @GetMapping(value = "/persons", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseBody
    public Flux<Person> persons(@Nullable @RequestParam(name = "hasPhoto", required = false) Boolean withPhoto,
                                @Nullable @RequestParam(name = "inContact", required = false) Boolean inContact,
                                @Nullable @RequestParam(name = "favourite", required = false) Boolean favourite,
                                @Nullable @DecimalMin("0") @DecimalMax("1") @RequestParam(name = "minCompatibilityScore", required = false) Float minCompatibilityScore,
                                @Nullable @DecimalMin("0") @DecimalMax("1") @RequestParam(name = "maxCompatibilityScore", required = false) Float maxCompatibilityScore,
                                @Nullable @Min(18) @Max(95) @RequestParam(name = "minAge", required = false) Integer minAge,
                                @Nullable @Min(18) @Max(95) @RequestParam(name = "maxAge", required = false) Integer maxAge,
                                @Nullable @Min(135) @Max(210) @RequestParam(name = "minHeight", required = false) Integer minHeight,
                                @Nullable @Min(135) @Max(210) @RequestParam(name = "maxHeight", required = false) Integer maxHeight,
                                @Nullable @Min(0) @RequestParam(name = "distance", required = false) Float distance,
                                @Nullable @Pattern(regexp = DISTANCE_UNIT_REGEX) @RequestParam(name = "distanceUnit", required = false) String distanceUnit,
                                @Nullable @RequestParam(name = "lat", required = false) Double lat,
                                @Nullable @RequestParam(name = "lon", required = false) Double lon) {

        if (distance != null && (lat == null || lon == null || distanceUnit == null)) {
            throw new InvalidRequestException("Latitude/Longitude/DistanceUnit are required when requiring distance matches.");
        }

        validateRanges(Pair.of(minCompatibilityScore, maxCompatibilityScore), Pair.of(minAge, maxAge), Pair.of(minHeight, maxHeight));

        return customReactivePersonRepository.findBy(withPhoto, inContact, favourite, minCompatibilityScore, maxCompatibilityScore, minAge, maxAge,
                minHeight, maxHeight, distance, distanceUnit, lat, lon)
                .map(apiMapper::toApiPerson);
    }

    @GetMapping(value = "/persons/stream", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    @ResponseBody
    public Flux<Person> personsStream(@Nullable @RequestParam(name = "hasPhoto", required = false) Boolean withPhoto,
                                @Nullable @RequestParam(name = "inContact", required = false) Boolean inContact,
                                @Nullable @RequestParam(name = "favourite", required = false) Boolean favourite,
                                @Nullable @DecimalMin("0") @DecimalMax("1") @RequestParam(name = "minCompatibilityScore", required = false) Float minCompatibilityScore,
                                @Nullable @DecimalMin("0") @DecimalMax("1") @RequestParam(name = "maxCompatibilityScore", required = false) Float maxCompatibilityScore,
                                @Nullable @Min(18) @Max(95) @RequestParam(name = "minAge", required = false) Integer minAge,
                                @Nullable @Min(18) @Max(95) @RequestParam(name = "maxAge", required = false) Integer maxAge,
                                @Nullable @Min(135) @Max(210) @RequestParam(name = "minHeight", required = false) Integer minHeight,
                                @Nullable @Min(135) @Max(210) @RequestParam(name = "maxHeight", required = false) Integer maxHeight,
                                @Nullable @Min(0) @RequestParam(name = "distance", required = false) Float distance,
                                @Nullable @Pattern(regexp = DISTANCE_UNIT_REGEX) @RequestParam(name = "distanceUnit", required = false) String distanceUnit,
                                @Nullable @RequestParam(name = "lat", required = false) Double lat,
                                @Nullable @RequestParam(name = "lon", required = false) Double lon) {

        if (distance != null && (lat == null || lon == null || distanceUnit == null)) {
            throw new InvalidRequestException("Latitude/Longitude/DistanceUnit are required when requiring distance matches.");
        }

        validateRanges(Pair.of(minCompatibilityScore, maxCompatibilityScore), Pair.of(minAge, maxAge), Pair.of(minHeight, maxHeight));

        return customReactivePersonRepository.findBy(withPhoto, inContact, favourite, minCompatibilityScore, maxCompatibilityScore, minAge, maxAge,
                minHeight, maxHeight, distance, distanceUnit, lat, lon)
                .map(apiMapper::toApiPerson);
    }

    @SafeVarargs
    private final <T extends Number> void validateRanges(Pair<T, T>... pairs) {
        if (Stream.of(pairs)
                .anyMatch(pair -> pair.getFirst() != null && pair.getSecond() != null && pair.getFirst().floatValue() > pair.getSecond().floatValue())) {
            throw new InvalidRequestException("Invalid range.");
        }
    }
}
