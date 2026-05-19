package com.empresa.catalogo.exception;

public class ApiError {

    private int status;
    private String error;
    private String mensaje;
    private String timestamp;
    private String path;
// constructor y getters
    public ApiError() {
    }

    public ApiError(int status, String error, String mensaje, String path, String timestamp) {
        this.error = error;
        this.mensaje = mensaje;
        this.path = path;
        this.status = status;
        this.timestamp = timestamp;
    }

    public ApiError(int status, String error, String mensaje, String path) {
        this.error = error;
        this.mensaje = mensaje;
        this.path = path;
        this.status = status;
    }
}
