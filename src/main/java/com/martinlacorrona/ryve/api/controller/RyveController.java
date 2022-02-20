package com.martinlacorrona.ryve.api.controller;

import com.martinlacorrona.ryve.api.exception.ErrorMessage;
import com.martinlacorrona.ryve.api.exception.RestException;
import org.springframework.http.HttpStatus;

public abstract class RyveController {
    public void validateRequiredParam(Object param, String paramName) {
        if (param == null || (param instanceof String && ((String) param).length() == 0)) {
            throw new RestException(HttpStatus.BAD_REQUEST, ErrorMessage.MISSING_PARAMETER, new String[]{paramName});
        }
    }

    public void validateRequiredStringParamLenght(String param, String paramName, Integer min, Integer max) {
        if(param != null)
            if (param.length() < min || param.length() > max) {
                throw new RestException(HttpStatus.BAD_REQUEST, ErrorMessage.PARAMETER_MIN_MAX_CHARACTERS,
                        new String[]{paramName, min.toString(), max.toString()});
            }
    }

    public void validateRequiredDoubleParamLenght(Double param, String paramName, Integer min, Integer max) {
        if(param != null) {
            if (param < min || param > max) {
                throw new RestException(HttpStatus.BAD_REQUEST, ErrorMessage.PARAMETER_MIN_MAX_QUANTITY,
                        new String[]{paramName, min.toString(), max.toString()});
            }
        }
    }
}
