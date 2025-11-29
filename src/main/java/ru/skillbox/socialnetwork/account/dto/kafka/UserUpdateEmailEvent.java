package ru.skillbox.socialnetwork.account.dto.kafka;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserUpdateEmailEvent {
    private UUID userId;
    private String newEmail;
    private LocalDateTime changedAt;
}
