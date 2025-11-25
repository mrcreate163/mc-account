package ru.skillbox.socialnetwork.account.dto.request;

import lombok.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AccountSearchDto {
    private List<UUID> ids;
    private String author;
    private String firstName;
    private String lastName;
    private LocalDateTime birthDateFrom;
    private LocalDateTime birthDateTo;
    private String city;
    private String country;
    private Boolean isBlocked;
    private Boolean isDeleted;
    private Integer ageTo;
    private Integer ageFrom;
}
