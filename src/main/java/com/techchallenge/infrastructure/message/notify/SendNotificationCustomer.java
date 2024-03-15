package com.techchallenge.infrastructure.message.notify;

import com.techchallenge.application.usecase.NotifyCustomerUseCase;
import com.techchallenge.domain.entity.NotifyCustomer;
import lombok.extern.log4j.Log4j2;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import java.util.List;

@Log4j2
@Configuration
@EnableScheduling
public class SendNotificationCustomer {

    private static final long SECOND = 1000;
    private static final long MINUTE = SECOND * 60;

    private NotifyCustomerUseCase notifyCustomerUseCase;

    public SendNotificationCustomer(NotifyCustomerUseCase notifyCustomerUseCase) {
        this.notifyCustomerUseCase = notifyCustomerUseCase;
    }

    @Scheduled(fixedDelay = MINUTE)
    public void send(){
        List<NotifyCustomer> notSent = notifyCustomerUseCase.findNotSent();
        if(notSent.isEmpty()){
            log.info("No message found for send");
        }
        notSent.forEach( notSend -> {
            log.info("send email {}", notSend);
            notSend.setNotifySent(Boolean.TRUE);
            notifyCustomerUseCase.insert(notSend);
        });
    }

}
