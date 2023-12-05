package com.ats.offer;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
@Transactional
public interface OfferRepository extends JpaRepository<Offer, Long> {

    @Override
    void deleteById(Long aLong);

    @Override
    Optional<Offer> findById(Long aLong);
}
