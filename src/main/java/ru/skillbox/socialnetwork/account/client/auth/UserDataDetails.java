package ru.skillbox.socialnetwork.account.client.auth;

import java.util.UUID;

public interface UserDataDetails {
    UUID getUserId();
    String getEmail();

    // В будущем можно добавить обработку роли пользователя
}
