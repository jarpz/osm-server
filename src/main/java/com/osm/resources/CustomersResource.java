package com.osm.resources;

import com.osm.domain.Customer;
import com.osm.domain.CustomerType;
import com.osm.services.CustomersService;
import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/customers")
@RequestScoped
public class CustomersResource {

    @Inject
    private CustomersService customersService;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Customer> getAll() {
        return customersService.getCustomers();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/types")
    public List<CustomerType> getTypes() {
        return customersService.getTypes();
    }
}
