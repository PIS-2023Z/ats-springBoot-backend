package com.ats.cv;

import java.io.IOException;
import java.util.List;

import lombok.RequiredArgsConstructor;
import org.bson.types.Binary;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


@Controller
@RequestMapping("api/cv/")
@RequiredArgsConstructor
public class CVController {

    private final CVService cvService;

    @GetMapping("/files/{id}")
    public ResponseEntity<CV> getCV(@PathVariable("id") String id) {
        return cvService.getCV(id);
    }

    @GetMapping("/filetext/{id}")
    public ResponseEntity<String> getCVText(@PathVariable("id") String id) {
        return cvService.getCVText(id);
    }

    @GetMapping("/files")
    public ResponseEntity<List<CV>> getAll() {
        return cvService.getAll();
    }

    @PostMapping("/upload")
    public ResponseEntity<String> uploadCV(@RequestParam("cv") MultipartFile file) throws IOException {
        CV cv = cvService.addCV(file);
        return ResponseEntity.ok().body(cv.getId());
    }

    @GetMapping("/download-bin/{id}")
    public ResponseEntity<byte[]> downloadBinary(@PathVariable("id") String id) throws IOException {
        ResponseEntity<CV> cvList = cvService.getCV(id);
        Binary cv = cvList.getBody().getData();
        byte[] binary = cv.getData();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_PDF);
        headers.setContentDispositionFormData("attachment", id + ".pdf");
        headers.setContentLength(binary.length);
        return ResponseEntity.ok().headers(headers).body(binary);
    }
}
