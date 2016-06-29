package com.osm.resources;

import com.osm.domain.Company;
import com.osm.domain.Customer;
import com.osm.domain.Order;
import com.osm.services.OrderService;
import java.util.List;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/orders")
@RequestScoped
public class OrderResource {

    private Company company = new Company("LINKCO", "LINKCO", "001");

    @Inject
    private OrderService mOrderService;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Order> getAll() {
        return mOrderService.getAll(company);
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/customer/{customer}")
    public List<Order> getByCustomer(@PathParam("customer") String customer) {
        return mOrderService.getByCustomer(new Customer(customer));
    }
}
