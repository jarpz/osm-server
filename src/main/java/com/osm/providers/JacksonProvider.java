package com.osm.providers;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.Version;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import javax.ws.rs.Produces;
import javax.ws.rs.ext.ContextResolver;

@Produces
public class JacksonProvider implements ContextResolver<ObjectMapper> {

    final SimpleModule module = new SimpleModule("CustomSerializer", new Version(1, 0, 0, ""));

    private ObjectMapper mapper = new ObjectMapper();

    public JacksonProvider() {
        module.addSerializer(String.class, new StringTrimSerializer());
        mapper.registerModule(module);
        mapper.setSerializationInclusion(JsonInclude.Include.NON_EMPTY);
    }

    @Override
    public ObjectMapper getContext(Class<?> type) {
        return mapper;
    }

}
