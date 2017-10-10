package com.affinitas.filter;

import com.affinitas.filter.exceptions.DatasourceInitializationException;
import com.affinitas.filter.logger.Loggable;
import com.affinitas.filter.model.internal.Person;
import com.affinitas.filter.repositories.ReactivePersonRepository;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;

import javax.validation.constraints.NotNull;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@SpringBootApplication
public class FilterApplication implements Loggable {

    public static void main(String[] args) {
        SpringApplication.run(FilterApplication.class);
    }

    @Bean
    public CommandLineRunner initDatabase(@NotNull ReactivePersonRepository repository,
                                          @NotNull ObjectMapper objectMapper,
                                          @NotNull ApplicationContext ctx) {
        final List<Person> persons = new ArrayList<>();
        try {
            final Map<String, List<Person>> staticData = objectMapper.readValue(ctx.getResource("classpath:database/data.json").getInputStream(), new TypeReference<Map<String, List<Person>>>() {});
            persons.addAll(staticData.get("matches"));
        } catch (Exception e) {
            throw new DatasourceInitializationException(e);
        }

        return args -> repository.deleteAll()
                .thenMany(repository.saveAll(persons))
                .blockLast(Duration.ofSeconds(2));
    }
}
