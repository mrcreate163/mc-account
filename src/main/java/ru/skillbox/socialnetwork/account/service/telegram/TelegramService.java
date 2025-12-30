package ru.skillbox.socialnetwork.account.service.telegram;

import org.springframework.data.domain.Pageable;
import ru.skillbox.socialnetwork.account.dto.telegram.response.AccountTelegramResponse;
import ru.skillbox.socialnetwork.account.dto.telegram.response.PageAccountDto;

import java.util.UUID;

public interface TelegramService {

    AccountTelegramResponse getAccountById(UUID id);

    PageAccountDto getAccounts(Pageable pageable);

    PageAccountDto searchAccountsByEmail(String email, Pageable pageable);

}
