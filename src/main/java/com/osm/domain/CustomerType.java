package com.osm.domain;

public class CustomerType extends Model {

    private Boolean isDefault;

    public CustomerType() {
    }

    public CustomerType(String code, String name, Boolean isDefault) {
        super(code, name);
        this.isDefault = isDefault;
    }

    public Boolean getIsDefault() {
        return isDefault;
    }

    public void setIsDefault(Boolean isDefault) {
        this.isDefault = isDefault;
    }

}
