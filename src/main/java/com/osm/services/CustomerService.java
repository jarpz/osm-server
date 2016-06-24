package com.osm.services;

import com.osm.domain.Company;
import com.osm.domain.Customer;
import com.osm.domain.CustomerType;
import com.osm.domain.Model;
import com.osm.domain.TaxType;
import com.osm.exceptions.CreateException;
import com.osm.exceptions.ServerException;
import com.osm.exceptions.UpdateException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import org.apache.log4j.Logger;
import org.jooq.DSLContext;
import org.jooq.Field;
import org.jooq.Record;
import org.jooq.exception.DataAccessException;
import org.jooq.impl.DSL;

@ApplicationScoped
public class CustomerService {

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
                    customer.setCode(rs.get(DSL.field("codigo"), String.class));
                    customer.setName(rs.get(DSL.field("nombre"), String.class));
                    customer.setZone(rs.get(DSL.field("sector", String.class)));
                    customer.setAddress(rs.get(DSL.field("direccion", String.class)));
                    customer.setTin(rs.get(DSL.field("nrorif", String.class)));
                    customer.addPhone(rs.get(DSL.field("telefonos"), String.class));
                    customer.setTaxType(TaxType.valueOf(rs.get(DSL.field("formafis", Double.class)).intValue()));
                    return customer;
                });
    }

    public boolean insert(final Company company,
            final Customer customer) throws ServerException {

        try {

            if (db.selectCount()
                    .from(DSL.table("cliempre"))
                    .where(DSL.field("codigo").eq(customer.getCode()))
                    .fetchOne(0, Integer.class) <= 0) {

                CustomerType type = getTypes(company)
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
                        DSL.field("status"), DSL.field("formafis"))
                        .values(company.getCode(), company.getBranch(),
                                customer.getCode(), customer.getName(),
                                customer.getIdentification(), customer.getTin(),
                                customer.getAddress(), customer.getPhones().get(0),
                                1, type.getCode(), customer.getZone(),
                                new Date(), Boolean.TRUE, customer.getTaxType().value())
                        .execute() > 0;
            } else {
                throw new CreateException()
                        .putCode(10002);
            }
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
            if (customer.getZone() != null) {
                values.put(DSL.field("sector"), customer.getZone());
            }
                        
            if(customer.getTaxType() !=null){
                values.put(DSL.field("formafis"),customer.getTaxType().value());
            }
            
            if (customer.getContact() != null) {
                values.put(DSL.field("perscont"), customer.getContact());
            }           

            if (values.isEmpty() || customer.getCode() == null) {
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

    public List<CustomerType> getTypes(final Company company) {
        return db.select()
                .from(DSL.table("tipocli"))
                .where(DSL.field("id_empresa").equal(company.getCode()))
                .fetch((Record rs) -> {
                    CustomerType type = new CustomerType();
                    type.setCode(rs.get("codigo", String.class));
                    type.setName(rs.get("nombre", String.class));
                    type.setIsDefault(rs.get("predeter", Integer.class) > 0);
                    return type;
                });
    }

    public List<Model> getZones(final Company company) {
        return db.select()
                .from(DSL.table("sectores"))
                .where(DSL.field("id_empresa").equal(company.getCode()))
                .fetch((Record rs) -> new Model(rs.get(DSL.field("codigo"), String.class),
                        rs.get(DSL.field("zona"), String.class)));
    }
}
