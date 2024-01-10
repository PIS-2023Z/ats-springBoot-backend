package com.ats.cv;

import lombok.RequiredArgsConstructor;
import org.bson.BsonBinarySubType;
import org.bson.types.Binary;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CVService {

    private final CVRepository cvRepo;

    public ResponseEntity<String> addCV(MultipartFile file) throws IOException {
        CV cv = new CV();
        cv.setData(new Binary(BsonBinarySubType.BINARY, file.getBytes()));
        cv = cvRepo.insert(cv);
        return ResponseEntity.ok().body(cv.getId());
    }

    public ResponseEntity<CV> getCV(String id) {
        Optional<CV> optionalCV = cvRepo.findById(id);
        if (optionalCV.isEmpty()) {
            HttpHeaders headers = new HttpHeaders();
            headers.add("response", "CV not found");
            return ResponseEntity.badRequest().headers(headers).body(null);
        }
        CV cv = optionalCV.get();
        return ResponseEntity.ok().contentType(MediaType.APPLICATION_PDF).body(cv);
    }

    public ResponseEntity<List<CV>> getAll() {
        List<CV> cvList = cvRepo.findAll();
        return ResponseEntity.ok().body(cvList);
    }
}
