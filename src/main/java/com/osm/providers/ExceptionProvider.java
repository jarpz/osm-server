
package com.osm.providers;

import com.osm.exceptions.ResponseException;
import com.osm.exceptions.UnAuthorizedException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.inject.Inject;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class ExceptionProvider implements ExceptionMapper<Throwable> {

    @Inject
    private Logger log;

    @Override
    public Response toResponse(Throwable throwable) {
        log.log(Level.SEVERE, "", throwable);

        if (throwable instanceof UnAuthorizedException) {
            return Response.status(Response.Status.UNAUTHORIZED)
                    .type(MediaType.APPLICATION_JSON)
                    .entity(new ResponseException.Builder()
                            .setCode(0)
                            .build())
                    .build();
        } else if (throwable instanceof WebApplicationException) {
            WebApplicationException wae = (WebApplicationException) throwable;
            return Response.status(wae.getResponse().getStatusInfo().getStatusCode())
                    .type(MediaType.APPLICATION_JSON)
                    .entity(new ResponseException.Builder()
                            .setCode(ResponseException.UNHANDLER_ERROR)
                            .setMessage(wae.getResponse().getStatusInfo().getReasonPhrase().toUpperCase())
                            .build())
                    .build();
        }

        return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                .type(MediaType.APPLICATION_JSON)
                .entity(new ResponseException.Builder()
                        .setCode(ResponseException.UNHANDLER_ERROR)
                        .setMessage(throwable.getLocalizedMessage())
                        .build())
                .build();
    }
}
