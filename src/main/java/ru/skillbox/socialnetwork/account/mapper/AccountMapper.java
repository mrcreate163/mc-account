package ru.skillbox.socialnetwork.account.mapper;

import org.mapstruct.Mapper;
import ru.skillbox.socialnetwork.account.dto.AccountDTO;
import ru.skillbox.socialnetwork.account.model.Account;

import java.util.List;

@Mapper(componentModel = "spring")
public interface AccountMapper {
    Account toEntity(AccountDTO accountDTO);
    AccountDTO toDto(Account account);
    List<AccountDTO> toDto(List<Account> accounts);
}
