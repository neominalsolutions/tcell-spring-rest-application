package com.mertalptekin.springrestapplication.sample.payment;


import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

@Component
public class PaymentServiceLocator {

     private final ApplicationContext applicationContext;

    public PaymentServiceLocator(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    public void pay(String paymentType) {
        IPayment payment = (IPayment) applicationContext.getBean(paymentType);
        payment.Pay( java.math.BigDecimal.valueOf(200) );
    }


}
