package com.mertalptekin.springrestapplication.sample.payment;

import java.math.BigDecimal;

public interface IPayment {

    void Pay(BigDecimal amount);
}
