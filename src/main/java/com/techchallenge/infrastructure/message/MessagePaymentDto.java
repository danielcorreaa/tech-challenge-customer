package com.techchallenge.infrastructure.message;

public record MessagePaymentDto(String externalReference,String orderStatus, String cpfCustomer ) {

    @Override
    public String toString() {
        return externalReference +" - "+ orderStatus +" - "+cpfCustomer;
    }
}
