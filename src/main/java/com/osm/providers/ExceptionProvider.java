/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.osm.providers;

import com.osm.exceptions.ResponseException;
import javax.inject.Inject;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;
import org.slf4j.Logger;

@Provider
public class ExceptionProvider implements ExceptionMapper<Throwable> {

    @Inject
    private Logger log;

    @Override
    public Response toResponse(Throwable throwable) {
        log.error(ExceptionProvider.class.getName(), throwable);

        return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                .type(MediaType.APPLICATION_JSON)
                .entity(new ResponseException.Builder()
                        .setCode(ResponseException.UNHANDLER_ERROR)
                        .setMessage(throwable.getLocalizedMessage())
                        .build())
                .build();
    }
}
