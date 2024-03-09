package com.techchallenge.infrastructure.gateway;

import com.techchallenge.application.gateway.NotifyCustomerGateway;
import com.techchallenge.domain.entity.NotifyCustomer;
import com.techchallenge.infrastructure.persistence.entity.NotifyCustomerEntity;
import com.techchallenge.infrastructure.persistence.mapper.NotifyCustomerMapper;
import com.techchallenge.infrastructure.persistence.repository.NotifyCustomerRespository;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class NotifyCustomerRepositoryGateway implements NotifyCustomerGateway {

    private final NotifyCustomerRespository notifyCustomerRespository;

    private final NotifyCustomerMapper notifyCustomerMapper;

    public NotifyCustomerRepositoryGateway(NotifyCustomerRespository notifyCustomerRespository,
                                           NotifyCustomerMapper notifyCustomerMapper) {
        this.notifyCustomerRespository = notifyCustomerRespository;
        this.notifyCustomerMapper = notifyCustomerMapper;
    }

    @Override
    public NotifyCustomer insert(NotifyCustomer notifyCustomer) {
        NotifyCustomerEntity notifyCustomerEntity  = notifyCustomerRespository
                .save(notifyCustomerMapper.toNotifyCustomerEntity(notifyCustomer));

        return notifyCustomerMapper.toNotifyCustomer(notifyCustomerEntity) ;
    }

    @Override
    public List<NotifyCustomer> findNotSent() {
        return notifyCustomerMapper.toNotifyCustomers(notifyCustomerRespository.findNotSent()) ;
    }
}
