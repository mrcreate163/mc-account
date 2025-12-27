package ru.skillbox.socialnetwork.account.dto.telegram.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AccountTelegramResponse {

    private UUID id;
    private String email;
    private String phone;
    private String photo;
    private String about;
    private String city;
    private String country;
    private String firstName;
    private String lastName;
    private LocalDateTime regDate;
    private LocalDate birthDate;
    private LocalDateTime lastOnlineTime;
    private boolean isOnline;
    private boolean isBlocked;
    private boolean isDeleted;

}
