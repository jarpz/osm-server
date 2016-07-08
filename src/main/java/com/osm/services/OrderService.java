package com.osm.services;

import com.osm.domain.Company;
import com.osm.domain.Customer;
import com.osm.domain.Order;
import com.osm.domain.OrderItem;
import com.osm.exceptions.CreateException;
import com.osm.exceptions.InvalidParamsException;
import org.apache.log4j.Logger;
import org.jooq.DSLContext;
import org.jooq.InsertValuesStepN;
import org.jooq.Record;
import org.jooq.Result;
import org.jooq.impl.DSL;

import javax.annotation.Resource;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@ApplicationScoped
public class OrderService {

    private static final String ORDER_TYPE = "PED";

    @Resource(name = "string/defaultUser")
    private String defaultUser;

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
                        .and(DSL.field("order.tipodoc").eq(ORDER_TYPE)))
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
                        .and(DSL.field("order.tipodoc").eq(ORDER_TYPE)))
                .orderBy(DSL.field("order.documento"),
                        DSL.field("order.codcliente"),
                        DSL.field("orderitem.codigo"))
                .fetch(), false);
    }

    public boolean create(final Company company,
                          final Order order) {
        return db.transactionResult(config -> {

            Map<String, Double> itemValues = new HashMap();
            for (OrderItem oItem : order.getItems()) {
                if (oItem.getQuantity() > 0)
                    itemValues.put(oItem.getCode(), oItem.getQuantity());
                else
                    throw new InvalidParamsException()
                            .putMessage("Quantity can not be 0")
                            .putCode(20001);
            }

            String orderSeq = DSL.using(config)
                    .select()
                    .from(DSL.table("agencias"))
                    .where(DSL.field("agencia").eq(company.getBranch())
                            .and(DSL.field("id_empresa").eq(company.getCode())))
                    .fetchOne(DSL.field("cpedidov", String.class));
            Long seq = Long.valueOf(orderSeq) + 1;
            orderSeq = orderSeq.substring(0, orderSeq.length() - seq.toString().length()) + seq.toString();

            log.info("Document: " + orderSeq);

            List<OrderItem> items = DSL.using(config)
                    .select()
                    .from(DSL.table("articulo"))
                    .where(DSL.field("codigo").in(order.getItemsCode()))
                    .fetch((Record rs) -> {
                        OrderItem item = new OrderItem();
                        item.setCode(rs.get(DSL.field("codigo"), String.class).trim());
                        item.setName(rs.get(DSL.field("nombre"), String.class).trim());
                        item.setGroup(rs.get(DSL.field("grupo"), String.class).trim());
                        item.setGroup(item.getGroup() + "," + rs.get(DSL.field("subgrupo", String.class)).trim());
                        item.setSupplier("monda");
                        item.setOrigin(rs.get(DSL.field("origen", Double.class)).longValue());
                        item.setUseSerial(rs.get(DSL.field("usaserial", Double.class)).intValue() > 0);
                        item.setComposite(rs.get(DSL.field("compuesto", Double.class)).intValue());
                        item.setUseStock(rs.get(DSL.field("usaexist", Double.class)).intValue() > 0);

                        item.setQuantity(itemValues.get(item.getCode()));
                        item.setCost(rs.get(DSL.field("costo", Double.class)));
                        item.setUnitPrice(rs.get(DSL.field("precio" + order.getCustomer().getPrice(), Double.class)));
                        item.setAmount(item.getUnitPrice() * item.getQuantity());
                        item.setTaxRef(rs.get(DSL.field("impuesto1", Double.class)));
                        item.setTax((item.getAmount() * item.getTaxRef()) / 100);
                        item.setTotal(item.getAmount() + item.getTax());

                        order.setAmount(order.getAmount() + item.getAmount());
                        order.setTax(order.getTax() + item.getTax());
                        order.setTotal(order.getTotal() + item.getTotal());
                        return item;
                    });

            if (DSL.using(config)
                    .insertInto(DSL.table("operti"),
                            DSL.field("id_empresa"), DSL.field("agencia"),
                            DSL.field("tipodoc"), DSL.field("moneda"),
                            DSL.field("documento"), DSL.field("codcliente"),
                            DSL.field("nombrecli"), DSL.field("contacto"), DSL.field("rif"),
                            DSL.field("direccion"), DSL.field("telefonos"),
                            DSL.field("tipoprecio"), DSL.field("emision"),
                            DSL.field("recepcion"), DSL.field("vence"),
                            DSL.field("fechacrea"), DSL.field("totcosto"),
                            DSL.field("totcomi"), DSL.field("totbruto"),
                            DSL.field("totneto"), DSL.field("totalfinal"),
                            DSL.field("totimpuest"), DSL.field("impuesto1"), DSL.field("impuesto2"),
                            DSL.field("diascred"), DSL.field("vendedor"),
                            DSL.field("uemisor"), DSL.field("estacion"),
                            DSL.field("almacen"), DSL.field("sector"),
                            DSL.field("formafis"), DSL.field("retencion"),
                            DSL.field("horadocum"), DSL.field("despacho_nro"),
                            DSL.field("fechayhora"), DSL.field("idvalidacion"))
                    .values(company.getCode(), company.getBranch(),
                            ORDER_TYPE, "001", orderSeq, order.getCustomer().getCode(),
                            order.getCustomer().getName(), order.getCustomer().getContact(),
                            order.getCustomer().getTin(), order.getCustomer().getAddress(),
                            order.getCustomer().getPhones().get(0), order.getCustomer().getTaxType().value(),
                            new Date(order.getCreated()), new Date(System.currentTimeMillis()),
                            new Date(order.getExpiration()), new Date(System.currentTimeMillis()),
                            order.getAmount(), 0, order.getAmount(), order.getAmount(),
                            order.getTotal(), order.getTax(), order.getTax(),
                            order.getTax(), order.getCustomer().getCreditDays(), "02", defaultUser, "001", "01",
                            order.getCustomer().getZone(), order.getCustomer().getTaxType().value(),
                            0, "", new SimpleDateFormat("hh:mm").format(new java.util.Date()),
                            new Date(System.currentTimeMillis()), "ID_VALIDATE")
                    .execute() > 0) {


                InsertValuesStepN<Record> valuesStepN = DSL.using(config)
                        .insertInto(DSL.table("opermv"),
                                DSL.field("id_empresa"), DSL.field("agencia"),
                                DSL.field("tipodoc"), DSL.field("documento"),
                                DSL.field("grupo"), DSL.field("subgrupo"),
                                DSL.field("origen"), DSL.field("codigo"),
                                DSL.field("pid"), DSL.field("nombre"),
                                DSL.field("costounit"), DSL.field("preciounit"),
                                DSL.field("preciofin"), DSL.field("preciooriginal"),
                                DSL.field("cantidad"), DSL.field("montoneto"),
                                DSL.field("montototal"), DSL.field("almacen"),
                                DSL.field("proveedor"), DSL.field("fechadoc"),
                                DSL.field("impuesto1"), DSL.field("impuesto2"),
                                DSL.field("timpueprc"), DSL.field("impu_mto"),
                                DSL.field("comision"), DSL.field("comisprc"),
                                DSL.field("vendedor"), DSL.field("emisor"),
                                DSL.field("usaserial"), DSL.field("tipoprecio"),
                                DSL.field("agrupado"), DSL.field("compuesto"),
                                DSL.field("usaexist"), DSL.field("estacion"));
                for (OrderItem orderItem : items) {
                    String[] groups = orderItem.getGroup().split(",");
                    valuesStepN = valuesStepN.values(
                            company.getCode(), company.getBranch(),
                            ORDER_TYPE, orderSeq, groups.length > 0 ? groups[0] : "",
                            groups.length > 1 ? groups[1] : "", orderItem.getOrigin(),
                            orderItem.getCode(), "000NISE", orderItem.getName(),
                            orderItem.getCost(), orderItem.getUnitPrice(),
                            orderItem.getUnitPrice(), orderItem.getUnitPrice(),
                            orderItem.getQuantity(), orderItem.getAmount(),
                            orderItem.getTotal(), "01", orderItem.getSupplier(), new Date(order.getCreated()),
                            orderItem.getTax(), orderItem.getTax(), orderItem.getTaxRef(),
                            orderItem.getTax(), 0, 0, "02", defaultUser, orderItem.useSerial(),
                            order.getCustomer().getPrice(), orderItem.getComposite(), orderItem.getComposite(),
                            orderItem.useStock(), "001");
                }
                if (valuesStepN.execute() > 0 && DSL.using(config)
                        .update(DSL.table("agencias"))
                        .set(DSL.field("cpedidov"), orderSeq)
                        .where(DSL.field("agencia").eq(company.getBranch())
                                .and(DSL.field("id_empresa").eq(company.getCode())))
                        .execute() > 0)
                    return true;
                else
                    throw new CreateException()
                            .putMessage("Order Items can not be created")
                            .putCode(20003);
            } else
                throw new CreateException()
                        .putMessage("Order can not be created")
                        .putCode(20002);
        });
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
