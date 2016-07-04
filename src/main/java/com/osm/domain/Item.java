package com.osm.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.sun.org.apache.xpath.internal.operations.Bool;

import java.util.ArrayList;
import java.util.List;

public class Item extends Model {

    private String group;
    private List<Double> price;
    private Double tax;
    private Double taxRef;
    private Double cost;
    private Long stock;
    @JsonIgnore
    private String supplier;
    @JsonIgnore
    private Long origin;
    private Boolean useSerial;

    private Integer composite;
    private Boolean useStock;

    public Item() {
    }

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    public Double getCost() {
        return cost;
    }

    public void setCost(Double cost) {
        this.cost = cost;
    }

    public List<Double> getPrice() {
        return price;
    }

    public void setPrice(List<Double> price) {
        this.price = price;
    }

    @JsonIgnore
    public void addPrice(final Double price) {
        if (this.price == null) {
            this.price = new ArrayList<>();
        }
        this.price.add(price);
    }

    public Double getTax() {
        return tax;
    }

    public void setTax(Double tax) {
        this.tax = tax;
    }

    public Long getStock() {
        return stock;
    }

    public void setStock(Long stock) {
        this.stock = stock;
    }

    public String getSupplier() {
        return supplier;
    }

    public void setSupplier(String supplier) {
        this.supplier = supplier;
    }


    public Long getOrigin() {
        return origin;
    }

    public void setOrigin(Long origin) {
        this.origin = origin;
    }


    public Double getTaxRef() {
        return taxRef;
    }

    public void setTaxRef(Double taxRef) {
        this.taxRef = taxRef;
    }

    public Boolean useSerial() {
        return useSerial;
    }

    public void setUseSerial(boolean useSerial) {
        this.useSerial = useSerial;
    }

    public Integer getComposite() {
        return composite;
    }

    public void setComposite(Integer composite) {
        this.composite = composite;
    }

    public Boolean useStock() {
        return useStock;
    }

    public void setUseStock(boolean useStock) {
        this.useStock = useStock;
    }
}
