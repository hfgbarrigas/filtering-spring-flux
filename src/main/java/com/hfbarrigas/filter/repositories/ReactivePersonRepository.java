package com.hfbarrigas.filter.repositories;

import com.hfbarrigas.filter.model.internal.Person;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface ReactivePersonRepository extends ReactiveMongoRepository<Person, String> {

}
