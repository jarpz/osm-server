package com.osm.resources;

import com.osm.domain.Company;
import com.osm.domain.Customer;
import com.osm.domain.CustomerType;
import com.osm.domain.Model;
import com.osm.domain.TaxType;
import com.osm.services.CustomersService;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    //
    private Company company = new Company("LINKCO", "LINKCO", "001");

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
        mCustomersService.insert(company, customer);
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
        return mCustomersService.getTypes(company);
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/zones")
    public List<Model> getZones() {
        return mCustomersService.getZones(company);
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/tax-types")
    public Map<String, Integer> getTaxTypes() {
        Map<String, Integer> values = new HashMap<>();
        values.put(TaxType.TAXPAYER.name(), TaxType.TAXPAYER.value());
        values.put(TaxType.NO_TAXPAYER.name(), TaxType.NO_TAXPAYER.value());
        values.put(TaxType.EXEMPT.name(), TaxType.EXEMPT.value());
        values.put(TaxType.EXPORTER.name(), TaxType.EXPORTER.value());
        values.put(TaxType.FORMAL_TAXPAYER.name(), TaxType.FORMAL_TAXPAYER.value());
        values.put(TaxType.OTHERS.name(), TaxType.OTHERS.value());
        return values;
    }
}
