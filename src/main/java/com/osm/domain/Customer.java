package com.osm.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Customer extends Object implements Serializable {

    private String identification;
    private String address;
    private List<String> phones;
    private String tin;
    private Integer price;
    private String type;
    private String tag;

    public Customer() {
    }

    public Customer(final String code,
            final String name,
            final String identification,
            final String tin,
            final String address,
            final List<String> phones,
            final Integer price,
            final String type,
            final String tag) {

        super(code, name);
        this.identification = identification;
        this.tin = tin;
        this.address = address;
        this.phones = phones;
        this.price = price;
        this.type = type;
        this.tag = tag;
    }

    public String getIdentification() {
        return identification;
    }

    public void setIdentification(String identification) {
        this.identification = identification;
    }

    public String getTin() {
        return tin;
    }

    public void setTin(String tin) {
        this.tin = tin;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public List<String> getPhones() {
        return phones;
    }

    public void setPhones(List<String> phones) {
        this.phones = phones;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public static class Builder {

        private String code;
        private String name;
        private String identification;
        private String address;
        private String tin;
        private List<String> phones;
        private Integer price;
        private String type;
        private String tag;

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

        public Builder setTin(String tin) {
            this.tin = tin;
            return this;
        }

        public Builder setAddress(String address) {
            this.address = address;
            return this;
        }

        public void setPhones(List<String> phones) {
            this.phones = phones;
        }

        public Builder addPhone(String phone) {
            if (this.phones == null) {
                this.phones = new ArrayList<>();
            }

            if (phone != null && !phone.isEmpty()) {
                this.phones.add(phone);
            }

            return this;
        }

        public Builder setPrice(int price) {
            this.price = price;
            return this;
        }

        public Builder setType(String type) {
            this.type = type;
            return this;
        }

        public Builder setTag(String tag) {
            this.tag = tag;
            return this;
        }

        public Customer build() {
            return new Customer(code, name, identification, tin, address, phones, price, type, tag);
        }
    }
}
