package com.osm.services;

import com.osm.domain.Company;
import com.osm.domain.Customer;
import com.osm.domain.Order;
import com.osm.domain.OrderItem;
import java.util.ArrayList;
import java.sql.Date;
import java.util.List;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import org.apache.log4j.Logger;
import org.jooq.DSLContext;
import org.jooq.Result;
import org.jooq.impl.DSL;

@ApplicationScoped
public class OrderService {

    @Inject
    private Logger log;

    @Inject
    private DSLContext db;

    public List<Order> getAll(final Company company) {
        return extractor(db.select(
                DSL.field("order.codcliente").as("customer_code"), DSL.field("order.nombrecli").as("customer_name"),
                DSL.field("order.direccion").as("customer_address"), DSL.field("order.rif").as("customer_tin"),
                DSL.field("order.documento").as("code"), DSL.field("order.emision").as("created"),
                DSL.field("order.vence").as("expiration"), DSL.field("order.totneto").as("amount"),
                DSL.field("order.totimpuest").as("tax"), DSL.field("order.totalfinal").as("total"),
                DSL.field("currencies.simbolo").as("currency"), DSL.field("orderitem.codigo").as("orderitem.code"),
                DSL.field("orderitem.nombre").as("orderitem.name"), DSL.field("orderitem.grupo").as("orderitem.group"),
                DSL.field("orderitem.impu_mto").as("orderitem.tax"), DSL.field("orderitem.cantidad").as("orderitem.quantity"),
                DSL.field("orderitem.preciounit").as("orderitem.unit_price"), DSL.field("orderitem.montoneto").as("orderitem.amount"),
                DSL.field("orderitem.montototal").as("orderitem.total"))
                .from(DSL.table("operti").as("order"))
                .innerJoin(DSL.table("monedas").as("currencies"))
                .on(DSL.field("order.moneda").eq(DSL.field("currencies.codigo")))
                .innerJoin(DSL.table("opermv").as("orderitem"))
                .on(DSL.field("order.documento").eq(DSL.field("orderitem.documento")))
                .where(DSL.field("order.id_empresa").eq(company.getCode())
                        .and(DSL.field("order.tipodoc").eq("PED")))
                .orderBy(DSL.field("order.documento"),
                        DSL.field("order.codcliente"),
                        DSL.field("orderitem.codigo"))
                .fetch(), true);
    }

    public List<Order> getByCustomer(final Customer cs) {
        return extractor(db.select(
                DSL.field("order.documento").as("code"), DSL.field("order.emision").as("created"),
                DSL.field("order.vence").as("expiration"), DSL.field("order.totneto").as("amount"),
                DSL.field("order.totimpuest").as("tax"), DSL.field("order.totalfinal").as("total"),
                DSL.field("currencies.simbolo").as("currency"), DSL.field("orderitem.codigo").as("orderitem.code"),
                DSL.field("orderitem.nombre").as("orderitem.name"), DSL.field("orderitem.grupo").as("orderitem.group"),
                DSL.field("orderitem.impu_mto").as("orderitem.tax"), DSL.field("orderitem.cantidad").as("orderitem.quantity"),
                DSL.field("orderitem.preciounit").as("orderitem.unit_price"), DSL.field("orderitem.montoneto").as("orderitem.amount"),
                DSL.field("orderitem.montototal").as("orderitem.total"))
                .from(DSL.table("operti").as("order"))
                .innerJoin(DSL.table("monedas").as("currencies"))
                .on(DSL.field("order.moneda").eq(DSL.field("currencies.codigo")))
                .innerJoin(DSL.table("opermv").as("orderitem"))
                .on(DSL.field("order.documento").eq(DSL.field("orderitem.documento")))
                .where(DSL.field("order.codcliente").eq(cs.getCode())
                        .and(DSL.field("order.tipodoc").eq("PED")))
                .orderBy(DSL.field("order.documento"),
                        DSL.field("order.codcliente"),
                        DSL.field("orderitem.codigo"))
                .fetch(), false);
    }

    private List<Order> extractor(Result<?> records, boolean withCustomer) {
        log.info("result: " + records.size());

        List<Order> orders = new ArrayList<>();

        Order order;
        OrderItem item;
        Customer customer;

        for (int i = 0; i < records.size(); i++) {
            order = new Order();

            order.setCode(records.getValue(i, DSL.field("code", String.class)));
            order.setCreated(records.getValue(i, DSL.field("created", Date.class)).getTime());
            order.setExpiration(records.getValue(i, DSL.field("expiration", Date.class)).getTime());
            order.setAmount(records.getValue(i, DSL.field("amount", Double.class)));
            order.setTax(records.getValue(i, DSL.field("tax", Double.class)));
            order.setTotal(records.getValue(i, DSL.field("total", Double.class)));
            order.setCurrency(records.getValue(i, DSL.field("currency", String.class)));

            if (withCustomer) {
                customer = new Customer();
                customer.setCode(records.getValue(i, DSL.field("customer_code", String.class)));
                customer.setName(records.getValue(i, DSL.field("customer_name", String.class)));
                customer.setAddress(records.getValue(i, DSL.field("customer_address", String.class)));
                customer.setTin(records.getValue(i, DSL.field("customer_tin", String.class)));
                order.setCustomer(customer);
            }

            List<OrderItem> items = new ArrayList<>();
            for (int j = i; j < records.size(); j++, i++) {

                if (records.getValue(j, DSL.field("code", String.class))
                        .equalsIgnoreCase(order.getCode())) {
                    item = new OrderItem();
                    item.setCode(records.getValue(j, DSL.field("orderitem.code", String.class)));
                    item.setName(records.getValue(j, DSL.field("orderitem.name", String.class)));
                    item.setDocument(order.getCode());
                    item.setGroup(records.getValue(j, DSL.field("orderitem.group", String.class)));
                    item.setTax(records.getValue(j, DSL.field("orderitem.tax", Double.class)));
                    item.setUnitPrice(records.getValue(j, DSL.field("orderitem.unit_price", Double.class)));
                    item.setQuantity(records.getValue(j, DSL.field("orderitem.quantity", Double.class)));
                    item.setAmount(records.getValue(j, DSL.field("orderitem.amount", Double.class)));
                    item.setTotal(records.getValue(j, DSL.field("orderitem.total", Double.class)));
                    items.add(item);
                } else {
                    break;
                }
            }
            i--;
            order.setItems(items);
            orders.add(order);
        }
        return orders;
    }
}
