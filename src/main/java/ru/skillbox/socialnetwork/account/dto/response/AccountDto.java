package ru.skillbox.socialnetwork.account.dto.response;

import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AccountDto {
    private UUID id;
    private String email;
    private String firstName;
    private String lastName;
    private LocalDateTime registeredAt;
    private LocalDate birthDate;
    private LocalDateTime lastOnlineTime;
    private LocalDateTime createdOn;
    private LocalDateTime updatedOn;
    private boolean isOnline;
    private boolean isBlocked;
    private boolean isDeleted;
    private String phone;
    private String photo;
    private String about;
    private String city;
    private String country;
    private String photoName;
    private String emojiStatus;
}
