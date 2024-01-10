package com.ats.cv;

import lombok.*;
import org.bson.types.Binary;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter
@Document(collection = "cvs")
public class CV {
    @Id
    private String id;

    private Binary data;
}
