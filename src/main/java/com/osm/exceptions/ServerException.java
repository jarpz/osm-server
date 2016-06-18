package com.osm.exceptions;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.osm.providers.ServerExceptionSerializer;
import java.io.Serializable;

@JsonSerialize(using = ServerExceptionSerializer.class)
public class ServerException extends RuntimeException implements Serializable {

    public static final int UNHANDLER_ERROR = -1;

    private int code;
    private String message;
    private String localizedMessage;

    public ServerException() {
    }

    public ServerException(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    @JsonIgnore
    public ServerException putCode(int code) {
        this.code = code;
        return this;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @JsonIgnore
    public ServerException putMessage(String message) {
        this.message = message;
        return this;
    }

    public String getLocalizedMessage() {
        return localizedMessage;
    }

    public void setLocalizedMessage(String localizedMessage) {
        this.localizedMessage = localizedMessage;
    }

    @JsonIgnore
    public ServerException putLocalizedMessage(String message) {
        this.localizedMessage = message;
        return this;
    }

    public static class Builder {

        private int code;
        private String message;
        private String localizedMessage;

        public Builder() {
        }

        public Builder setCode(int code) {
            this.code = code;
            return this;
        }

        public Builder setMessage(String message) {
            this.message = message;
            return this;
        }

        public Builder setLocalizedMessage(String localizedMessage) {
            this.localizedMessage = localizedMessage;
            return this;
        }

        public ServerException build() {
            return new ServerException(code, message)
                    .putLocalizedMessage(localizedMessage);
        }
    }
}
