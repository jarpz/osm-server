package com.osm.services;

import com.osm.domain.Customer;
import com.osm.domain.CustomerType;
import com.osm.services.data.CustomerSql;
import com.osm.services.data.CustomerTypeSql;
import com.osm.utils.Utils;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import org.jooq.DSLContext;
import org.jooq.Record;
import org.jooq.impl.DSL;

@ApplicationScoped
public class CustomersService {

    @Inject
    private Logger log;

    @Inject
    private DSLContext db;

    public List<Customer> getCustomers() {
        return db.select()
                .from(DSL.table("cliempre"))
                .where(DSL.field("status").equal(1))
                .fetch((Record rs) -> new Customer.Builder()
                        .setCode(Utils.trim(rs.get(DSL.field(CustomerSql.FIELDS.CODE), String.class)))
                        .setIdentification(Utils.trim(rs.get(DSL.field(CustomerSql.FIELDS.IDENTIFICATION), String.class)))
                        .setName(Utils.trim(rs.get(DSL.field(CustomerSql.FIELDS.NAME), String.class)))
                        .setType(Utils.trim(rs.get(DSL.field(CustomerSql.FIELDS.TYPE, String.class))))
                        .setTag(Utils.trim(rs.get(DSL.field(CustomerSql.FIELDS.TAG, String.class))))
                        .setAddress(Utils.trim(rs.get(DSL.field(CustomerSql.FIELDS.ADDRESS, String.class))))
                        .setTin(Utils.trim(rs.get(DSL.field(CustomerSql.FIELDS.TIN, String.class))))
                        .setPrice(rs.get(DSL.field(CustomerSql.FIELDS.PRICE, Double.class)).intValue())
                        .addPhone(Utils.trim(rs.get(DSL.field(CustomerSql.FIELDS.PHONES), String.class)))
                        .build());
    }

    public List<CustomerType> getTypes() {
        return db.select()
                .from(DSL.table("tipocli"))
                .fetch((Record rs) -> new CustomerType.Builder()
                        .setCode(Utils.trim(rs.get(CustomerTypeSql.FIELDS.CODE, String.class)))
                        .setName(Utils.trim(rs.get(CustomerTypeSql.FIELDS.NAME, String.class)))
                        .setIsDefault(rs.get(CustomerTypeSql.FIELDS.DEFAULT, Integer.class) > 0)
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
