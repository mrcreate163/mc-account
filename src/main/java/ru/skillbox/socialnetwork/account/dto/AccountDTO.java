package ru.skillbox.socialnetwork.account.dto;

import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AccountDTO {
    private UUID id;
    private String email;
    private String phoneNumber;
    private String photo;
    private String about;
    private String city;
    private String country;
    private String firstName;
    private String lastName;
    private LocalDateTime registrationDate;
    private LocalDate birthDate;
    private LocalDateTime lastOnlineTime;
    private Boolean isOnline;
    private Boolean isBlocked;
    private Boolean isDeleted;
    private String photoName;
    private LocalDateTime createdOn;
    private LocalDateTime updatedOn;
    private String emojiStatus;
}
