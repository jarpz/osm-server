package com.osm.domain;

import com.fasterxml.jackson.annotation.JsonValue;


public enum TaxType {
    TAXPAYER(1),
    NO_TAXPAYER(2),
    EXEMPT(3),
    EXPORTER(4),
    FORMAL_TAXPAYER(5),
    OTHERS(6);

    private int value;

    private TaxType(int value) {
        this.value = value;
    }

    @JsonValue
    public int value() {
        return value;
    }

    public static TaxType valueOf(int value) {
        switch (value) {
            case 1:
                return TAXPAYER;
            case 2:
                return NO_TAXPAYER;
            case 3:
                return EXEMPT;
            case 4:
                return EXPORTER;
            case 5:
                return FORMAL_TAXPAYER;
            case 6:
                return OTHERS;
            default:
                return null;
        }
    }
}
