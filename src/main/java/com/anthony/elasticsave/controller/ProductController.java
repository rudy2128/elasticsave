package com.anthony.elasticsave.controller;

import com.anthony.elasticsave.model.Product;
import com.anthony.elasticsave.service.ProductService;
import com.anthony.elasticsave.utils.MinioFileUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/")
public class ProductController {

    @Autowired
    ProductService productService;

    @Autowired
    private MinioFileUtil minioFileUtil;


    @CrossOrigin
    @PostMapping(path = "/upload", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public Map<String, String> uploadFile(@RequestPart(value = "file", required = false) MultipartFile files) throws IOException {
        minioFileUtil.uploadFile(files.getOriginalFilename(), files.toString());
        Map<String, String> result = new HashMap<>();
        result.put("imageUrl", files.getOriginalFilename());
        return result;
    }

    @CrossOrigin
    @PostMapping(value = "/save-product")
    public void saveProduct(@RequestBody Product product) throws IOException {
        productService.createProductIndex(product);


    }

    @CrossOrigin
    @PostMapping("/save-bulk")
    public void saveProductBulk(@RequestBody List<Product>productList)throws IOException{
        productService.createProductIndexBulk(productList);
    }

    @CrossOrigin
    @GetMapping("getAll")
    public Iterable<Product>getAll()throws IOException{
        return productService.findAll();
    }

    @CrossOrigin
    @GetMapping("/get-name-containing")
    public List<Product>getNameContaining(@RequestHeader String name)throws IOException{
        List<Product>responseList = new ArrayList<>();
        responseList.addAll(productService.findByNameContaining(name));
        return responseList;
    }


    @CrossOrigin
    @DeleteMapping("/delete-by-id")
    public void deleteById(@RequestHeader String id)throws IOException{
        productService.deleteById(id);
    }


}
