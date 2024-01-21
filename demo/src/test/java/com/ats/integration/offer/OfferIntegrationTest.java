package com.ats.integration.offer;

import com.ats.offer.Offer;
import com.ats.offer.OfferRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
public class OfferIntegrationTest {

    @Autowired
    private OfferRepository offerRepository;

    @Test
    public void testFindNoneByRace() {
        Optional<Offer> list = offerRepository.findById(1L);
        assertTrue(list.isPresent());
    }


    @Test
    public void testFindByFraze() {
        List<Offer> offers = offerRepository.findByFraze("html");
        assertEquals(1, offers.size());
        offers = offerRepository.findByFraze("noen fraze");
        assertEquals(0, offers.size());
    }

}