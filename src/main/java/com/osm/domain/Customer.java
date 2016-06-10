package com.osm.domain;

import java.io.Serializable;

public class Customer implements Serializable {

    private String code;
    private String name;
    private String identification;
    private String address;

    public Customer() {
    }

    public Customer(final String code,
            final String name,
            final String identification,
            final String address) {

        this.code = code;
        this.name = name;
        this.identification = identification;
        this.address = address;
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

    public String getIdentification() {
        return identification;
    }

    public void setIdentification(String identification) {
        this.identification = identification;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public static class Builder {

        private String code;
        private String name;
        private String identification;
        private String address;

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

        public Builder setIdentification(String identification) {
            this.identification = identification;
            return this;
        }

        public Builder setAddress(String address) {
            this.address = address;
            return this;
        }

        public Customer build() {
            return new Customer(code, name, identification, address);
        }
    }
}
