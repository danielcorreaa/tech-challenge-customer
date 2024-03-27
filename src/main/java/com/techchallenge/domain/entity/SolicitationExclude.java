package com.techchallenge.domain.entity;

import com.techchallenge.domain.valueobject.Cpf;
import com.techchallenge.domain.valueobject.Address;

import java.time.LocalDateTime;
import java.util.Optional;

public class SolicitationExclude {

    private Cpf cpf;
    private String name;
    private String telephone;
    private Address address;
    private LocalDateTime dateSolicitation;
    private LocalDateTime dateExclude;
    private Boolean executed;



    public SolicitationExclude(String cpf, String name, String telefone, Address address) {
        this.cpf = new Cpf(cpf);
        this.name = name;
        this.telephone = telefone;
        this.address = address;
        this.dateSolicitation = LocalDateTime.now();
        this.executed = Boolean.FALSE;
    }

    public Cpf getCpf() {
        return cpf;
    }
    public String getCpfValue() {
        return Optional.ofNullable(cpf).map(Cpf::getValue).orElse("");
    }

    public String getName() {
        return name;
    }

    public String getTelephone() {
        return telephone;
    }

    public Address getAddress() {
        return address;
    }

    public LocalDateTime getDateSolicitation() {
        return dateSolicitation;
    }

    public LocalDateTime getDateExclude() {
        return dateExclude;
    }

    public Boolean getExecuted() {
        return executed;
    }

    public SolicitationExclude toExclude(){
        this.dateExclude = LocalDateTime.now();
        this.executed = Boolean.TRUE;
        return this;
    }


    public static final class SolicitationExcludeBuilder {
        private String cpf;
        private String name;
        private String telephone;
        private Address address;
        private LocalDateTime dateSolicitation;
        private LocalDateTime dateExclude;
        private Boolean executed;

        private SolicitationExcludeBuilder() {
        }

        public static SolicitationExcludeBuilder aSolicitationExclude() {
            return new SolicitationExcludeBuilder();
        }

        public SolicitationExcludeBuilder withCpf(String cpf) {
            this.cpf = cpf;
            return this;
        }

        public SolicitationExcludeBuilder withName(String name) {
            this.name = name;
            return this;
        }

        public SolicitationExcludeBuilder withTelephone(String telephone) {
            this.telephone = telephone;
            return this;
        }

        public SolicitationExcludeBuilder withEndereco(Address address) {
            this.address = address;
            return this;
        }

        public SolicitationExcludeBuilder withDateSolicitation(LocalDateTime dateSolicitation) {
            this.dateSolicitation = dateSolicitation;
            return this;
        }

        public SolicitationExcludeBuilder withDateExclude(LocalDateTime dateExclude) {
            this.dateExclude = dateExclude;
            return this;
        }

        public SolicitationExcludeBuilder withExecuted(Boolean executed) {
            this.executed = executed;
            return this;
        }

        public SolicitationExclude build() {
            SolicitationExclude solicitationExclude = new SolicitationExclude(cpf, name, telephone, address);
            solicitationExclude.dateExclude = this.dateExclude;
            solicitationExclude.executed = this.executed;
            solicitationExclude.dateSolicitation = this.dateSolicitation;
            return solicitationExclude;
        }
    }
}
