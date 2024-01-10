package com.ats.cv;

import java.io.IOException;
import java.util.List;

import lombok.RequiredArgsConstructor;
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

    @GetMapping("/files")
    public ResponseEntity<List<CV>> getAll() {
        return cvService.getAll();
    }

    @PostMapping("/upload")
    public ResponseEntity<String> uploadCV(@RequestParam("cv") MultipartFile file) throws IOException {
        return cvService.addCV(file);
    }
}
