package ru.skillbox.socialnetwork.account.controller;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.skillbox.socialnetwork.account.dto.AccountDTO;
import ru.skillbox.socialnetwork.account.dto.AccountByFilterDTO;
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

    @PostMapping
    public ResponseEntity<AccountDTO> createAccount(@RequestBody AccountDTO dto) {
        return ResponseEntity.ok(accountService.createAccount(dto));
    }

    @PostMapping("/searchByFilter")
    public ResponseEntity<Page<AccountDTO>> searchAccountsByFilter(
            @RequestBody AccountByFilterDTO filterDTO,
            Pageable pageable
    ) {
        return ResponseEntity.ok(accountService.searchAccounts(filterDTO, pageable));
    }

    @PostMapping("/find")
    public ResponseEntity<List<AccountDTO>> findAccountsByIds(@RequestBody List<UUID> ids) {
        return ResponseEntity.ok(accountService.getAccountByIds(ids));
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getAccountById(@PathVariable UUID id) {
        AccountDTO findingAccount = accountService.getAccountById(id);
        return ResponseEntity.ok(findingAccount);
    }

    @GetMapping("/search")
    public ResponseEntity<Page<AccountDTO>> searchAccounts(
            AccountByFilterDTO request,
            @PageableDefault(sort = "id", direction = Sort.Direction.ASC)
            Pageable pageable
    ) {
        return ResponseEntity.ok(accountService.searchAccounts(request, pageable));
    }

    @GetMapping("/accountIds")
    public ResponseEntity<Page<AccountDTO>> getAccountsByIds(
            @RequestParam("ids") List<UUID> ids,
            @PageableDefault(sort = "id", direction = Sort.Direction.ASC)
            Pageable pageable
    ) {
        return ResponseEntity.ok(accountService.findAllAccountByIds(ids, pageable));
    }

}
