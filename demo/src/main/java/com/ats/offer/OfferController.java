package com.ats.offer;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/offer/")
@RequiredArgsConstructor
public class OfferController {
    private final OfferService offerService;

    @PostMapping("add")
    /**
     * Requires only daysActive and name, description and monthSalary are optional
    */
    public ResponseEntity<Offer> add(@RequestBody Offer newOffer) {
        Offer responseOffer;
        try {
            responseOffer = offerService.addOffer(newOffer);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(null);
        }
        return ResponseEntity.ok().body(responseOffer);
    }

    @DeleteMapping("delete/{id}")
    public ResponseEntity<String> deleteById(@PathVariable("id") Long id) {
        try{
            offerService.deleteById(id);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("No element in database or no authorization");
        }
        return new ResponseEntity<>("Resource with ID " + id + " deleted successfully.", HttpStatus.NO_CONTENT);
    }

    @PutMapping("update")
    public ResponseEntity<Offer> update(@RequestBody Offer updatedOffer) {
        Offer updated;
        try {
            updated = offerService.updateOffer(updatedOffer);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(null);
        }
        return ResponseEntity.ok().body(updated);
    }



}
