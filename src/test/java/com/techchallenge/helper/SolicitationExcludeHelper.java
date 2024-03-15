package com.techchallenge.helper;

import com.techchallenge.domain.entity.SolicitationExclude;
import com.techchallenge.domain.valueobject.Address;

public class SolicitationExcludeHelper {

    public static SolicitationExclude solicitationExclude(String cpf){
        Address endereco = new Address("test", "test", "test", "test", "test", "test");
        return  new SolicitationExclude(cpf, "test", "test", endereco);
    }
}
