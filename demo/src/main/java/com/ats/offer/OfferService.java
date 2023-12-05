package com.ats.offer;

import com.ats.account.Account;
import com.ats.account.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class OfferService {

    private final AccountService accountService;
    private final OfferRepository offerRepository;
    @Transactional
    public Offer addOffer(Offer newOffer) {
        Offer offer = null;
        LocalDateTime expiresAt;
        if(newOffer.getExpiresAt().isBefore(LocalDateTime.now()) || newOffer.getName().isEmpty()) {
            throw new IllegalStateException(
                    "Incorrect date or name: " + newOffer.getName() + newOffer.getExpiresAt()
            );
        }
        Account account = accountService.findById(1L);
        offer = new Offer(
                account,
                newOffer.getName(),
                LocalDateTime.now(),
                newOffer.getExpiresAt(),
                newOffer.getStatus()
        );
        try {offer.setMonthlySalary(newOffer.getMonthlySalary());}
        catch(Exception e) {}
        try {offer.setDescription(newOffer.getDescription());}
        catch(Exception e) {}
        offerRepository.save(offer);
        offer.getId();
        return offer;
    }

    @Transactional
    public void deleteById(Long id) {
        //TODO check if authorized
        try{
            offerRepository.deleteById(id);
        } catch (Exception e) {
            throw new IllegalArgumentException("Offer not found for id: " + id);
        }
    }

    @Transactional
    public Offer updateOffer(Offer offerToUpdate) {
        Offer oldOffer = offerRepository.findById(offerToUpdate.getId()).orElseThrow();
        oldOffer.setName(offerToUpdate.getName());
        oldOffer.setMonthlySalary(offerToUpdate.getMonthlySalary());
        oldOffer.setDescription(offerToUpdate.getDescription());
        oldOffer.setExpiresAt(offerToUpdate.getExpiresAt());
        oldOffer.setStatus(offerToUpdate.getStatus());
        offerRepository.save(oldOffer);
        return offerToUpdate;
    }
}
