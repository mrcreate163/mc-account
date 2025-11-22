package ru.skillbox.socialnetwork.account.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.skillbox.socialnetwork.account.dto.AccountDto;
import ru.skillbox.socialnetwork.account.dto.AccountByFilterDTO;
import ru.skillbox.socialnetwork.account.service.AccountService;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/account")
@RequiredArgsConstructor
public class AccountController {

    private final AccountService accountService;

    @PutMapping("/block/{id}")
    public ResponseEntity<?> blockAccountById(@PathVariable UUID id) {
        boolean isBlocked = accountService.blockedAccountById(id);

        if (isBlocked)
            return new ResponseEntity<>("Аккаунт успешно заблокирован", HttpStatus.OK);
        else
            return new ResponseEntity<>("Аккаунт не был заблокирован", HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/block/{id}")
    public ResponseEntity<?> unblockAccountById(@PathVariable UUID id) {
        boolean isUnblockAccount = accountService.unblockAccount(id);

        if (isUnblockAccount)
            return new ResponseEntity<>("Аккаунт успешно разблокирован", HttpStatus.OK);
        else
            return new ResponseEntity<>("Аккаунт не был разблокирован", HttpStatus.NOT_FOUND);
    }

    @GetMapping
    public ResponseEntity<?> getAllAccounts(
            @PageableDefault(sort = "id", direction = Sort.Direction.ASC)
            Pageable pageable
    ) {
        Page<AccountDto> accountDTO = accountService.getAllAccounts(pageable);
        return ResponseEntity.ok(accountDTO);
    }

    @PostMapping
    public ResponseEntity<?> createAccount(@RequestBody AccountDto dto) {
        AccountDto accountDTO = accountService.createAccount(dto);
        if (accountDTO == null)
            return ResponseEntity.badRequest()
                    .body(Map.of("message", "Аккаунт с id: " + dto.getId() + " уже существует"));

        return ResponseEntity.ok(accountDTO);
    }

    @PostMapping("/searchByFilter")
    public ResponseEntity<Page<AccountDto>> searchAccountsByFilter(
            @RequestBody AccountByFilterDTO filterDTO,
            Pageable pageable
    ) {
        return ResponseEntity.ok(accountService.searchAccounts(filterDTO, pageable));
    }

    @PostMapping("/find")
    public ResponseEntity<List<AccountDto>> findAccountsByIds(@RequestBody List<UUID> ids) {
        return ResponseEntity.ok(accountService.getAccountByIds(ids));
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getAccountById(@PathVariable UUID id) {
        AccountDto findingAccount = accountService.getAccountById(id);
        return ResponseEntity.ok(findingAccount);
    }

    @GetMapping("/search")
    public ResponseEntity<Page<AccountDto>> searchAccounts(
            AccountByFilterDTO request,
            @PageableDefault(sort = "id", direction = Sort.Direction.ASC)
            Pageable pageable
    ) {
        return ResponseEntity.ok(accountService.searchAccounts(request, pageable));
    }

    @GetMapping("/accountIds")
    public ResponseEntity<Page<AccountDto>> getAccountsByIds(
            @RequestParam("ids") List<UUID> ids,
            @PageableDefault(sort = "id", direction = Sort.Direction.ASC)
            Pageable pageable
    ) {
        return ResponseEntity.ok(accountService.getAllAccountByIds(ids, pageable));
    }

}
