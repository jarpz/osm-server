package com.osm.providers;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.Version;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import javax.enterprise.inject.Produces;

public class ObjectMapperProvider {

    @Produces
    public ObjectMapper instance() {
        final SimpleModule module = new SimpleModule("CustomSerializer", Version.unknownVersion());

        ObjectMapper mapper = new ObjectMapper();
        module.addSerializer(String.class, new StringTrimSerializer());
        mapper.registerModule(module);
        mapper.setSerializationInclusion(JsonInclude.Include.NON_EMPTY);
        return mapper;
    }
}
