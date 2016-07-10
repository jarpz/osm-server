package com.osm.providers;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.osm.exceptions.ServerException;
import java.io.IOException;

public class ServerExceptionSerializer extends JsonSerializer<ServerException> {

    @Override
    public void serialize(ServerException error, JsonGenerator jgen, SerializerProvider sp) throws IOException, JsonProcessingException {
        jgen.writeStartObject();
        jgen.writeNumberField("code", error.getCode());
        jgen.writeStringField("message", error.getMessage());
        if (error.getLocalizedMessage() != null) {
            jgen.writeStringField("localizedMessage", error.getLocalizedMessage());
        }
        jgen.writeEndObject();
    }

}
