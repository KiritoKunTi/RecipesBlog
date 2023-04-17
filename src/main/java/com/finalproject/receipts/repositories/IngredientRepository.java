package com.finalproject.receipts.repositories;

import com.finalproject.receipts.models.Ingredient;

import java.util.List;

public interface IngredientRepository {
    void save(Ingredient ingredient);
    List<Ingredient> findIngredientsByReceiptID(long receiptID);
}
