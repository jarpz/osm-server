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
                    item.setPrice(rs.get(DSL.field("precio1"),Double.class));
                    item.setStock(rs.get(DSL.field("existencia"),Double.class).longValue());
                    return item;
                });
    }
}
