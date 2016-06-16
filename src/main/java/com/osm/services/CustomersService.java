package com.osm.services;

import com.osm.domain.Customer;
import com.osm.domain.CustomerType;
import com.osm.services.data.CustomerSql;
import com.osm.services.data.CustomerTypeSql;
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
                .setCode(Utils.trim(rs.getString(CustomerSql.FIELDS.CODE)))
                .setName(Utils.trim(rs.getString(CustomerSql.FIELDS.NAME)))
                .setIdentification(Utils.trim(rs.getString(CustomerSql.FIELDS.IDENTIFICATION)))
                .setTin(Utils.trim(rs.getString(CustomerSql.FIELDS.TIN)))
                .setAddress(Utils.trim(rs.getString(CustomerSql.FIELDS.ADDRESS).trim()))
                .addPhone(Utils.trim(rs.getString(CustomerSql.FIELDS.PHONES)))
                .addPhone(Utils.trim(rs.getString(CustomerSql.FIELDS.MOVIL)))
                .setPrice(rs.getInt(CustomerSql.FIELDS.PRICE))
                .setType(Utils.trim(rs.getString(CustomerSql.FIELDS.TYPE)))
                .setTag(Utils.trim(rs.getString(CustomerSql.FIELDS.TAG)))
                .build());
    }

    public List<CustomerType> getTypes() {
        return db.query(CustomerTypeSql.FIND_ALL,
                (ResultSet rs, int index)
                -> new CustomerType.Builder()
                .setCode(Utils.trim(rs.getString(CustomerTypeSql.FIELDS.CODE)))
                .setName(Utils.trim(rs.getString(CustomerTypeSql.FIELDS.NAME)))
                .setIsDefault(rs.getInt(CustomerTypeSql.FIELDS.DEFAULT) > 0)
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
