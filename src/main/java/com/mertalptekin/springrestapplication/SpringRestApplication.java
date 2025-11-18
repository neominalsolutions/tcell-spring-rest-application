package com.mertalptekin.springrestapplication;

import com.mertalptekin.springrestapplication.sample.dto.UserDto;
import com.mertalptekin.springrestapplication.sample.model.User;
import com.mertalptekin.springrestapplication.sample.payment.CashPayment;
import com.mertalptekin.springrestapplication.sample.payment.IPayment;
import com.mertalptekin.springrestapplication.sample.payment.PaymentService;
import com.mertalptekin.springrestapplication.sample.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;

import java.time.LocalDate;

@SpringBootApplication
public class SpringRestApplication {


    public static void main(String[] args) {
        ApplicationContext context = SpringApplication.run(SpringRestApplication.class, args);

        User usr = new User(LocalDate.now());
        usr.setId(1);
        usr.setName("Mert Alptekin");

        // Spring Context'ten UserService Bean'ini alıyoruz
        // streotypelar spriing contexten class path göre okunur.
        UserService userService = context.getBean(UserService.class);

        UserDto dto = userService.convertToDto(usr);
        System.out.println(dto);

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
