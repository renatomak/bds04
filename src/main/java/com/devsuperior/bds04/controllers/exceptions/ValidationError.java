package com.devsuperior.bds04.controllers.exceptions;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ValidationError extends StandardError implements Serializable {
    private static final long serialVersionUID = -8980680802489609207L;

    private List<FieldMessage> erros = new ArrayList<>();

    public void addError(String fieldName, String message) {
        erros.add(new FieldMessage(fieldName, message));
    }

    public List<FieldMessage> getErros() {
        return erros;
    }
}
