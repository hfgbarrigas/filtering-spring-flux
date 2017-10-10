package com.affinitas.filter.mapper;

import com.affinitas.filter.model.api.City;
import com.affinitas.filter.model.api.Person;
import com.affinitas.filter.model.internal.Coordinates;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class ApiMapper {

    public Person toApiPerson(com.affinitas.filter.model.internal.Person p) {
        com.affinitas.filter.model.internal.City city = Optional.ofNullable(p.getCity()).orElse(new com.affinitas.filter.model.internal.City());
        Coordinates coordinates = Optional.ofNullable(city.getCoordinates()).orElse(null);

        City.Builder cityBuilder = City.builder().withName(p.getCity().getName());
        Optional.ofNullable(coordinates)
                .ifPresent(c -> cityBuilder.withCoordinates(com.affinitas.filter.model.api.Coordinates.builder()
                        .withLon(c.getLon())
                        .withLat(c.getLat())
                        .build()));

        return Person.builder()
                .withAge(p.getAge())
                .withCity(cityBuilder.build())
                .withCompatibilityScore(p.getCompatibilityScore())
                .withContactsExchanged(p.getContactsExchanged())
                .withDisplayName(p.getDisplayName())
                .withFavourite(p.getFavourite())
                .withHeightInCm(p.getHeightInCm())
                //.withId(p.getId()) // removed for testing purposes
                .withJobTitle(p.getJobTitle())
                .withMainPhoto(p.getMainPhoto())
                .withReligion(p.getReligion())
                .build();
    }
}
