package com.osm.services;

import com.osm.domain.Company;
import com.osm.domain.Customer;
import com.osm.domain.CustomerType;
import com.osm.exceptions.CreateException;
import com.osm.exceptions.ServerException;
import com.osm.exceptions.UpdateException;
import com.osm.services.data.CustomerSql;
import com.osm.services.data.CustomerTypeSql;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import org.jooq.DSLContext;
import org.jooq.Field;
import org.jooq.Record;
import org.jooq.exception.DataAccessException;
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
                .fetch((Record rs) -> {
                    Customer customer = new Customer();
                    customer.setCode(rs.get(DSL.field(CustomerSql.FIELDS.CODE), String.class));
                    customer.setIdentification(rs.get(DSL.field(CustomerSql.FIELDS.IDENTIFICATION), String.class));
                    customer.setName(rs.get(DSL.field(CustomerSql.FIELDS.NAME), String.class));
                    customer.setType(rs.get(DSL.field(CustomerSql.FIELDS.TYPE, String.class)));
                    customer.setTag(rs.get(DSL.field(CustomerSql.FIELDS.TAG, String.class)));
                    customer.setAddress(rs.get(DSL.field(CustomerSql.FIELDS.ADDRESS, String.class)));
                    customer.setTin(rs.get(DSL.field(CustomerSql.FIELDS.TIN, String.class)));
                    customer.setPrice(rs.get(DSL.field(CustomerSql.FIELDS.PRICE, Double.class)).intValue());
                    customer.addPhone(rs.get(DSL.field(CustomerSql.FIELDS.PHONES), String.class));
                    return customer;
                });
    }

    public boolean insert(Company company, Customer customer) throws ServerException {
        try {
            CustomerType type = getTypes()
                    .stream()
                    .filter((CustomerType t) -> t.getIsDefault())
                    .findFirst()
                    .get();

            return db.insertInto(DSL.table("cliempre"),
                    DSL.field("id_empresa"), DSL.field("agencia"),
                    DSL.field("codigo"), DSL.field("nombre"),
                    DSL.field("cedula"), DSL.field("nrorif"),
                    DSL.field("direccion"), DSL.field("telefonos"),
                    DSL.field("precio"), DSL.field("tipo"),
                    DSL.field("sector"), DSL.field("fecha"),
                    DSL.field("status"))
                    .values(company.getCode(), company.getBranch(),
                            customer.getCode(), customer.getName(),
                            customer.getIdentification(), customer.getTin(),
                            customer.getAddress(), customer.getPhones().get(0),
                            customer.getPrice(), type.getCode(),
                            customer.getTag(), new Date(), Boolean.TRUE)
                    .execute() > 0;
        } catch (DataAccessException throwable) {
            throw new CreateException()
                    .putCode(10001)
                    .putLocalizedMessage(throwable.getLocalizedMessage());
        }
    }

    public boolean update(Customer customer) {
        try {
            Map<Field<?>, Object> values = new HashMap<Field<?>, Object>();

            if (customer.getName() != null) {
                values.put(DSL.field("nombre"), customer.getName());
            }
            if (customer.getIdentification() != null) {
                values.put(DSL.field("cedula"), customer.getIdentification());
            }
            if (customer.getTin() != null) {
                values.put(DSL.field("nrorif"), customer.getTin());
            }
            if (customer.getAddress() != null) {
                values.put(DSL.field("direccion"), customer.getAddress());
            }
            if (customer.getPhones() != null && customer.getPhones().size() > 0) {
                values.put(DSL.field("telefonos"), customer.getPhones().get(0));
            }
            if (customer.getPrice() != null) {
                values.put(DSL.field("precio"), customer.getPrice());
            }
            if (customer.getTag() != null) {
                values.put(DSL.field("sector"), customer.getTag());
            }
            if (customer.getContact() != null) {
                values.put(DSL.field("perscont"), customer.getContact());
            }

            if (values.isEmpty()) {
                throw new UpdateException()
                        .putCode(20002)
                        .putMessage("No values to update");
            }

            return db.update(DSL.table("cliempre"))
                    .set(values)
                    .where(DSL.field("codigo").eq(customer.getCode()))
                    .execute() > 0;
        } catch (DataAccessException throwable) {
            throw new UpdateException()
                    .putCode(20001)
                    .putLocalizedMessage(throwable.getLocalizedMessage());
        }
    }

    public List<CustomerType> getTypes() {
        return db.select()
                .from(DSL.table("tipocli"))
                .fetch((Record rs) -> new CustomerType.Builder()
                        .setCode(rs.get(CustomerTypeSql.FIELDS.CODE, String.class))
                        .setName(rs.get(CustomerTypeSql.FIELDS.NAME, String.class))
                        .setIsDefault(rs.get(CustomerTypeSql.FIELDS.DEFAULT, Integer.class) > 0)
                        .build());
    }

}
