package com.anthony.elasticsave.service;

import com.anthony.elasticsave.config.MinioConfig;
import com.anthony.elasticsave.model.Product;
import com.anthony.elasticsave.repo.ProductRepository;
import io.minio.MinioClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class ProductService {
    @Autowired
    ProductRepository productRepository;

    @Autowired
    MinioClient minioClient;

    @Autowired
    MinioConfig minioConfig;


    public void createProductIndexBulk(final List<Product>products){
        productRepository.saveAll(products);

    }
    public void createProductIndex(Product product){
        productRepository.save(product);
    }


    public Iterable<Product>findAll(){
        return productRepository.findAll();
    }


    public List<Product> findByNameContaining(String name){

        return productRepository.findByNameContaining(name);
    }

    public void deleteById(String id){
        productRepository.deleteById(id);
    }



}
