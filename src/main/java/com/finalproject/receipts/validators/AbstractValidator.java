package com.finalproject.receipts.validators;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

abstract class AbstractValidator {
    private Map<String, Object> errors;
    AbstractValidator(){
        errors = new HashMap<>();
    }
    public abstract void check();
    public boolean isValid(){
        return errors.size() == 0;
    }

    public Map<String, Object> getErrors() {
        Map<String, Object> mp = new HashMap<>();
        mp.put("errors", errors);
        return mp;
    }

    protected void addError(String key, Object err){
        errors.put(key, err);
    }
}
