package ru.skillbox.socialnetwork.account.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import ru.skillbox.socialnetwork.account.client.auth.UserDataDetails;
import ru.skillbox.socialnetwork.account.dto.request.AccountByFilterDto;
import ru.skillbox.socialnetwork.account.dto.request.AccountSearchDto;
import ru.skillbox.socialnetwork.account.dto.request.CreatedAccountRequest;
import ru.skillbox.socialnetwork.account.dto.response.AccountDto;
import ru.skillbox.socialnetwork.account.service.AccountService;

import java.util.List;
import java.util.UUID;

@Slf4j
@RestController
@RequestMapping("/api/v1/account")
@RequiredArgsConstructor
public class AccountController {

    private final AccountService accountService;

    @GetMapping("/me")
    public ResponseEntity<AccountDto> getCurrentAccount(@AuthenticationPrincipal UserDataDetails user) {
        log.info("{}{}", user.getUserId(), user.getEmail());
        AccountDto accountDto = accountService.getAccountById(user.getUserId());
        boolean isNotNullCheck = accountDto == null;
        log.info(Boolean.toString(isNotNullCheck));
        return ResponseEntity.ok(accountDto);
    }

    @PutMapping("/me")
    public ResponseEntity<AccountDto> editCurrentAccount(@RequestBody AccountDto dto) {
        return ResponseEntity.ok(accountService.updateAccount(dto));
    }

    @DeleteMapping("/me")
    public ResponseEntity<String> deleteCurrentAccount(@AuthenticationPrincipal UserDataDetails user) {
        return ResponseEntity.ok(accountService.deleteAccount(user.getUserId()));
    }

    /**
     *
     * @param id
     * @return
     */
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
    public ResponseEntity<?> createAccount(
            @AuthenticationPrincipal UserDataDetails user,
            @RequestBody CreatedAccountRequest dto
    ) {
        AccountDto accountDTO = accountService.createAccount(dto, user);
        return ResponseEntity.ok(accountDTO);
    }

    @PostMapping("/searchByFilter")
    public ResponseEntity<Page<AccountDto>> searchAccountsByFilter(@RequestBody AccountByFilterDto filterDTO) {
        return ResponseEntity.ok(accountService.searchAccountsByFilter(filterDTO));
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
            AccountSearchDto request,
            @PageableDefault(sort = "id", direction = Sort.Direction.ASC)
            Pageable pageable
    ) {
        return ResponseEntity.ok(accountService.searchAccountsByFilter(request, pageable));
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
