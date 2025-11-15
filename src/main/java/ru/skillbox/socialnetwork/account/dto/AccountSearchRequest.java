package ru.skillbox.socialnetwork.account.dto;

import lombok.Data;

import java.util.List;
import java.util.UUID;

@Data
public class AccountSearchRequest {
    private List<UUID> ids;
    private String author;
    private String firstName;
    private String lastName;
    private String city;
    private String country;
    private Boolean isBlocked;
    private Boolean isDeleted;
    private Integer ageTo;
    private Integer ageFrom;
}
