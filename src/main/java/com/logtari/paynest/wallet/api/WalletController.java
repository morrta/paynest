package com.logtari.paynest.wallet.api;

import com.logtari.paynest.wallet.api.mapper.MoneyMapper;
import com.logtari.paynest.wallet.api.mapper.WalletMapper;
import com.logtari.paynest.wallet.api.request.CreateWalletRequest;
import com.logtari.paynest.wallet.api.request.MoneyRequest;
import com.logtari.paynest.wallet.api.response.WalletResponse;
import com.logtari.paynest.wallet.application.service.CreateWalletService;
import com.logtari.paynest.wallet.application.service.DepositMoneyService;
import com.logtari.paynest.wallet.application.service.GetWalletService;
import com.logtari.paynest.wallet.application.service.WithdrawMoneyService;
import com.logtari.paynest.wallet.domain.*;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

import static org.springframework.http.HttpStatus.CREATED;

@RestController
@RequestMapping("/api/v1/wallets")
@AllArgsConstructor
public class WalletController {
    private final CreateWalletService createWallet;
    private final GetWalletService getWallet;
    private final DepositMoneyService depositMoney;
    private final WithdrawMoneyService withdrawMoney;

    @PostMapping
    @ResponseStatus(CREATED)
    public WalletResponse createWallet(
            @Valid @RequestBody CreateWalletRequest request){

        CustomerId requestCustomerId = new CustomerId(UUID.fromString(request.customerId()));
        Customer customer = Customer.builder()
                .customerId(requestCustomerId)
                .customerName(request.customerName())
                .build();

        Wallet wallet = createWallet.execute(customer);
        return WalletMapper.from(wallet);
    }

    @GetMapping("/{walletId}")
    public WalletResponse getWallet(
            @PathVariable UUID walletId
    ) {

        Wallet wallet = getWallet.execute(
                new WalletId(walletId)
        );

        return WalletMapper.from(wallet);
    }

    @PostMapping("/{walletId}/deposits")
    public WalletResponse deposit(
            @PathVariable UUID walletId,
            @Valid @RequestBody MoneyRequest request
    ) {

        Money money = MoneyMapper.toMoney(request);

        Wallet wallet = depositMoney.execute(
                new WalletId(walletId),
                money
        );

        return WalletMapper.from(wallet);
    }

    @PostMapping("/{walletId}/withdrawals")
    public WalletResponse withdraw(
            @PathVariable UUID walletId,
            @Valid @RequestBody MoneyRequest request
    ) {

        Money money = MoneyMapper.toMoney(request);

        Wallet wallet = withdrawMoney.execute(
                new WalletId(walletId),
                money
        );

        return WalletMapper.from(wallet);
    }


}
