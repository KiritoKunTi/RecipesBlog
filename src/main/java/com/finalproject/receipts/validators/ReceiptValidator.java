package com.finalproject.receipts.validators;

import com.finalproject.receipts.models.Ingredient;
import com.finalproject.receipts.models.Receipt;
import com.finalproject.receipts.repositories.UserRepository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ReceiptValidator extends AbstractValidator{
    private Receipt receipt;
    private UserRepository userRepository;

    public ReceiptValidator(Receipt receipt, UserRepository userRepository) {
        this.receipt = receipt;
        this.userRepository = userRepository;
    }

    @Override
    public void check() {
        checkIngredients();
        if (!isNameValid())
            addError("name", "name length should be greater than 0");
        if (!isUserIDValid())
            addError("userID", "user with " + receipt.getUserID() + " doesn't exists");
        if (!isDescriptionValid())
            addError("description", "description length should be greater than 0");
    }

    private void checkIngredients(){
        List<Map<String, Object>> errors = new ArrayList<>();
        for (Ingredient ingredient: receipt.getIngredients()){
            IngredientValidator ingredientValidator = new IngredientValidator(ingredient);
            ingredientValidator.check();
            if (!ingredientValidator.isValid()){
                errors.add(ingredientValidator.getErrors());
            }
        }
        if (errors.size() != 0){
            addError("ingredients", errors);
        }
    }
    private boolean isNameValid(){
        return receipt.getName().length() != 0;
    }

    private boolean isUserIDValid(){
        try {
            userRepository.findByID(receipt.getUserID());
        }catch (Exception e){
            return false;
        }
        return true;
    }

    private boolean isDescriptionValid(){
        return receipt.getDescription().length() != 0;
    }



}
