package com.ats.application;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional
public interface ApplicationRepository extends JpaRepository<Application, Long> {

    List<Application> findByOfferId(Long offerId);
}
