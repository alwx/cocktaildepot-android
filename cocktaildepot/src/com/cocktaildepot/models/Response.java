package com.cocktaildepot.models;

public class Response {
    private int code;
    private String message;
    private Object object;

    public Response(int code, Object object) {
        this.code = code;
        this.object = object;
        this.message = null;
    }

    public Response(int code, String message) {
        this.code = code;
        this.object = null;
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public Object getObject() {
        return object;
    }
}
