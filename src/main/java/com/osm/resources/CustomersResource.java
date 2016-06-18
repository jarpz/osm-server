package com.osm.resources;

import com.osm.domain.Company;
import com.osm.domain.Customer;
import com.osm.domain.CustomerType;
import com.osm.services.CustomersService;
import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/customers")
@RequestScoped
public class CustomersResource {

    @Inject
    private CustomersService mCustomersService;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Customer> getAll() {
        return mCustomersService.getCustomers();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response create(Customer customer) {
        mCustomersService.insert(new Company("LINKCO", "LINKCO", "001"), customer);
        return Response
                .status(Response.Status.CREATED)
                .build();
    }

    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response update(Customer customer) {
        mCustomersService.update(customer);
        return Response
                .ok()
                .build();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/types")
    public List<CustomerType> getTypes() {
        return mCustomersService.getTypes();
    }
}
