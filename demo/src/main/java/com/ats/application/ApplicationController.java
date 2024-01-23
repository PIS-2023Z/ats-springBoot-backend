package com.ats.application;

import com.ats.offer.Offer;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("api/application/")
@RequiredArgsConstructor
public class ApplicationController {
    private final ApplicationService applicationService;

    @GetMapping("view/{applicationId}")
    public ResponseEntity<Application> getByApplicationId(@PathVariable("applicationId") Long applicationId) {
       return applicationService.getApplication(applicationId);
    }

    @GetMapping("viewByOffer/{offerId}")
    public ResponseEntity<List<Application>> getApplicationsByOfferId(@PathVariable("offerId") Long offerId) {
        return applicationService.getAllApplicationsByOffer(offerId);
    }

    @PostMapping("apply/{offerId}")
    public ResponseEntity<Application> apply(@PathVariable("offerId") Long offerId, @RequestHeader(HttpHeaders.AUTHORIZATION) String authHeader, @ModelAttribute Application newApplication, @RequestParam("cv") MultipartFile file) {
        Application responseApplication;
        try {
            responseApplication = applicationService.apply(authHeader, newApplication, offerId, file);
            System.out.println(responseApplication.getCvId());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(null);
        }
        return ResponseEntity.ok().body(responseApplication);
    }

    @PostMapping("addApplication/{offerId}")
    public ResponseEntity<Application> addApplicationToOffer(@PathVariable("offerId") Long offerId, @RequestHeader(HttpHeaders.AUTHORIZATION) String authHeader, @ModelAttribute Application newApplication) {
        Application responseApplication;
        try {
            responseApplication = applicationService.addApplicationToOffer(authHeader, newApplication, offerId);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(null);
        }
        return ResponseEntity.ok().body(responseApplication);
    }

    @GetMapping("getAppByStr/{str}")
    public ResponseEntity<List<Application>> getAllApplicationsByStringInCV(@PathVariable("str") String str) {
        return applicationService.getAllApplicationsByStringInCV(str);
    }

    @GetMapping("get-by-employer")
    public ResponseEntity<List<Application>> getByEmployer(@RequestHeader(HttpHeaders.AUTHORIZATION) String authHeader) {
        return applicationService.getByEmployer(authHeader);
    }

    @DeleteMapping("remove/{id}")
    public ResponseEntity delete(@PathVariable("id") Long id) {
        return applicationService.delete(id);
    }

}
