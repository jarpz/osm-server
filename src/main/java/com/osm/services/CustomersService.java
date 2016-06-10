/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.osm.services;

import com.github.davidmoten.rx.jdbc.Database;
import com.osm.domain.Customer;
import com.osm.utils.Utils;
import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import org.slf4j.Logger;
import rx.Observable;

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
    private Database db;

    public List<Customer> getCustomers() {
        return db.select("select * from cliempre")
                .get(rs -> new Customer.Builder()
                        .setCode(Utils.trim(rs.getString(FIELDS.CODE.getName())))
                        .setName(Utils.trim(rs.getString(FIELDS.NAME.getName())))
                        .setIdentification(Utils.trim(rs.getString(FIELDS.IDENTIFICATION.getName())))
                        .setAddress(Utils.trim(rs.getString(FIELDS.ADDRESS.getName()).trim()))
                        .build())
                .toList()
                .toBlocking()
                .single();
    }

    public boolean insert(Customer customer) {
        try {
            Observable<Boolean> begin = db.beginTransaction();
            return db.commit(db.update("insert into tbl_test (name,age) values(?,?)")
                    .parameter(customer.getName())
                    .parameter(customer.getAddress())
                    .dependsOn(begin)
                    .count())
                    .toBlocking()
                    .single();
        } catch (Throwable throwable) {
            log.error(CustomersService.class.getName(), throwable);
        }
        return false;
    }
}
