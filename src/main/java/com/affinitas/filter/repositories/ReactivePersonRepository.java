package com.affinitas.filter.repositories;

import com.affinitas.filter.model.Person;
import org.reactivestreams.Publisher;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ReactivePersonRepository extends ReactiveMongoRepository<Person, String> {

    Flux<Person> findByDisplayName(String firstname);

    //Flux<Person> findByFirstname(Publisher<String> firstname);

    //Flux<Person> findByFirstnameOrderByLastname(String firstname, Pageable pageable);

    //Mono<Person> findByFirstnameAndLastname(String firstname, String lastname);
}
