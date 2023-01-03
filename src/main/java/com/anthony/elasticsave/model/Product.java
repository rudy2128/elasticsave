package com.anthony.elasticsave.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.web.multipart.MultipartFile;


@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@JsonInclude(JsonInclude.Include.NON_NULL)
@Document(indexName = "product_index")
public class Product {
    private final static long serialVersionUID = 3381379210325832090L;
    @Id
    private String id;
    private String name;
    private Double price;
    private Integer quantity;
//    @SuppressWarnings("java:S1948")
//    private MultipartFile file;
//    private Long size;
//    private String filename;
    private String imageUrl;
}
