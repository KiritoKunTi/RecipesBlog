package com.finalproject.receipts.repositories;

import com.finalproject.receipts.models.Receipt;

import java.util.List;

public interface ReceiptRepository {
    void save(Receipt receipt);

    List<Receipt> findAll();
    Receipt findByID(long id);

}
