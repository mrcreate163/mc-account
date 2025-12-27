package ru.skillbox.socialnetwork.account.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.skillbox.socialnetwork.account.dto.kafka.UserRegisteredEvent;
import ru.skillbox.socialnetwork.account.dto.request.CreatedAccountRequest;
import ru.skillbox.socialnetwork.account.dto.response.AccountDto;
import ru.skillbox.socialnetwork.account.dto.telegram.response.AccountTelegramResponse;
import ru.skillbox.socialnetwork.account.model.Account;

import java.util.List;

@Mapper(componentModel = "spring")
public interface AccountMapper {

    Account toEntity(AccountDto dto);

    Account toEntity(CreatedAccountRequest dto);

    @Mapping(target = "phone", source = "accountInfo.phone")
    @Mapping(target = "photo", source = "accountInfo.photo")
    @Mapping(target = "about", source = "accountInfo.about")
    @Mapping(target = "city", source = "accountInfo.city")
    @Mapping(target = "country", source = "accountInfo.country")
    @Mapping(target = "birthDate", source = "accountInfo.birthDate")
    @Mapping(target = "photoName", source = "accountInfo.photoName")
    @Mapping(target = "emojiStatus", source = "accountInfo.emojiStatus")
    AccountDto toDto(Account account);

    List<AccountDto> toDto(List<Account> accounts);

    @Mapping(target = "id", source = "userId")
    @Mapping(target = "createdOn", source = "registeredAt")
    @Mapping(target = "updatedOn", source = "registeredAt")
    @Mapping(target = "lastOnlineTime", source = "registeredAt")
    @Mapping(target = "isOnline", ignore = true)
    @Mapping(target = "isBlocked", ignore = true)
    @Mapping(target = "isDeleted", ignore = true)
    Account toEntity(UserRegisteredEvent event);

    @Mapping(target = "regDate", source = "registeredAt")
    AccountTelegramResponse toTelegramDto(Account account);

    List<AccountTelegramResponse> toTelegramDto(List<Account> accounts);

}
