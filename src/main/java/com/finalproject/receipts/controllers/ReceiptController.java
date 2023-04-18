package com.finalproject.receipts.controllers;

import com.finalproject.receipts.models.Ingredient;
import com.finalproject.receipts.models.Receipt;
import com.finalproject.receipts.repositories.IngredientRepository;
import com.finalproject.receipts.repositories.ReceiptRepository;
import com.finalproject.receipts.repositories.UserRepository;
import com.finalproject.receipts.security.Authentication;
import com.finalproject.receipts.validators.ReceiptValidator;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.websocket.server.PathParam;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/api/receipt")
public class ReceiptController {
    ReceiptRepository receiptRepository;

    UserRepository userRepository;
    IngredientRepository ingredientRepository;
    public ReceiptController(ReceiptRepository receiptRepository, UserRepository userRepository, IngredientRepository ingredientRepository){
        this.ingredientRepository = ingredientRepository;
        this.receiptRepository = receiptRepository;
        this.userRepository = userRepository;
    }

    @GetMapping(path = "/all")
    public Object receipts(){
        List<Receipt> receipts = receiptRepository.findAll();
        for (Receipt receipt: receipts){
            receipt.setIngredients(ingredientRepository.findIngredientsByReceiptID(receipt.getId()));
        }
        return receipts;
    }
    @GetMapping(path = "/{id}")
    public Object receipt(@PathVariable("id") String id){
        Receipt receipt;
        try{
            receipt = receiptRepository.findByID(Long.parseLong(id));
        }catch(Exception e){
            return "bad request";}
        receipt.setIngredients(ingredientRepository.findIngredientsByReceiptID(receipt.getId()));
        return receipt;
    }
    @PostMapping
    public Object addReceipt(@RequestBody Receipt receipt, @CookieValue(name = "refresh_token") String refreshToken, @CookieValue(name = "access_token") String accessToken, HttpServletResponse response){
        long userID;
        try {
            userID = Authentication.getUserID(accessToken, refreshToken, response);
            if (userID == 0) {
                return Authentication.needAuthenticationResponse();
            }
        }catch (Exception exception){
            return Authentication.needAuthenticationResponse();
        }
        receipt.setUserID(userID);
        ReceiptValidator validator = new ReceiptValidator(receipt, userRepository);
        validator.check();
        if (!validator.isValid()){
            return validator.getErrors();
        }
        receiptRepository.save(receipt);
        for (Ingredient ingredient: receipt.getIngredients()){
            ingredient.setReceiptID(receipt.getId());
            ingredientRepository.save(ingredient);
        }
        return receipt;
    }
}
