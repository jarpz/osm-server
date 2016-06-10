/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.osm.services;

import com.osm.domain.Customer;
import com.osm.utils.Utils;
import java.sql.ResultSet;
import java.util.List;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import org.slf4j.Logger;
import org.springframework.jdbc.core.JdbcTemplate;

@RequestScoped
public class CustomersService {

    private enum FIELDS {
        CODE("codigo"),
        NAME("nombre"),
        IDENTIFICATION("cedula"),
        ADDRESS("direccion");

        private String name;

        private FIELDS(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }
    }

    @Inject
    private Logger log;

    @Inject
    private JdbcTemplate db;

    public List<Customer> getCustomers() {
        return db.query("select * from cliempre",
                (ResultSet rs, int index)
                -> new Customer.Builder()
                .setCode(Utils.trim(rs.getString(FIELDS.CODE.getName())))
                .setName(Utils.trim(rs.getString(FIELDS.NAME.getName())))
                .setIdentification(Utils.trim(rs.getString(FIELDS.IDENTIFICATION.getName())))
                .setAddress(Utils.trim(rs.getString(FIELDS.ADDRESS.getName()).trim()))
                .build());
    }

    public boolean insert(Customer customer) {
        try {
            return true;
        } catch (Throwable throwable) {
            log.error(CustomersService.class.getName(), throwable);
        }
        return false;
    }
}
