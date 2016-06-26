package com.osm.services;

import com.osm.domain.Company;
import com.osm.domain.Item;
import java.util.List;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import org.apache.log4j.Logger;
import org.jooq.DSLContext;
import org.jooq.Record;
import org.jooq.impl.DSL;

@ApplicationScoped
public class ItemService {

    @Inject
    private Logger log;

    @Inject
    private DSLContext db;

    public List<Item> getAll(final Company company) {
        return db.select()
                .from(DSL.table("articulo"))
                .where(DSL.field("id_empresa").equal(company.getCode()))
                .fetch((Record rs) -> {
                    Item item = new Item();
                    item.setCode(rs.get(DSL.field("codigo"), String.class));
                    item.setName(rs.get(DSL.field("nombre"), String.class));
                    item.setGroup(rs.get(DSL.field("grupo"), String.class));
                    item.addPrice(rs.get(DSL.field("precio1"), Double.class));
                    item.addPrice(rs.get(DSL.field("precio2"), Double.class));
                    item.addPrice(rs.get(DSL.field("precio3"), Double.class));
                    item.addPrice(rs.get(DSL.field("precio4"), Double.class));
                    item.addPrice(rs.get(DSL.field("precio5"), Double.class));
                    item.addPrice(rs.get(DSL.field("precio6"), Double.class));
                    item.addPrice(rs.get(DSL.field("precio7"), Double.class));
                    item.addPrice(rs.get(DSL.field("precio8"), Double.class));
                    item.setStock(rs.get(DSL.field("existencia"), Double.class).longValue());
                    return item;
                });
    }
}
