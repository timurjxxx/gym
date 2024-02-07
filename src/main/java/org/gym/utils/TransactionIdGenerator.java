package org.gym.utils;

import java.util.UUID;

public class TransactionIdGenerator {

    public static String generateTransactionId() {
        return UUID.randomUUID().toString();
    }
}
