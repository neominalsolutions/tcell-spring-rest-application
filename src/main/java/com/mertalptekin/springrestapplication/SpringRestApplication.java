package com.mertalptekin.springrestapplication;

import com.mertalptekin.springrestapplication.sample.payment.PaymentService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;


@SpringBootApplication
public class SpringRestApplication {


    public static void main(String[] args) {
        ApplicationContext context = SpringApplication.run(SpringRestApplication.class, args);


        String message = context.getBean("messageBean1", String.class);
        System.out.println(message);

        // Hibernate Dialect for H2 Database ?
        PaymentService paymentService = context.getBean(PaymentService.class);
//        PaymentService paymentService1 = new PaymentService(new CashPayment());
        paymentService.pay();


        // Payment Service Locator kullanımı
        var paymentServiceLocator = context.getBean(com.mertalptekin.springrestapplication.sample.payment.PaymentServiceLocator.class);
        paymentServiceLocator.pay("creditPayment");


    }

}
