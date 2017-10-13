package com.hfbarrigas.filter.model.api;

import java.util.Objects;

public class City {

    private String name;
    private Coordinates coordinates;

    //jackson purposes
    public City() {
    }

    private City(Builder builder) {
        this.name = builder.name;
        this.coordinates = builder.coordinates;
    }

    public String getName() {
        return name;
    }

    public Coordinates getCoordinates() {
        return coordinates;
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

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private String name;
        private Coordinates coordinates;

        private Builder() {
        }

        public Builder withName(String name) {
            this.name = name;
            return this;
        }

        public Builder withCoordinates(Coordinates coordinates) {
            this.coordinates = coordinates;
            return this;
        }

        public City build() {
            return new City(this);
        }
    }
}
