package com.osm.exceptions;

public class CreateException extends ServerException {

    public CreateException() {
        setMessage("Entity not created");
    }
}
