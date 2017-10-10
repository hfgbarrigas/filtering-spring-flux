package com.hfbarrigas.filter.model.api;

import java.time.OffsetDateTime;
import java.util.Objects;

public class ErrorDetails {

    private OffsetDateTime timestamp;
    private Integer status;
    private String error;
    private String exception;
    private String message;
    private String path;

    private ErrorDetails(Builder builder) {
        this.timestamp = builder.timestamp;
        this.status = builder.status;
        this.error = builder.error;
        this.exception = builder.exception;
        this.message = builder.message;
        this.path = builder.path;
    }

    public OffsetDateTime getTimestamp() {
        return timestamp;
    }

    public Integer getStatus() {
        return status;
    }

    public String getError() {
        return error;
    }

    public String getException() {
        return exception;
    }

    public String getMessage() {
        return message;
    }

    public String getPath() {
        return path;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ErrorDetails that = (ErrorDetails) o;
        return Objects.equals(timestamp, that.timestamp) &&
                Objects.equals(status, that.status) &&
                Objects.equals(error, that.error) &&
                Objects.equals(exception, that.exception) &&
                Objects.equals(message, that.message) &&
                Objects.equals(path, that.path);
    }

    @Override
    public int hashCode() {
        return Objects.hash(timestamp, status, error, exception, message, path);
    }

    @Override
    public String toString() {
        return "ErrorDetails{" +
                "timestamp=" + timestamp +
                ", status=" + status +
                ", error='" + error + '\'' +
                ", exception='" + exception + '\'' +
                ", message='" + message + '\'' +
                ", path='" + path + '\'' +
                '}';
    }

    public static Builder builder() {
        return new Builder();
    }

    public static final class Builder {

        protected OffsetDateTime timestamp = null;
        protected Integer status = null;
        protected String error = null;
        protected String exception = null;
        protected String message = null;
        protected String path = null;

        private Builder() {

        }

        public Builder withTimestamp(OffsetDateTime timestamp) {
            this.timestamp = timestamp;
            return this;
        }

        public Builder withStatus(Integer status) {
            this.status = status;
            return this;
        }

        public Builder withError(String error) {
            this.error = error;
            return this;
        }

        public Builder withException(String exception) {
            this.exception = exception;
            return this;
        }

        public Builder withMessage(String message) {
            this.message = message;
            return this;
        }

        public Builder withPath(String path) {
            this.path = path;
            return this;
        }

        public ErrorDetails build() {
            return new ErrorDetails(this);
        }
    }

}
