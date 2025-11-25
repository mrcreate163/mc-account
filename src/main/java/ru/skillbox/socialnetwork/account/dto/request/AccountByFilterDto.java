package ru.skillbox.socialnetwork.account.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AccountByFilterDto {
    private AccountSearchDto accountSearchDto;
    private Integer pageSize;
    private Integer pageNumber;
}
