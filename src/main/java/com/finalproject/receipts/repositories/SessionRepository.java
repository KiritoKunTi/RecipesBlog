package com.finalproject.receipts.repositories;

import com.finalproject.receipts.models.Session;
import com.finalproject.receipts.models.User;

public interface SessionRepository {
    Session save(long userID);
    Session findByUUID(String uuid);
}
