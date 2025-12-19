package ru.skillbox.socialnetwork.account.client.auth;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;
import ru.skillbox.socialnetwork.account.client.auth.dto.UserDto;

@Slf4j
@Component
public class AuthClient {

    private static final String VALIDATE_URI = "/validate?token=";
    private static final String USER_URI = "/user";

    private final RestClient restClient;
    private final String authUrl;

    public AuthClient(
            @Value("${client.auth}") String authUrl,
            RestClient.Builder restClient
    ) {
        this.authUrl = authUrl;
        this.restClient = restClient.baseUrl(authUrl).build();
    }

    public Boolean checkValidateToken(String token) {
        try {
            String validateUrl = VALIDATE_URI + token;
            log.info("{}{}", authUrl, validateUrl);
            return restClient.get()
                    .uri(validateUrl)
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
