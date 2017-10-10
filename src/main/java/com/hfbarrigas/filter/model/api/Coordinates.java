package com.hfbarrigas.filter.model.api;

import java.util.Objects;

public class Coordinates {

    private Double lat;
    private Double lon;

    //jackson
    public Coordinates() {
    }

    private Coordinates(Builder builder) {
        this.lat = builder.lat;
        this.lon = builder.lon;
    }

    public Double getLat() {
        return lat;
    }

    public Double getLon() {
        return lon;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Coordinates that = (Coordinates) o;
        return Objects.equals(lat, that.lat) && Objects.equals(lon, that.lon);
    }

    @Override
    public int hashCode() {
        return Objects.hash(lat, lon);
    }

    @Override
    public String toString() {
        return "Coordinates{" +
                "lat=" + lat +
                ", lon=" + lon +
                '}';
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private Double lon;
        private Double lat;

        public Builder withLon(Double lon) {
            this.lon = lon;
            return this;
        }

        public Builder withLat(Double lat) {
            this.lat = lat;
            return this;
        }

        public Coordinates build() {
            return new Coordinates(this);
        }
    }
}
