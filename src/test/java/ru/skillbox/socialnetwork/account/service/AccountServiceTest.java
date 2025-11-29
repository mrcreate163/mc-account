package ru.skillbox.socialnetwork.account.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.skillbox.socialnetwork.account.dto.response.AccountDto;
import ru.skillbox.socialnetwork.account.mapper.AccountMapper;
import ru.skillbox.socialnetwork.account.model.Account;
import ru.skillbox.socialnetwork.account.repository.AccountRepository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class AccountServiceTest {

    @Mock
    private AccountRepository accountRepository;

    @Mock
    private AccountMapper accountMapper;

    @InjectMocks
    private AccountServiceImpl accountService;

    private List<UUID> ids;
    private List<Account> accounts;
    private List<AccountDto> accountsDTO;

    @BeforeEach
    void setUp() {
        ids = List.of(
                UUID.fromString("d8197e9c-e7b5-4b99-8dd7-10dda802f256"),
                UUID.fromString("b3356558-70bc-42a9-ad9d-c6720e10afe8"),
                UUID.fromString("4952330c-409b-4541-a516-1a71ff1f76ba")
        );
        accounts = List.of(
                new Account(UUID.fromString("d8197e9c-e7b5-4b99-8dd7-10dda802f256"), "sdf",
                        "fsd", "fsd", "dsf", "gsdf", "afsd",
                        "dgfas", "sfd", LocalDateTime.now(), LocalDate.now(),
                        LocalDateTime.now(), false, false, false, "sfads",
                        LocalDateTime.now(), LocalDateTime.now(), "fds"
                )
        );
        accountsDTO = List.of(
                new AccountDto(UUID.fromString("d8197e9c-e7b5-4b99-8dd7-10dda802f256"), "sdf",
                        "fsd", "fsd", "dsf", "gsdf", "afsd",
                        "dgfas", "sfd", LocalDateTime.now(), LocalDate.now(),
                        LocalDateTime.now(), false, false, false, "sfads",
                        LocalDateTime.now(), LocalDateTime.now(), "fds"
                )
        );
    }

    @Test
    void getAccountByIds_whenAccountsFound_shouldReturnAccountDtos() {
        // Arrange
        when(accountRepository.findAllById(ids)).thenReturn(accounts);
        when(accountMapper.toDto(accounts)).thenReturn(accountsDTO);

        // Act
        List<AccountDto> result = accountService.getAccountByIds(ids);

        // Assert
        assertThat(result).isEqualTo(accountsDTO);
    }
}
