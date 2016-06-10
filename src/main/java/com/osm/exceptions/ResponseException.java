/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.osm.exceptions;

import java.io.Serializable;

public class ResponseException implements Serializable {

    public static final int UNHANDLER_ERROR = -1;

    private int code;
    private String message;

    public ResponseException() {
    }

    public ResponseException(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public static class Builder {

        private int code;
        private String message;

        public Builder() {
        }

        public Builder setCode(int code) {
            this.code = code;
            return this;
        }

        public Builder setMessage(String message) {
            this.message = message;
            return this;
        }

        public ResponseException build() {
            return new ResponseException(code, message);
        }
    }
}
