package com.mertalptekin.springrestapplication.sample.payment;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;


// Payment Servisi parametre bazlı çalıştıracak olan Manager Servis

@Component
public class PaymentService {

    private final IPayment payment;

    public PaymentService(@Qualifier("creditPayment") IPayment payment) {
        this.payment = payment;
    }

    public void pay() {
        payment.Pay( BigDecimal.valueOf(100) );
    }

}
