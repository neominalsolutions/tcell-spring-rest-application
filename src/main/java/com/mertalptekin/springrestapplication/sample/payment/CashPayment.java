package com.mertalptekin.springrestapplication.sample.payment;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Component("cashPayment")
public class CashPayment implements IPayment {
    @Override
    public void Pay(BigDecimal amount) {
        System.out.println("Paying with Cash" + amount);
    }
}
