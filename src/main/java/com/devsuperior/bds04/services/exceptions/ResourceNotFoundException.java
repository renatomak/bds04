package com.devsuperior.bds04.services.exceptions;

public class ResourceNotFoundException extends RuntimeException {
    private static final long serialVersionUID = 2062434147875331065L;

    public ResourceNotFoundException(String msg) {
        super(msg);
    }
}
