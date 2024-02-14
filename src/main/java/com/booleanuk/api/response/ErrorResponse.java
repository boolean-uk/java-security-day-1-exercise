package com.booleanuk.api.response;

public class ErrorResponse extends Response<String>{

    public ErrorResponse() {
        this.setStatus("Error");
        this.setData("Could not create/update videogame, please check all required fields.");
    }

    public ErrorResponse(int id) {
        this.setStatus("Error");
        this.setData(("Videogame with id " + id + " not found"));
    }
}
