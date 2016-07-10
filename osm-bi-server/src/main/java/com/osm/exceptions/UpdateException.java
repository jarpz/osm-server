package com.osm.exceptions;

public class UpdateException extends ServerException {

    public UpdateException() {
        setMessage("Entity not updated");
    }
}
