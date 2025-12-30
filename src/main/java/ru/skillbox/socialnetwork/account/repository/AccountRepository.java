package ru.skillbox.socialnetwork.account.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import ru.skillbox.socialnetwork.account.model.Account;

import java.util.List;
import java.util.UUID;

@Repository
public interface AccountRepository extends JpaRepository<Account, UUID>, JpaSpecificationExecutor<Account> {
    Page<Account> findAllByIdIn(List<UUID> ids, Pageable pageable);
    Page<Account> findByEmailContainingIgnoreCase(String email, Pageable pageable);
}
