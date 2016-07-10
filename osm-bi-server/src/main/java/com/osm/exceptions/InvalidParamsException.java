package com.osm.exceptions;


public class InvalidParamsException extends ServerException {


    public InvalidParamsException() {
        setMessage("Invalid parameters");
    }
}
