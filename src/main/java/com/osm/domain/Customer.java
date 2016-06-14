
package com.osm.domain;

import com.fasterxml.jackson.annotation.JsonInclude;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class Customer implements Serializable {

    private String code;
    private String name;
    private String identification;
    private String address;
    private List<String> phones;
    private String tin;
    private Integer priceId;

    public Customer() {
    }

    public Customer(final String code,
            final String name,
            final String identification,
            final String tin,
            final String address,
            final List<String> phones,
            final Integer priceId) {

        this.code = code;
        this.name = name;
        this.identification = identification;
        this.tin = tin;
        this.address = address;
        this.phones = phones;
        this.priceId = priceId;
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

    public Integer getPriceId() {
        return priceId;
    }

    public void setPriceId(Integer priceId) {
        this.priceId = priceId;
    }

    public static class Builder {

        private String code;
        private String name;
        private String identification;
        private String address;
        private String tin;
        private List<String> phones;
        private Integer priceId;

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

        public Builder setPriceId(int priceId) {
            this.priceId = priceId;
            return this;
        }

        public Customer build() {
            return new Customer(code, name, identification, tin, address, phones, priceId);
        }
    }
}
