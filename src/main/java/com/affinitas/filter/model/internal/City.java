package com.affinitas.filter.model.internal;

import org.springframework.data.mongodb.core.index.GeoSpatialIndexType;
import org.springframework.data.mongodb.core.index.GeoSpatialIndexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;
import java.util.Objects;

@Document(collection = "cities")
public class City implements Serializable{
    private String name;
    @GeoSpatialIndexed(type = GeoSpatialIndexType.GEO_2DSPHERE)
    private Coordinates coordinates;

    public String getName() {
        return name;
    }

    public City setName(String name) {
        this.name = name;
        return this;
    }

    public Coordinates getCoordinates() {
        return coordinates;
    }

    public City setCoordinates(Coordinates coordinates) {
        this.coordinates = coordinates;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        City city = (City) o;
        return Objects.equals(name, city.name) &&
                Objects.equals(coordinates, city.coordinates);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, coordinates);
    }

    @Override
    public String toString() {
        return "City{" +
                "name='" + name + '\'' +
                ", coordinates=" + coordinates +
                '}';
    }
}
