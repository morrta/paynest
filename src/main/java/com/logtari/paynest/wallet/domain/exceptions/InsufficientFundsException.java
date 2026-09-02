package com.logtari.paynest.wallet.domain.exceptions;

public class InsufficientFundsException extends RuntimeException{
    public InsufficientFundsException() {
        super("Wallet has insufficient amount for withdrawal");
    }
}
