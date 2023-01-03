package com.anthony.elasticsave.repo;

import com.anthony.elasticsave.model.Product;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import java.util.List;

public interface ProductRepository extends ElasticsearchRepository<Product,String> {

    List<Product> findByNameContaining(String name);

    void deleteById(String id);
}
