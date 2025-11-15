package ru.skillbox.socialnetwork.account.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import ru.skillbox.socialnetwork.account.dto.AccountDTO;
import ru.skillbox.socialnetwork.account.service.AccountService;

import java.util.UUID;

@RestController("URI")
public class AccountController {

    private static final String URI = "api/v1/account";

    private final AccountService accountService;

    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getAccountById(@PathVariable UUID id) {
        AccountDTO findingAccount = accountService.findAccountById(id);

        if (findingAccount == null)
            return ResponseEntity.notFound().build();

        return ResponseEntity.ok(findingAccount);
    }

}
