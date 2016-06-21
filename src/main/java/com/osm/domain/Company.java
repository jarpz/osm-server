package com.osm.domain;

public class Company extends Model {

    private String branch;

    public Company() {
    }

    public Company(String code, String name, String branch) {
        super(code, name);
        this.branch = branch;
    }

    public String getBranch() {
        return branch;
    }

    public void setBranch(String branch) {
        this.branch = branch;
    }
}
