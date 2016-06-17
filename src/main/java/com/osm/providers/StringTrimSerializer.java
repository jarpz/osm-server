package com.osm.providers;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import java.io.IOException;

public class StringTrimSerializer extends JsonSerializer<String> {

    @Override
    public void serialize(String value, JsonGenerator jg, SerializerProvider sp) throws IOException, JsonProcessingException {
        if (value != null) {
            jg.writeString(value.trim());
        }
    }
}
