package com.mertalptekin.springrestapplication.sample.payment;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

// Beanlere isim verdik.
@Component("creditPayment")
@Primary // Default olarak uygulama buradan çalışır eğer bean ismi gönderisek bu durumda ovveride ederek.
public class CreditPayment implements IPayment {
    @Override
    public void Pay(BigDecimal amount) {
        System.out.println("Paid with credit card: " + amount);
    }
}
