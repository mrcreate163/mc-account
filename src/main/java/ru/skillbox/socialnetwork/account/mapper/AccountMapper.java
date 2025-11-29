package ru.skillbox.socialnetwork.account.mapper;

import org.mapstruct.Mapper;
import ru.skillbox.socialnetwork.account.dto.request.CreatedAccountRequest;
import ru.skillbox.socialnetwork.account.dto.response.AccountDto;
import ru.skillbox.socialnetwork.account.model.Account;

import java.util.List;

@Mapper(componentModel = "spring")
public interface AccountMapper {
    Account toEntity(AccountDto dto);
    Account toEntity(CreatedAccountRequest dto);
    AccountDto toDto(Account account);
    List<AccountDto> toDto(List<Account> accounts);
}
