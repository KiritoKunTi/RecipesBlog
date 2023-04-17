package com.finalproject.receipts.validators;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

abstract class AbstractValidator {
    private Map<String, String> errors;
    AbstractValidator(){
        errors = new HashMap<>();
    }
    public abstract void check();
    public boolean isValid(){
        return errors.size() == 0;
    }

    public Map<String, String> getErrors() {
        return errors;
    }

    public void addError(String key, String err){
        errors.put(key, err);
    }
}
