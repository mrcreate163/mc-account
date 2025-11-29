package ru.skillbox.socialnetwork.account.client.auth;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;
import ru.skillbox.socialnetwork.account.client.auth.dto.UserDto;

@Component
@Slf4j
public class AuthClient {
    @Value("${client.auth}")
    private String authUrl;

    private static final String VALIDATE_URI = "/validate";
    private static final String USER_URI = "/user";

    private final RestClient restClient = RestClient.create(authUrl);

    public Boolean checkValidateToken(String token) {
        try {
            return restClient.get()
                    .uri(VALIDATE_URI)
                    .headers(it -> it.setBearerAuth(token))
                    .retrieve()
                    .toEntity(Boolean.class)
                    .getBody();
        } catch (Exception ex) {
            log.warn("Validate Token failed: {}", ex.getMessage());
            return false;
        }
    }

    public UserDto getUserByToken(String token) {
        try {
            return restClient.get()
                    .uri(USER_URI)
                    .headers(it -> it.setBearerAuth(token))
                    .retrieve()
                    .toEntity(UserDto.class)
                    .getBody();
        } catch (Exception ex) {
            log.warn("При получении пользователя по токену произошла ошибка: {}", ex.getMessage());
            return null;
        }
    }
}
