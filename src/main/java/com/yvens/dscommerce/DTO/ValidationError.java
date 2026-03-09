package com.yvens.dscommerce.DTO;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

public class ValidationError extends CustomErros {

    List<Fieldmessage> erros = new ArrayList<>();

    public ValidationError(Instant timestamp, Integer status, String error, String path) {
        super(timestamp, status, error, path);

    }

    public List<Fieldmessage> getErros() {
        return erros;
    }

    public void addError(String fieldName, String message) {
        erros.add(new Fieldmessage(fieldName, message));
    }

}
