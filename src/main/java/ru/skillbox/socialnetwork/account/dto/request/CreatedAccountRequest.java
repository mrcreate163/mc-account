package ru.skillbox.socialnetwork.account.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CreatedAccountRequest {
    private String phone;
    private String photo;
    private String about;
    private String city;
    private String country;
    private LocalDate birthDate;
    private String photoName;
    private String emojiStatus;
}
