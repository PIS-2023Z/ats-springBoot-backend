package com.ats.account;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Transactional
@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {
    Optional<Account> findById(Long id);
}
