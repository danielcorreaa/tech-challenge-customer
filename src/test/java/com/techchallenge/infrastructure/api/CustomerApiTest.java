package com.techchallenge.infrastructure.api;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.techchallenge.application.gateway.CustomerGateway;
import com.techchallenge.application.usecase.CustomerUseCase;
import com.techchallenge.application.usecase.interactor.CustomerUseCaseInteractor;
import com.techchallenge.domain.entity.Customer;
import com.techchallenge.core.exceptions.handler.ExceptionHandlerConfig;
import com.techchallenge.infrastructure.api.dtos.CustomerRequest;
import com.techchallenge.infrastructure.api.dtos.CustomerResponse;
import com.techchallenge.infrastructure.api.mapper.CustomerMapper;
import com.techchallenge.infrastructure.gateway.CustomerRepositoryGateway;
import com.techchallenge.infrastructure.persistence.entity.CustomerEntity;
import com.techchallenge.infrastructure.persistence.mapper.CustomerEntityMapper;
import com.techchallenge.infrastructure.persistence.repository.CustomerRepository;
import com.techchallenge.core.response.JsonUtils;
import com.techchallenge.core.response.Result;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
class
CustomerApiTest {

    private MockMvc mockMvc;
    private CustomerApi customerApi;
    private CustomerUseCase customerUseCase;
    private CustomerGateway customerGateway;

    @MockBean
    private CustomerRepository repository;

    private CustomerEntityMapper entityMapper;
    private CustomerMapper mapper;

    private JsonUtils jsonUtils;

    @BeforeEach
    void init() {
        ObjectMapper objectMapper = new ObjectMapper();
        jsonUtils = new JsonUtils(objectMapper);
        mapper = new CustomerMapper();
        entityMapper = new CustomerEntityMapper();
        customerGateway = new CustomerRepositoryGateway(repository, entityMapper);
        customerUseCase = new CustomerUseCaseInteractor(customerGateway);
        customerApi = new CustomerApi(customerUseCase, mapper);
        mockMvc = MockMvcBuilders.standaloneSetup(customerApi).setControllerAdvice(new ExceptionHandlerConfig()).build();
    }

    @Nested
    class TestInsertCustomer {

        @Test
        void testInsertCustomerFieldsInvalidCpf() throws Exception {
            CustomerRequest request = new CustomerRequest("12345568911", "Ze Comeia", "comeia@email.com");
            String jsonRequest = jsonUtils.toJson(request).orElse("");

            MvcResult mvcResult = mockMvc.perform(post("/customers/api/v1").contentType(MediaType.APPLICATION_JSON)
                            .content(jsonRequest))
                    .andExpect(status().isBadRequest()).andReturn();

            Optional<Result<CustomerResponse>> response = jsonUtils.parse(mvcResult.getResponse().getContentAsString(),
                    new TypeReference<Result<CustomerResponse>>() {
                    });
            int code = response.get().getCode();

            assertEquals(400, code, "Must Be Equals");
            assertEquals(List.of("Invalid Cpf!"), response.get().getErrors(), "Must Be Equals");

        }

        @Test
        void testInsertCustomerFieldsInvalidRequest() throws Exception {
            CustomerRequest request = new CustomerRequest("", "", "comeia@email.com");
            String jsonRequest = jsonUtils.toJson(request).orElse("");

            MvcResult mvcResult = mockMvc.perform(post("/customers/api/v1").contentType(MediaType.APPLICATION_JSON)
                            .content(jsonRequest))
                    .andExpect(status().isBadRequest()).andReturn();

            Optional<Result<CustomerResponse>> response = jsonUtils.parse(mvcResult.getResponse().getContentAsString(),
                    new TypeReference<Result<CustomerResponse>>() {
                    });
            int code = response.get().getCode();

            assertEquals(400, code, "Must Be Equals");
            assertEquals(List.of("Cpf is required!", "Cpf without correct number of digits!", "Name is required!"),
                    response.get().getErrors(), "Must Be Equals");


        }

        @Test
        void testInsertCustomerFieldsValid() throws Exception {
            CustomerRequest request = new CustomerRequest("02974127010", "Ze Comeia", "comeia@email.com");
            Customer customer = mapper.toCustomer(request);
            CustomerEntity entity = entityMapper.toCustomerEntity(customer);

            when(repository.save(entity)).thenReturn(entity);

            String jsonRequest = jsonUtils.toJson(request).orElse("");

            MvcResult mvcResult = mockMvc.perform(post("/customers/api/v1").contentType(MediaType.APPLICATION_JSON)
                            .content(jsonRequest))
                    .andExpect(status().isCreated()).andReturn();

            Optional<Result<CustomerResponse>> response = jsonUtils.parse(mvcResult.getResponse().getContentAsString(),
                    new TypeReference<Result<CustomerResponse>>() {
                    });

            int code = response.get().getCode();
            String cpf = response.get().getBody().cpf();
            String name = response.get().getBody().name();
            String email = response.get().getBody().email();

            assertEquals(201, code, "Must Be Equals");

            assertEquals("029.741.270-10", cpf, "Must Be Equals");
            assertEquals("Ze Comeia", name, "Must Be Equals");
            assertEquals("comeia@email.com", email, "Must Be Equals");

        }
    }

    @Nested
    class TestUpdateCustomer {
        @Test
        void testUpdatetCustomerFieldsValid() throws Exception {
            CustomerRequest request = new CustomerRequest("02974127010", "Ze Comeia", "ze@email.com");
            Customer customer = mapper.toCustomer(request);
            CustomerEntity entity = entityMapper.toCustomerEntity(customer);
            when(repository.existsById("02974127010")).thenReturn(true);
            when(repository.save(entity)).thenReturn(entity);

            String jsonRequest = jsonUtils.toJson(request).orElse("");

            MvcResult mvcResult = mockMvc.perform(put("/customers/api/v1").contentType(MediaType.APPLICATION_JSON)
                            .content(jsonRequest))
                    .andExpect(status().isOk()).andReturn();

            Optional<Result<CustomerResponse>> response = jsonUtils.parse(mvcResult.getResponse().getContentAsString(),
                    new TypeReference<Result<CustomerResponse>>() {
                    });

            int code = response.get().getCode();
            String cpf = response.get().getBody().cpf();
            String name = response.get().getBody().name();
            String email = response.get().getBody().email();

            assertEquals(200, code, "Must Be Equals");

            assertEquals("029.741.270-10", cpf, "Must Be Equals");
            assertEquals("Ze Comeia", name, "Must Be Equals");
            assertEquals("ze@email.com", email, "Must Be Equals");
        }
    }

    @Nested
    class TestFindCpfCustomer {
        @Test
        void testFindByCpf() throws Exception {
            Customer customer1 = new Customer("02974127010", "Ze Comeia", "ze@email.com");

            CustomerEntity entity = entityMapper.toCustomerEntity(customer1);

            when(repository.findByCpf("02974127010")).thenReturn(Optional.ofNullable(entity));

            MvcResult mvcResult = mockMvc.perform(get("/customers/api/v1/find/02974127010").contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk()).andReturn();

            Optional<Result<CustomerResponse>> response = jsonUtils.parse(mvcResult.getResponse().getContentAsString(),
                    new TypeReference<Result<CustomerResponse>>() {
                    });

            int code = response.get().getCode();
            String cpf = response.get().getBody().cpf();
            String name = response.get().getBody().name();
            String email = response.get().getBody().email();

            assertEquals(200, code, "Must Be Equals");

            assertEquals("029.741.270-10", cpf, "Must Be Equals");
            assertEquals("Ze Comeia", name, "Must Be Equals");
            assertEquals("ze@email.com", email, "Must Be Equals");


        }

        @Test
        void testFindByCpfNotFound() throws Exception {
            Customer customer1 = new Customer("02974127010", "Ze Comeia", "ze@email.com");

            CustomerEntity entity = entityMapper.toCustomerEntity(customer1);

            when(repository.findByCpf("02974127010")).thenReturn(Optional.ofNullable(entity));

            MvcResult mvcResult = mockMvc.perform(get("/customers/api/v1/find/123232323").contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isNotFound()).andReturn();

            Optional<Result<CustomerResponse>> response = jsonUtils.parse(mvcResult.getResponse().getContentAsString(),
                    new TypeReference<Result<CustomerResponse>>() {
                    });

            int code = response.get().getCode();

            assertEquals(404, code, "Must Be Equals");
            assertEquals(List.of("Customer not found!"), response.get().getErrors(), "Must Be Equals");


        }
    }

}