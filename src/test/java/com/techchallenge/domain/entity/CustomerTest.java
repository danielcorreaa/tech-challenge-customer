package com.techchallenge.domain.entity;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EmptySource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.*;

class CustomerTest {


    @Nested
    class CpfTest {

        @Test
        void testCustomerWithValidCpf() {
            Customer customer = new Customer("37465505569", "Doug Funny", "doug@email");
            assertEquals("37465505569", customer.getCpf().getValue(), "Must Be Equals");
            assertEquals("Doug Funny", customer.getName(), "Must Be Equals");
            assertEquals("doug@email", customer.getEmail(), "Must Be Equals");
            assertEquals("374.655.055-69", customer.getFormatCpf(), "Must Be Equals");
        }

        @Test
        void testCustomerWithValidCpfRemoveEspecialCaracter() {
            Customer customer = new Customer("374.655.055-69", "Doug Funny", "doug@email");
            assertEquals("37465505569", customer.getCpf().getValue(), "Must Be Equals");
            assertEquals("Doug Funny", customer.getName(), "Must Be Equals");
            assertEquals("doug@email", customer.getEmail(), "Must Be Equals");
            assertEquals("374.655.055-69", customer.getFormatCpf(), "Must Be Equals");
        }

        @ParameterizedTest
        @ValueSource(strings = "999999999")
        void testCustomerWithInvalidCpfRepeatedDigit(String cpf) {
            IllegalArgumentException assertThrows = assertThrows(IllegalArgumentException.class,
                    () -> new Customer(cpf, "Zé Comeia", "email@email")
            );
            assertEquals("Invalid Cpf!", assertThrows.getMessage(), "Must Be Equals");
        }

        @ParameterizedTest
        @ValueSource(strings = "85236974154")
        void testCustomerWithInvalidCpf(String cpf) {
            IllegalArgumentException assertThrows = assertThrows(IllegalArgumentException.class,
                    () -> new Customer(cpf, "Zé Comeia", "email@email")
            );
            assertEquals("Invalid Cpf!", assertThrows.getMessage(), "Must Be Equals");
        }

        @ParameterizedTest
        @ValueSource(strings = "852369741548")
        void testInvalidCpfDigitSizeLargerThanAllowed(String cpf) {
            IllegalArgumentException assertThrows = assertThrows(IllegalArgumentException.class,
                    () -> new Customer(cpf, "Zé Comeia", "email@email")
            );
            assertEquals("Invalid Cpf!", assertThrows.getMessage(), "Must Be Equals");
        }

        @ParameterizedTest
        @EmptySource
        void testInvalidCpfDigitIsEmpty(String cpf) {
            IllegalArgumentException assertThrows = assertThrows(IllegalArgumentException.class,
                    () -> new Customer(cpf, "Zé Comeia", "email@email")
            );
            assertEquals("Invalid Cpf!", assertThrows.getMessage(), "Must Be Equals");
        }
    }

    @Nested
    class EmailTest{

        @Test
        void testCustomerWithValidEmail() {
            Customer customer = new Customer("82857628234", "Dino da Silva Sauro", "dino@email");
            assertEquals("82857628234", customer.getCpf().getValue(), "Must Be Equals");
            assertEquals("Dino da Silva Sauro", customer.getName(), "Must Be Equals");
            assertEquals("dino@email", customer.getEmail(), "Must Be Equals");
            assertEquals("828.576.282-34", customer.getFormatCpf(), "Must Be Equals");
        }

        @Test
        void testCustomerWithInvalidEmail() {
            IllegalArgumentException assertThrows = assertThrows(IllegalArgumentException.class,
                    () -> new Customer("82857628234", "Dino da Silva Sauro", "dinoemail")
            );
            assertEquals("Invalid Email!", assertThrows.getMessage(), "Must Be Equals");
        }

    }


}