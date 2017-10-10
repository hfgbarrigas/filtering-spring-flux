package com.affinitas.filter.model.internal;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.data.mongodb.core.geo.GeoJson;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class Coordinates implements GeoJson<List<Double>>, Serializable {

    private static final String TYPE = "Point";
    private Double lat;
    private Double lon;

    @JsonCreator
    public Coordinates(@JsonProperty(value = "lat") Double lat, @JsonProperty(value = "lon") Double lon) {
        this.lat = lat;
        this.lon = lon;
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
        return Objects.equals(lat, that.lat) &&
                Objects.equals(lon, that.lon);
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

    @Override
    public String getType() {
        return TYPE;
    }

    @Override
    public List<Double> getCoordinates() {
        return Arrays.asList(getLat(), getLon());
    }
}
