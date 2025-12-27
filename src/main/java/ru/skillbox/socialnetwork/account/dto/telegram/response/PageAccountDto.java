package ru.skillbox.socialnetwork.account.dto.telegram.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class PageAccountDto {

    private Long totalElements;
    private Integer totalPages;
    private Integer size;
    private List<AccountTelegramResponse> content;
    private Integer number;

}
