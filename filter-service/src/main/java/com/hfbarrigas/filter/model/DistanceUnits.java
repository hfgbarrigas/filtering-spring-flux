package com.hfbarrigas.filter.model;

import org.springframework.data.geo.Metrics;

public enum DistanceUnits {
    KM(Metrics.KILOMETERS),
    MI(Metrics.MILES);

    private Metrics metric;

    DistanceUnits(Metrics metric) {
        this.metric = metric;
    }

    public Metrics getMongoMetric() {
        return metric;
    }
}
