package com.hfbarrigas.filter.repositories;

import com.hfbarrigas.filter.model.internal.Person;
import reactor.core.publisher.Flux;

public interface CustomReactivePersonRepository {
    Flux<Person> findBy(Boolean withPhoto, Boolean inContact, Boolean favourite, Float minCompatibilityScore,
                        Float maxCompatibilityScore, Integer minAge, Integer maxAge, Integer minHeight,
                        Integer maxHeight, Float distance, String distanceUnit, Double lat, Double lon);
}
