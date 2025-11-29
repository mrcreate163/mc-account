package ru.skillbox.socialnetwork.account.client.auth.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.skillbox.socialnetwork.account.client.auth.UserDataDetails;

import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserDto implements UserDataDetails {
    private UUID userId;
    private String email;
}
