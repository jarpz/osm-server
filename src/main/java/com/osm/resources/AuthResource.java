/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.osm.resources;

import com.osm.exceptions.ServerException;
import com.osm.exceptions.UnAuthorizedException;
import javax.inject.Inject;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import org.apache.log4j.Logger;

@Path("/auth")
public class AuthResource {

    @Inject
    private Logger log;

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public Response auth(@HeaderParam("Authorization") String authorization) throws ServerException {
        log.info(authorization);

        throw new UnAuthorizedException()
                .putCode(1001);
    }
}
