package com.ats.offer;

import com.ats.account.Account;
import com.ats.account.AccountService;
import com.ats.offer.requests.CreateOfferRequest;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
public class OfferService {

    private final AccountService accountService;
    private final OfferRepository offerRepository;
    @Transactional
    public Offer addOffer(CreateOfferRequest newOffer) {
        Offer offer = null;
        if(newOffer.getDaysToExpire() <= 0 || newOffer.getName().isEmpty()) {
            throw new IllegalStateException("Incorrect days or name: " + newOffer.getName() + newOffer.getDaysToExpire());
        }

        Account account = accountService.findById(1L);
        offer = new Offer(
                account,
                newOffer.getName(),
                LocalDateTime.now(),
                LocalDateTime.now().plusDays(newOffer.getDaysToExpire()),
                OfferStratus.GOING
        );
        try {offer.setMonthSalary(newOffer.getMonthSalary());}
        catch(Exception e) {}
        try {offer.setDescription(newOffer.getDescription());}
        catch(Exception e) {}
        offerRepository.save(offer);
        offer.getId();
        return offer;
    }
}
