
package com.osm.services.data;

public final class CustomerSql {

    public static enum FIELDS {
        CODE("codigo"),
        NAME("nombre"),
        IDENTIFICATION("cedula"),
        TIN("nrorif"),
        ADDRESS("direccion"),
        PHONES("telefonos"),
        MOVIL("telefono_movil"),
        PRICE("precio");

        private String name;

        private FIELDS(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }
    }

    public static final String FIND_ALL = "select * from cliempre";
}
