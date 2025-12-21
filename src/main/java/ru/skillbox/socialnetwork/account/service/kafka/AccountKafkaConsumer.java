package ru.skillbox.socialnetwork.account.service.kafka;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import ru.skillbox.socialnetwork.account.dto.kafka.UserRegisteredEvent;
import ru.skillbox.socialnetwork.account.service.AccountService;

@Slf4j
@RequiredArgsConstructor
@Service
public class AccountKafkaConsumer {

    private final AccountService accountService;

    /**
     * Создает аккаунт по ивенту REGISTER_TOP, который инициирует auth-сервис в эндпоинте /register
     * @param record - параметр хранящий тело ивента
     */
    @KafkaListener(topics = "REGISTER_TOP", groupId = "account_service")
    public void consumeAccountCreate(ConsumerRecord<String, UserRegisteredEvent> record) {
        UserRegisteredEvent event = record.value();

        accountService.createAccountAnEvent(event);

        log.info("Account created! email={}, userId={}, firstName={}, secondName={}",
                event.getEmail(), event.getUserId(), event.getFirstName(), event.getLastName());
    }

}
