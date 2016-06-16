package com.osm.services.data;

public class CustomerTypeSql {

    public static final class FIELDS {

        public static final String CODE = "codigo";
        public static final String NAME = "nombre";
        public static final String DEFAULT = "predeter";
    }

    public static final String FIND_ALL = "select * from tipocli order by predeter";

}
