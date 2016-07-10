package com.osm.providers;

import com.fasterxml.jackson.databind.ObjectMapper;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.Produces;
import javax.ws.rs.ext.ContextResolver;

@Produces
public class JacksonProvider implements ContextResolver<ObjectMapper> {

    @Inject
    private ObjectMapper mapper;

    @Override
    public ObjectMapper getContext(Class<?> type) {
        return mapper;
    }

}
