package com.ats.offer;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
@Transactional
public interface OfferRepository extends JpaRepository<Offer, Long> {

    @Query("SELECT o FROM Offer o WHERE " +
            "LOWER(o.description) LIKE LOWER(CONCAT('%', :fraze, '%')) " +
            "OR LOWER(o.name) LIKE LOWER(CONCAT('%', :fraze, '%')) " +
            "AND o.status = GOING")
    List<Offer> findByFraze(String fraze);

    @Override
    void deleteById(Long aLong);

    @Override
    Optional<Offer> findById(Long aLong);
}
