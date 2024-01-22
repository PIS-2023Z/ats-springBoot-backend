package com.ats.application;

import com.ats.account.Account;
import com.ats.account.AccountService;
import com.ats.cv.CV;
import com.ats.cv.CVService;
import com.ats.offer.Offer;
import com.ats.offer.OfferRepository;
import lombok.RequiredArgsConstructor;
import org.bson.BsonBinarySubType;
import org.bson.types.Binary;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class ApplicationService {
    private final ApplicationRepository applicationRepository;
    private final AccountService accountService;
    private final OfferRepository offerRepository;
    private final CVService cvService;

    private Application createNewApplication(String authToken, Application newApplication, Long offerId) {
        Account acc = accountService.getAccountFromToken(authToken);
        Offer offer = offerRepository.findById(offerId).orElseThrow();
        Application application = new Application();
        application.setSalary(newApplication.getSalary());
        application.setHours(newApplication.getHours());
        application.setAccount(acc);
        application.setOffer(offer);
        return application;
    }

    ResponseEntity<Application> getApplication(Long id) {
        Optional<Application> optionalApplication = applicationRepository.findById(id);
        if (optionalApplication.isEmpty()) {
            HttpHeaders headers = new HttpHeaders();
            headers.add("response", "Application not found");
            return ResponseEntity.badRequest().headers(headers).body(null);
        }
        Application application = optionalApplication.get();
        System.out.println(application.getCvId());
        System.out.println(application.getAccount());
        System.out.println(application.getOffer());
        return ResponseEntity.ok().body(application);
    }

    ResponseEntity<List<Application>> getAllApplicationsByOffer(Long offerId) {
        List<Application> applications = applicationRepository.findByOfferId(offerId);
        return ResponseEntity.ok().body(applications);
    }

    Application addApplicationToOffer(String authToken, Application newApplication, Long offerId) {
        Application application = createNewApplication(authToken, newApplication, offerId);
        application.setCvId(newApplication.getCvId());
        applicationRepository.save(application);
        return application;
    }

    Application apply(String authToken, Application newApplication, Long offerId, MultipartFile file) throws IOException {
        Application application = createNewApplication(authToken, newApplication, offerId);
        CV cv = cvService.addCV(file);
        application.setCvId(cv.getId());
        applicationRepository.save(application);
        return application;
    }
}
