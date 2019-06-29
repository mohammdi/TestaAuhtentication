package com.keyob.payment.gateway.model;


import java.io.Serializable;

public class PerPaymentCorrelationDto  implements Serializable {

    private String  CorrelationId;

    public String getCorrelationId() {
        return CorrelationId;
    }

    public void setCorrelationId(String correlationId) {
        CorrelationId = correlationId;
    }
}
