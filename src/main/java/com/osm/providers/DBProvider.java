
package com.osm.providers;

import javax.annotation.Resource;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Produces;
import javax.sql.DataSource;
import org.springframework.jdbc.core.JdbcTemplate;

@ApplicationScoped
public class DBProvider {

    @Resource(name = "jdbc/pskloud")
    private DataSource dataSource;

    @Produces
    public JdbcTemplate get() {
        return new JdbcTemplate(dataSource);
    }
}
