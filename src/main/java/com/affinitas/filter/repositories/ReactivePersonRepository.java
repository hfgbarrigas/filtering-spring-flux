package com.affinitas.filter.repositories;

import com.affinitas.filter.model.internal.Person;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface ReactivePersonRepository extends ReactiveMongoRepository<Person, String> {

}
