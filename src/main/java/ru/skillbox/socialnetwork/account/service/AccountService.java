package ru.skillbox.socialnetwork.account.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import ru.skillbox.socialnetwork.account.dto.AccountDTO;
import ru.skillbox.socialnetwork.account.dto.AccountByFilterDTO;

import java.util.List;
import java.util.UUID;

public interface AccountService {
    List<AccountDTO> getAccountByIds(List<UUID> ids);
    AccountDTO getAccountById(UUID id);
    Page<AccountDTO> findAllAccountByIds(List<UUID> ids, Pageable pageable);
    Page<AccountDTO> searchAccounts(AccountByFilterDTO request, Pageable pageable);
    AccountDTO createAccount(AccountDTO dto);
}
