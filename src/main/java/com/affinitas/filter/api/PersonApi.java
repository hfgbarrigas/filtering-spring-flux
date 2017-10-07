package com.affinitas.filter.api;

import com.affinitas.filter.model.Person;
import com.affinitas.filter.repositories.ReactivePersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

@RestController
public class PersonApi {

    private ReactivePersonRepository personRepository;

    @Autowired
    public PersonApi(ReactivePersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    @GetMapping(value = "/persons", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseBody
    public Flux<Person> listPersons() {
        return personRepository.findAll();
    }

    @GetMapping(value = "/persons", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    @ResponseBody
    public Flux<Person> listPersonsAsJson(@RequestParam(value = "json", required = true) String json) {
        return personRepository.findAll();
    }

}
