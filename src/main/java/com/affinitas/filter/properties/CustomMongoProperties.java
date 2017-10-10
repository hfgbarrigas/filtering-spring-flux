package com.affinitas.filter.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "affinitas.filtering.mongo")
public class CustomMongoProperties {
    //this can grow way more, write concerns, read concerns and preferences, heart beat configurations, cluster configuration ... and so on
    private Integer readTimeout = 1000;
    private Integer connectTimeout = 2000;
    private String applicationName = "filtering";
    private Integer heartbeatFrequency = 1000;
    private Integer serverSelectionTimeout = 1000;

    public Integer getReadTimeout() {
        return readTimeout;
    }

    public void setReadTimeout(Integer readTimeout) {
        this.readTimeout = readTimeout;
    }

    public Integer getConnectTimeout() {
        return connectTimeout;
    }

    public void setConnectTimeout(Integer connectTimeout) {
        this.connectTimeout = connectTimeout;
    }

    public String getApplicationName() {
        return applicationName;
    }

    public void setApplicationName(String applicationName) {
        this.applicationName = applicationName;
    }

    public Integer getHeartbeatFrequency() {
        return heartbeatFrequency;
    }

    public void setHeartbeatFrequency(Integer heartbeatFrequency) {
        this.heartbeatFrequency = heartbeatFrequency;
    }

    public Integer getServerSelectionTimeout() {
        return serverSelectionTimeout;
    }

    public void setServerSelectionTimeout(Integer serverSelectionTimeout) {
        this.serverSelectionTimeout = serverSelectionTimeout;
    }
}
