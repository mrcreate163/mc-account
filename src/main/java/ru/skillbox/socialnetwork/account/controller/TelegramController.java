package ru.skillbox.socialnetwork.account.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.skillbox.socialnetwork.account.dto.telegram.response.AccountTelegramResponse;
import ru.skillbox.socialnetwork.account.dto.telegram.response.PageAccountDto;
import ru.skillbox.socialnetwork.account.service.AccountService;
import ru.skillbox.socialnetwork.account.service.telegram.TelegramServiceImpl;

import java.util.UUID;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/internal")
public class TelegramController {

    private final TelegramServiceImpl telegramService;
    private final AccountService accountService;

    @GetMapping("/account/{id}")
    public ResponseEntity<AccountTelegramResponse> getAccountById(@PathVariable UUID id) {
        return ResponseEntity.ok(telegramService.getAccountById(id));
    }

    @GetMapping("/account")
    public ResponseEntity<PageAccountDto> getAccounts(
            @PageableDefault(sort = "id", direction = Sort.Direction.ASC)
            Pageable pageable
    ) {
        return ResponseEntity.ok(telegramService.getAccounts(pageable));
    }

    @PutMapping("/account/block/{id}")
    public ResponseEntity<?> blockingAccountById(@PathVariable UUID id) {
        accountService.blockedAccountById(id);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/account/block/{id}")
    public ResponseEntity<?> unblockingAccountById(@PathVariable UUID id) {
        accountService.unblockedAccountById(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/account/search")
    public ResponseEntity<PageAccountDto> searchAccountsByEmail(
            @RequestParam String email,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "regDate,desc") String sort
    ) {
        String[] sortParams = sort.split(",");
        String sortField = sortParams[0];
        if ("regDate".equals(sortField)) {
            sortField = "registeredAt";
        }
        Sort.Direction sortDirection = sortParams.length > 1 && sortParams[1].equalsIgnoreCase("asc") 
                ? Sort.Direction.ASC 
                : Sort.Direction.DESC;
        Pageable pageable = org.springframework.data.domain.PageRequest.of(page, size, Sort.by(sortDirection, sortField));
        return ResponseEntity.ok(telegramService.searchAccountsByEmail(email, pageable));
    }
}
