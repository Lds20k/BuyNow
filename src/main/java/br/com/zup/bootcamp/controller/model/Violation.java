package br.com.zup.bootcamp.controller.model;

public class Violation {
    private String field;
    private String message;

    public Violation(String param, String message){
        this.field = param;
        this.message = message;
    }

    public String getField() {
        return field;
    }

    public String getMessage() {
        return message;
    }
}
