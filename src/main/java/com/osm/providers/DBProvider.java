
package com.osm.providers;

import javax.annotation.Resource;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Produces;
import javax.sql.DataSource;
import org.jooq.DSLContext;
import org.jooq.SQLDialect;
import org.jooq.impl.DSL;
import org.springframework.jdbc.core.JdbcTemplate;

@ApplicationScoped
public class DBProvider {

    @Resource(name = "jdbc/pskloud")
    private DataSource dataSource;

//    @Produces
//    public JdbcTemplate template() {
//        return new JdbcTemplate(dataSource);
//    }
    
    @Produces
    public DSLContext dslContext(){
        return DSL.using(dataSource, SQLDialect.MYSQL);
    }
}
