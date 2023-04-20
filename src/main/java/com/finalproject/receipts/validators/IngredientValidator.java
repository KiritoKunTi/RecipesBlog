package com.finalproject.receipts.validators;

import com.finalproject.receipts.models.Ingredient;
import com.finalproject.receipts.repositories.ReceiptRepository;
import com.finalproject.receipts.repositories.UserRepository;
import lombok.Data;

@Data
public class IngredientValidator extends AbstractValidator{
    private Ingredient ingredient;

    public IngredientValidator(Ingredient ingredient) {
        this.ingredient = ingredient;
    }

    @Override
    public void check() {
        if (!isNameValid())
            addError("name", "name length should be greater than 0");
        if (!isAmountValid())
            addError("amount", "amount should be greater than 0");
        if (!isMeasurementValid())
            addError("measurement", "measurement length should be greater than 0");
    }

    private boolean isAmountValid(){
        return ingredient.getAmount() > 0;
    }
    private boolean isNameValid(){
        return ingredient.getName().length() != 0;
    }

    private boolean isMeasurementValid(){
        return ingredient.getMeasurement().length() != 0;
    }
}
