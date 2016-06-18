package com.osm.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
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

    private String contact;

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

    @JsonIgnore
    public void addPhone(String phone) {
        if (this.phones == null) {
            this.phones = new ArrayList<>();
        }

        this.phones.add(phone);
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

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }
}
