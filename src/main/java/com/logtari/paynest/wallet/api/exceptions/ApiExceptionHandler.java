package com.logtari.paynest.wallet.api.exceptions;

import com.logtari.paynest.wallet.domain.exceptions.InsufficientFundsException;
import com.logtari.paynest.wallet.domain.exceptions.WalletNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ApiExceptionHandler {
    @ExceptionHandler(WalletNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponse handleWalletNotFound(
            WalletNotFoundException exception
    ) {
        return new ErrorResponse(
                "WALLET_NOT_FOUND",
                exception.getMessage()
        );
    }

    @ExceptionHandler(InsufficientFundsException.class)
    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    public ErrorResponse handleInsufficientFunds(
            InsufficientFundsException exception
    ) {
        return new ErrorResponse(
                "INSUFFICIENT_FUNDS",
                exception.getMessage()
        );
    }

    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleIllegalArgument(
            IllegalArgumentException exception
    ) {
        return new ErrorResponse(
                "INVALID_REQUEST",
                exception.getMessage()
        );
    }
}
