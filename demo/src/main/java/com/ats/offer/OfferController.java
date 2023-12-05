package com.ats.offer;

import com.ats.offer.requests.CreateOfferRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/offer/")
@RequiredArgsConstructor
public class OfferController {
    private final OfferService offerService;

    @PostMapping("add")
    public ResponseEntity<Offer> add(@RequestBody CreateOfferRequest newOffer) {
        Offer responseOffer;
        try {
            responseOffer = offerService.addOffer(newOffer);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(null);
        }
        return ResponseEntity.ok().body(responseOffer);
    }


}
