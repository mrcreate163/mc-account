package ru.skillbox.socialnetwork.account.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import ru.skillbox.socialnetwork.account.dto.AccountDto;
import ru.skillbox.socialnetwork.account.dto.AccountByFilterDTO;

import java.util.List;
import java.util.UUID;

public interface AccountService {
    List<AccountDto> getAccountByIds(List<UUID> ids);
    AccountDto getAccountById(UUID id);
    Page<AccountDto> getAllAccountByIds(List<UUID> ids, Pageable pageable);
    Page<AccountDto> searchAccounts(AccountByFilterDTO request, Pageable pageable);
    AccountDto createAccount(AccountDto dto);
    Page<AccountDto> getAllAccounts(Pageable pageable);
    boolean blockedAccountById(UUID id);
    boolean unblockAccount(UUID id);
}
