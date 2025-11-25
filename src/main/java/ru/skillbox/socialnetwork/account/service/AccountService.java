package ru.skillbox.socialnetwork.account.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import ru.skillbox.socialnetwork.account.dto.AccountDto;
import ru.skillbox.socialnetwork.account.dto.request.AccountByFilterDto;
import ru.skillbox.socialnetwork.account.dto.request.AccountSearchDto;

import java.util.List;
import java.util.UUID;

public interface AccountService {
    AccountDto updateAccount(AccountDto accountDto);
    String deleteAccount(UUID userId);
    List<AccountDto> getAccountByIds(List<UUID> ids);
    AccountDto getAccountById(UUID id);
    Page<AccountDto> getAllAccountByIds(List<UUID> ids, Pageable pageable);
    Page<AccountDto> searchAccountsByFilter(AccountByFilterDto request);
    Page<AccountDto> searchAccountsByFilter(AccountSearchDto request, Pageable pageable);
    AccountDto createAccount(AccountDto dto);
    Page<AccountDto> getAllAccounts(Pageable pageable);
    boolean blockedAccountById(UUID id);
    boolean unblockAccount(UUID id);
}
