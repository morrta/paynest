package com.logtari.paynest.wallet.api;


import com.logtari.paynest.wallet.application.service.CreateWalletService;
import com.logtari.paynest.wallet.application.service.DepositMoneyService;
import com.logtari.paynest.wallet.application.service.GetWalletService;
import com.logtari.paynest.wallet.application.service.WithdrawMoneyService;
import com.logtari.paynest.wallet.domain.CustomerId;
import com.logtari.paynest.wallet.domain.Wallet;
import com.logtari.paynest.wallet.domain.WalletId;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(WalletController.class)
public class WalletControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private CreateWalletService createWallet;

    @MockitoBean
    private GetWalletService getWallet;

    @MockitoBean
    private DepositMoneyService depositMoney;

    @MockitoBean
    private WithdrawMoneyService withdrawMoney;

    @Test
    void shouldCreateWallet() throws Exception {

        UUID customerId = UUID.randomUUID();
        Wallet wallet = new Wallet(
                WalletId.generate(),
                new CustomerId(customerId)
        );

        given(createWallet.execute(any()))
                .willReturn(wallet);

        mockMvc.perform(
                        post("/api/v1/wallets")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content("""
                            {
                              "customerId": "%s",
                              "customerName": "Alice"
                            }
                            """.formatted(customerId))
                )
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.walletId").exists())
                .andExpect(jsonPath("$.balance").value("0"))
                .andExpect(jsonPath("$.currency").value("EUR"));
    }
}
