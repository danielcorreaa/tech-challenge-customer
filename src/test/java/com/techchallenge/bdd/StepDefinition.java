package com.techchallenge.bdd;

import com.techchallenge.domain.entity.Customer;
import com.techchallenge.infrastructure.api.dtos.CustomerRequest;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.cucumber.java.pt.Dado;
import io.cucumber.java.pt.Entao;
import io.cucumber.java.pt.Quando;
import io.restassured.response.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import static io.restassured.RestAssured.given;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.hamcrest.Matchers.equalTo;


public class StepDefinition {

    private Response response;
    private CustomerRequest request;

    private String cpf;

    private String ENDPOINT_CUSTOMERS = "http://localhost:8085/tech-challenge-customer/customers/api/v1";

    @Dado("que quero cadastrar um cliente")
    public void que_quero_cadastrar_um_cliente() {
        request = new CustomerRequest("33672342068", "Ze Comeia", "comeia@email.com");
    }


    @Quando("informar um cpf válido e informar um email válido")
    public void informar_um_cpf_válido_e_informar_um_email_válido() {
        response = given()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(request)
                .when().post(ENDPOINT_CUSTOMERS);
    }

    @Entao("devo conseguir cadastrar cliente com sucesso")
    public void devo_conseguir_cadastrar_cliente_com_sucesso() {
        response.then()
                .statusCode(HttpStatus.CREATED.value())
                .body(matchesJsonSchemaInClasspath("./data/customer-schema.json"));
    }


    @Dado("que quero pesquisar um cliente")
    public void que_quero_pesquisar_um_cliente() {
        request = new CustomerRequest("79377085063", "Fred Flintstone","fred@email");
        given().contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(request)
                .when().post(ENDPOINT_CUSTOMERS);
        cpf = "79377085063";
    }
    @Quando("quando efetuar pesquisa")
    public void quando_efetuar_pesquisa() {
        response = given().contentType(MediaType.APPLICATION_JSON_VALUE)
                .when().get(ENDPOINT_CUSTOMERS+"/find/{cpf}",cpf );
    }
    @Entao("o cliente deve ser apresentado")
    public void o_cliente_deve_ser_apresentado() {
        response.then()
                .statusCode(HttpStatus.OK.value())
                .body(matchesJsonSchemaInClasspath("./data/customer-schema.json"))
                .body(matchesJsonSchemaInClasspath("./data/customer.json"));;
    }

    @Dado("que quero atualizar um cliente")
    public void que_quero_atualizar_um_cliente() {
        request = new CustomerRequest("33672342068", "Ze Comeia 2", "test@email.com");
    }
    @Quando("alterar nome ou email")
    public void alterar_nome_ou_email() {

        response = given()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(request)
                .when().put(ENDPOINT_CUSTOMERS);
    }
    @Entao("o cliente deve ser atualizado")
    public void o_cliente_deve_ser_atualizado() {
        response.then()
                .statusCode(HttpStatus.OK.value())
                .body(matchesJsonSchemaInClasspath("./data/customer-schema.json"));
    }

    @Dado("que quero deletar um cliente")
    public void que_quero_deletar_um_cliente() {
        cpf = "33672342068";
    }
    @Quando("informar um cpf existente")
    public void informar_um_cpf_existente() {
        response = given().contentType(MediaType.APPLICATION_JSON_VALUE)
                .when().delete(ENDPOINT_CUSTOMERS+"/delete/{cpf}",cpf );
    }
    @Entao("o cliente deve ser deletado")
    public void o_cliente_deve_ser_deletado() {
        response.then()
                .statusCode(HttpStatus.OK.value())
                .body("code", equalTo(200))
                .body("body", equalTo("Custumer delete with success!"));
    }


}
