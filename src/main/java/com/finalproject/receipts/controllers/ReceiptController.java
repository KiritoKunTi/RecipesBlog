package com.finalproject.receipts.controllers;

import com.finalproject.receipts.models.Receipt;
import com.finalproject.receipts.repositories.ReceiptRepository;
import com.finalproject.receipts.security.Authentication;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/api/receipt")
public class ReceiptController {
    ReceiptRepository receiptRepository;
    public ReceiptController(ReceiptRepository receiptRepository){
        this.receiptRepository = receiptRepository;
    }
    @PostMapping
    public Object addReceipt(@RequestBody Receipt receipt, @CookieValue(name = "refresh_token") String refreshToken, @CookieValue(name = "access_token") String accessToken, HttpServletResponse response){
        if (!Authentication.checkUser(accessToken, refreshToken, response)){
            return Authentication.needAuthenticationResponse();
        }
        receipt.setUserID(Authentication.getUserID(accessToken, refreshToken));
        receiptRepository.save(receipt);
        return receipt;
    }
}
