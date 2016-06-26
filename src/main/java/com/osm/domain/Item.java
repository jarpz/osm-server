package com.osm.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.util.ArrayList;
import java.util.List;

public class Item extends Model {

    private String group;
    private List<Double> price;
    private Double tax;
    private Long stock;

    public Item() {
    }

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
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

}
