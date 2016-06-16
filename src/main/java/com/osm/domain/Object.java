package com.osm.domain;

import com.fasterxml.jackson.annotation.JsonInclude;
import java.io.Serializable;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class Object implements Serializable {

    private String code;
    private String name;

    public Object() {
    }

    public Object(String code, String name) {
        this.code = code;
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public static class Builder {

        private String code;
        private String name;

        public Builder() {
        }

        public Builder setCode(String code) {
            this.code = code;
            return this;
        }

        public Builder setName(String name) {
            this.name = name;
            return this;
        }

        public Object build() {
            return new Object(code, name);
        }
    }
}