package com.osm.domain;

public class CustomerType extends Object {

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

    public static class Builder {

        private String code;
        private String name;
        private Boolean isDefault;

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

        public Builder setIsDefault(Boolean isDefault) {
            this.isDefault = isDefault;
            return this;
        }

        public CustomerType build() {
            return new CustomerType(code, name, isDefault);
        }
    }
}
