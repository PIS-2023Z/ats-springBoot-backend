package com.ats.cv;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface CVRepository extends MongoRepository<CV, String> {
}
