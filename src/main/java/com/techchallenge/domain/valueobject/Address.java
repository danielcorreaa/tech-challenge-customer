package com.techchallenge.domain.valueobject;

public class Address {

    private String city;
    private String cep;
    private String state;

    private String street;
    private String number;
    private String neighborhood;

    public Address(String city, String cep, String state, String street, String number, String neighborhood) {
        this.city = city;
        this.cep = cep;
        this.state = state;
        this.street = street;
        this.number = number;
        this.neighborhood = neighborhood;
    }

    public String getCity() {
        return city;
    }

    public String getCep() {
        return cep;
    }

    public String getState() {
        return state;
    }

    public String getStreet() {
        return street;
    }

    public String getNumber() {
        return number;
    }

    public String getNeighborhood() {
        return neighborhood;
    }


    public static final class EnderecoBuilder {
        private String city;
        private String cep;
        private String state;
        private String street;
        private String number;
        private String neighborhood;

        private EnderecoBuilder() {
        }

        public static EnderecoBuilder anEndereco() {
            return new EnderecoBuilder();
        }

        public EnderecoBuilder withCity(String city) {
            this.city = city;
            return this;
        }

        public EnderecoBuilder withCep(String cep) {
            this.cep = cep;
            return this;
        }

        public EnderecoBuilder withState(String state) {
            this.state = state;
            return this;
        }

        public EnderecoBuilder withStreet(String street) {
            this.street = street;
            return this;
        }

        public EnderecoBuilder withNumber(String number) {
            this.number = number;
            return this;
        }

        public EnderecoBuilder withNeighborhood(String neighborhood) {
            this.neighborhood = neighborhood;
            return this;
        }

        public Address build() {
            return new Address(city, cep, state, street, number, neighborhood);
        }
    }
}
