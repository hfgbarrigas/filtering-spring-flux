package com.affinitas.filter;

import com.affinitas.filter.exceptions.DatasourceInitializationException;
import com.affinitas.filter.logger.Loggable;
import com.affinitas.filter.model.Person;
import com.affinitas.filter.repositories.ReactivePersonRepository;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

import java.util.List;
import java.util.Map;

@SpringBootApplication
public class FilterApplication implements Loggable {

    public static void main(String[] args) {
        ApplicationContext ctx = SpringApplication.run(FilterApplication.class);

        final ObjectMapper objectMapper = ctx.getBean(ObjectMapper.class);
        final ReactivePersonRepository personRepository = ctx.getBean(ReactivePersonRepository.class);

        try {
            final Map<String, List<Person>> staticData = objectMapper.readValue(ctx.getResource("classpath:database/data.json").getInputStream(), new TypeReference<Map<String, List<Person>>>() {});
            final List<Person> personList = staticData.get("matches");
            //personList.forEach(p -> p.setId(UUID.randomUUID().toString()));
            personRepository.saveAll(personList)
                .subscribe(System.out::println);

        } catch (Exception e) {
            throw new DatasourceInitializationException("Error while bootstrapping application.", e);
        }

    }
}
