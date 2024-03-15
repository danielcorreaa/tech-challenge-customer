package com.techchallenge.infrastructure.api;

import com.techchallenge.application.usecase.NotifyCustomerUseCase;
import com.techchallenge.core.response.Result;
import com.techchallenge.domain.entity.NotifyCustomer;
import com.techchallenge.infrastructure.api.dtos.NotifyCustomerResponse;
import com.techchallenge.infrastructure.api.mapper.NotifyCustomerMapper;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/notify")
@Tag(name = "Notify Customer API")
public class NotifyCustomerApi {

    private NotifyCustomerUseCase notifyCustomerUseCase;

    private NotifyCustomerMapper notifyCustomerMapper;

    public NotifyCustomerApi(NotifyCustomerUseCase notifyCustomerUseCase, NotifyCustomerMapper notifyCustomerMapper) {
        this.notifyCustomerUseCase = notifyCustomerUseCase;
        this.notifyCustomerMapper = notifyCustomerMapper;
    }

    @GetMapping("/find/{orderId}")
    public ResponseEntity<Result<NotifyCustomerResponse>> find(@PathVariable String orderId) {
        NotifyCustomer notifyCustomer = notifyCustomerUseCase.findOrderId(orderId);
        return ResponseEntity.ok(Result.ok(notifyCustomerMapper.toNotifyCustomer(notifyCustomer)));
    }
}
