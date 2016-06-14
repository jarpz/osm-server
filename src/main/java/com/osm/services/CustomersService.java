
package com.osm.services;

import com.osm.domain.Customer;
import com.osm.services.data.CustomerSql;
import com.osm.utils.Utils;
import java.sql.ResultSet;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import org.springframework.jdbc.core.JdbcTemplate;

@ApplicationScoped
public class CustomersService {

    @Inject
    private Logger log;

    @Inject
    private JdbcTemplate db;

    public List<Customer> getCustomers() {
        return db.query(CustomerSql.FIND_ALL,
                (ResultSet rs, int index)
                -> new Customer.Builder()
                .setCode(Utils.trim(rs.getString(CustomerSql.FIELDS.CODE.getName())))
                .setName(Utils.trim(rs.getString(CustomerSql.FIELDS.NAME.getName())))
                .setIdentification(Utils.trim(rs.getString(CustomerSql.FIELDS.IDENTIFICATION.getName())))
                .setTin(Utils.trim(rs.getString(CustomerSql.FIELDS.TIN.getName())))
                .setAddress(Utils.trim(rs.getString(CustomerSql.FIELDS.ADDRESS.getName()).trim()))
                .addPhone(Utils.trim(rs.getString(CustomerSql.FIELDS.PHONES.getName())))
                .addPhone(Utils.trim(rs.getString(CustomerSql.FIELDS.MOVIL.getName())))
                .setPriceId(rs.getInt(CustomerSql.FIELDS.PRICE.getName()))
                .build());
    }

    public boolean insert(Customer customer) {
        try {
            return true;
        } catch (Throwable throwable) {
            log.log(Level.SEVERE, "", throwable);
        }
        return false;
    }
}
