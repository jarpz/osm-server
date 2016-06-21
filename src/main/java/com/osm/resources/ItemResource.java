package com.osm.resources;

import com.osm.domain.Company;
import com.osm.domain.Item;
import com.osm.services.ItemService;
import java.util.List;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/items")
@RequestScoped
public class ItemResource {

    //
    private Company company = new Company("LINKCO", "LINKCO", "001");

    @Inject
    private ItemService mItemService;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Item> getAll() {
        return mItemService.getAll(company);
    }
}
