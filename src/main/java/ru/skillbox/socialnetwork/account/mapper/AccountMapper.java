package ru.skillbox.socialnetwork.account.mapper;

import org.mapstruct.Mapper;
import ru.skillbox.socialnetwork.account.dto.AccountDto;
import ru.skillbox.socialnetwork.account.model.Account;

import java.util.List;

@Mapper(componentModel = "spring")
public interface AccountMapper {
    Account toEntity(AccountDto accountDTO);
    AccountDto toDto(Account account);
    List<AccountDto> toDto(List<Account> accounts);
}
