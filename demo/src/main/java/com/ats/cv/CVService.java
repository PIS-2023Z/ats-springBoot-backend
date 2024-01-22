package com.ats.cv;


import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch.core.IndexResponse;
import co.elastic.clients.elasticsearch.core.SearchResponse;
import co.elastic.clients.elasticsearch.core.search.Hit;
import co.elastic.clients.json.jackson.JacksonJsonpMapper;
import co.elastic.clients.transport.ElasticsearchTransport;
import co.elastic.clients.transport.rest_client.RestClientTransport;
import org.apache.http.HttpHost;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import lombok.RequiredArgsConstructor;
import java.io.ByteArrayInputStream;
import org.bson.BsonBinarySubType;
import org.bson.types.Binary;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Optional;
import org.elasticsearch.client.RestClient;


@Service
@RequiredArgsConstructor
public class CVService {

    private final CVRepository cvRepo;
    // Create the low-level client
    RestClient restClient = RestClient
            .builder(HttpHost.create("http://localhost:9200"))
            .build();
    ElasticsearchTransport transport = new RestClientTransport(restClient, new JacksonJsonpMapper());
    ElasticsearchClient client = new ElasticsearchClient(transport);


    public ResponseEntity<String> addCV(MultipartFile file) throws IOException {
        CV cv = new CV();
        cv.setData(new Binary(BsonBinarySubType.BINARY, file.getBytes()));
        cv = cvRepo.insert(cv);
        String new_id = cv.getId();
        String text = "";
        try {
            Binary binaryData = cv.getData();
            byte[] pdfBytes = binaryData.getData();
            InputStream inputStream = new ByteArrayInputStream(pdfBytes);
            PDDocument document = PDDocument.load(inputStream);
            PDFTextStripper pdfTextStripper = new PDFTextStripper();
            text = pdfTextStripper.getText(document); // text danego cv
            document.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        final ElasticCV cvElastic = new ElasticCV(new_id, text);
        IndexResponse response = client.index(i -> i
                .index("ElasticCV")
                .id(new_id)
                .document(cvElastic));
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
        return ResponseEntity.ok().body(cv);
    }

    public ResponseEntity<String> getCVText(String id) throws IOException {
        Optional<CV> optionalCV = cvRepo.findById(id);
        SearchResponse searchResponse1 = client.search(s -> s
                .index("cv")
                .query(q -> q
                        .match(t -> t
                                .field("id")
                                .query(id))), ElasticCV.class);
        List<Hit<ElasticCV>> hits = searchResponse1.hits().hits();
        return ResponseEntity.ok().body(hits.get(0).source().getText());
    }

    public ResponseEntity<List<CV>> getAll() {
        List<CV> cvList = cvRepo.findAll();
        return ResponseEntity.ok().body(cvList);
    }
}
