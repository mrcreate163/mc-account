package ru.skillbox.socialnetwork.account.controller;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.skillbox.socialnetwork.account.dto.AccountDTO;
import ru.skillbox.socialnetwork.account.service.AccountService;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/account")
public class AccountController {

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

    @GetMapping("/accountIds")
    public ResponseEntity<Page<AccountDTO>> getAccountsByIds(
            @RequestParam("ids") List<UUID> ids,
            @PageableDefault(sort = "id", direction = Sort.Direction.ASC)
            Pageable pageable
    ) {
        return ResponseEntity.ok(accountService.getAccountsByIds(ids, pageable));
    }

}
