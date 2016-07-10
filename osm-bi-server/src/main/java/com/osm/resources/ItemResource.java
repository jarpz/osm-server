package com.osm.resources;

import com.osm.domain.Company;
import com.osm.domain.Item;
import com.osm.services.MemCachedService;
import com.osm.services.ItemService;
import java.util.List;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import org.apache.log4j.Logger;

@Path("/items")
@RequestScoped
public class ItemResource {

    //
    private Company company = new Company("LINKCO", "LINKCO", "001");

    @Inject
    private ItemService mItemService;

    @Inject
    private MemCachedService mMcc;

    @Inject
    private Logger log;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Item> getAll() {
        return mItemService.getAll(company);
    }

    
    
    
    
    
    
    //TEST MemCached
    
    @POST
    @Path("/test")
    @Produces(MediaType.APPLICATION_JSON)
    public Response create(@QueryParam("value") String value) throws Throwable {
        return Response
                .ok(mMcc.set("value", 300, value, mMcc.new JsonTranscoder(String.class))
                        .get())
                .build();
    }

    @GET
    @Path("/test")
    @Produces(MediaType.TEXT_PLAIN)
    public String get() {
        String value = mMcc.get("value", mMcc.new JsonTranscoder<String>(String.class));
        if (value != null) {
            return value;
        }

        throw new UnsupportedOperationException();
    }

}
