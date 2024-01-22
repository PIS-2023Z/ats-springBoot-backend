package com.ats.cv;

import lombok.RequiredArgsConstructor;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.bson.BsonBinarySubType;
import org.bson.types.Binary;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CVService {

    private final CVRepository cvRepo;

    public CV addCV(MultipartFile file) throws IOException {
        CV cv = new CV();
        cv.setData(new Binary(BsonBinarySubType.BINARY, file.getBytes()));
        cv = cvRepo.insert(cv);
        return cv;
    }

    public ResponseEntity<CV> getCV(String id) {
        Optional<CV> optionalCV = cvRepo.findById(id);
        if (optionalCV.isEmpty()) {
            HttpHeaders headers = new HttpHeaders();
            headers.add("response", "CV not found");
            return ResponseEntity.badRequest().headers(headers).body(null);
        }
        CV cv = optionalCV.get();
        return ResponseEntity.ok().body(cv);
    }

    public ResponseEntity<String> getCVText(String id) {
        Optional<CV> cv = cvRepo.findById(id);
        String text = "";
        try {
            Binary binaryData = cv.get().getData();
            byte[] pdfBytes = binaryData.getData();
            InputStream inputStream = new ByteArrayInputStream(pdfBytes);
            PDDocument document = PDDocument.load(inputStream);
            PDFTextStripper pdfTextStripper = new PDFTextStripper();
            text = pdfTextStripper.getText(document); // text danego cv
            document.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return ResponseEntity.ok().body(text);
    }

    public ResponseEntity<List<CV>> getAll() {
        List<CV> cvList = cvRepo.findAll();
        return ResponseEntity.ok().body(cvList);
    }
}
