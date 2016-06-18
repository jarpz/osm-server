package com.osm.providers;

import com.osm.exceptions.CreateException;
import com.osm.exceptions.ServerException;
import com.osm.exceptions.UnAuthorizedException;
import com.osm.exceptions.UpdateException;
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

        if (throwable instanceof ServerException) {
            Response.Status status = Response.Status.SERVICE_UNAVAILABLE;

            if (throwable instanceof CreateException || throwable instanceof UpdateException) {
                status = Response.Status.BAD_REQUEST;
            } else if (throwable instanceof UnAuthorizedException) {
                status = Response.Status.UNAUTHORIZED;
            }

            return Response.status(status)
                    .type(MediaType.APPLICATION_JSON)
                    .entity(throwable)
                    .build();

        } else if (throwable instanceof WebApplicationException) {
            WebApplicationException wae = (WebApplicationException) throwable;
            return Response.status(wae.getResponse().getStatusInfo().getStatusCode())
                    .type(MediaType.APPLICATION_JSON)
                    .entity(new ServerException.Builder()
                            .setCode(wae.getResponse().getStatusInfo().getStatusCode())
                            .setMessage(wae.getResponse().getStatusInfo().getReasonPhrase().toUpperCase())
                            .setLocalizedMessage(wae.getLocalizedMessage())
                            .build())
                    .build();
        }

        return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                .type(MediaType.APPLICATION_JSON)
                .entity(new ServerException.Builder()
                        .setCode(ServerException.UNHANDLER_ERROR)
                        .setMessage(throwable.getLocalizedMessage())
                        .setLocalizedMessage(throwable.getLocalizedMessage())
                        .build())
                .build();
    }
}
