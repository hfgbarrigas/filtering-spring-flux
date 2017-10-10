package com.affinitas.filter.repositories.converters;

import com.affinitas.filter.model.internal.Coordinates;
import org.bson.Document;
import org.springframework.core.convert.converter.Converter;

import java.util.ArrayList;
import java.util.List;

public class CoordinatesReadConverter implements Converter<Document, Coordinates> {
    @Override
    @SuppressWarnings("unchecked")
    public Coordinates convert(Document document) {
        List<Double> coordinates = (List<Double>) document.getOrDefault("coordinates", new ArrayList<>(2));
        return new Coordinates(coordinates.get(0), coordinates.get(1));
    }
}
