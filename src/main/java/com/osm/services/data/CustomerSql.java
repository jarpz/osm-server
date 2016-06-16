package com.osm.services.data;

public final class CustomerSql {

    public static class FIELDS {

        public static final String CODE = "codigo";
        public static final String NAME = "nombre";
        public static final String IDENTIFICATION = "cedula";
        public static final String TIN = "nrorif";
        public static final String ADDRESS = "direccion";
        public static final String PHONES = "telefonos";
        public static final String MOVIL = "telefono_movil";
        public static final String PRICE = "precio";
        public static final String TAG = "sector";
        public static final String TYPE = "tipo";

    }

    public static final String FIND_ALL = "select * from cliempre where status = 1";
}
