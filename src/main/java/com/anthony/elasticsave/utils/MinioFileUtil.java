package com.anthony.elasticsave.utils;

import com.anthony.elasticsave.model.Product;
import com.anthony.elasticsave.service.ProductService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import io.minio.*;
import io.minio.errors.*;
import io.minio.messages.Bucket;
import io.minio.messages.Item;
import lombok.extern.slf4j.Slf4j;
import lombok.var;
import org.apache.commons.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class MinioFileUtil {

    @Autowired
    private MinioClient minioClient;
    @Autowired
    private ProductService productService;

    @Value("${minio.bucket.name}")
    private String defaultBucketName;

    @Value("${minio.default.folder}")
    private String defaultBaseFolder;


//    public Product uploadFileDto(Product request) {
//        try {
//            minioClient.putObject(PutObjectArgs.builder()
//                    .bucket(defaultBucketName)
//                    .object(request.getFile().getOriginalFilename())
//                    .stream(request.getFile().getInputStream(), request.getFile().getSize(), -1)
//                    .build());
//        } catch (Exception e) {
//            log.error("Happened error when upload file: ", e);
//        }
//        return Product.builder()
//                .name(request.getName())
//                .price(request.getPrice())
//                .quantity(request.getQuantity())
//                .size(request.getFile().getSize())
//                .imageUrl(getPreSignedUrl(request.getFile().getOriginalFilename()))
//                .filename(request.getFile().getOriginalFilename())
//                .build();
//
//    }


//    public List<Product> getListObjects() {
//        List<Product> objects = new ArrayList<>();
//        try {
//            Iterable<Result<Item>> result = minioClient.listObjects(ListObjectsArgs.builder()
//                    .bucket(defaultBucketName)
//                    .recursive(true)
//                    .build());
//            for (Result<Item> item : result) {
//                objects.add(Product.builder()
//                        .filename(item.get().objectName())
//                        .imageUrl(getPreSignedUrl(item.get().objectName()))
//                        .build());
//            }
//            return objects;
//        } catch (Exception e) {
//            log.error("Happened error when get list objects from minio: ", e);
//        }
//
//        return objects;
//    }

    private String getPreSignedUrl(String filename) {
        return "http://192.168.18.207:9000/example-product-image/".concat(filename);
    }




    public List<Bucket> getAllBuckets() throws RuntimeException, ServerException, InsufficientDataException,
            ErrorResponseException, IOException, NoSuchAlgorithmException, InvalidKeyException,
            InvalidResponseException, XmlParserException, InternalException {

        return minioClient.listBuckets();
    }

    // Lists maximum 100 objects information those names starts with 'E' and after
    // 'ExampleGuide.pdf'.

    public Iterable<Result<Item>>getResults() throws ServerException, InsufficientDataException, ErrorResponseException, IOException, NoSuchAlgorithmException, InvalidKeyException, InvalidResponseException, XmlParserException, InternalException {

        var results = minioClient.listObjects(
                ListObjectsArgs.builder()
                        .bucket(defaultBucketName)
                        .maxKeys(100)
                        .build());
        for (Result<Item>itemResult:results){
            ObjectMapper mapper = new ObjectMapper();
            mapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
            Item item = itemResult.get();
            System.out.println(item.lastModified() + "\t"+item.size() + "\t" + item.objectName());

        }
        return results;


    }


    public void uploadFile(String id, String encodedContent) throws IOException {

        byte[] content = decode64(encodedContent);

        String path = "C:\\decodedImage\\";
        File file = new File(path + id + ".png");

        Files.write(Paths.get(file.getAbsolutePath()),content);
        try {

            minioClient.uploadObject(UploadObjectArgs.builder()
                    .bucket(defaultBucketName)
                    .object(id)
                    .filename(file.getAbsolutePath())
                    .contentType("image/png")
                    .build());
        } catch (Exception e) {
            log.error("minio upload file exception : ", e);
        }
        log.info("minio : file with id {}, has been successfully uploaded", id);
    }


    public byte[] decode64(String encodedString) throws IOException {

        //byte[] decodedBytes = Base64.decodeBase64(encodedString);
        //Files.write(Paths.get("C:\\decodedImage\\image.png"), decodedBytes);
        if (encodedString == null) return null;
        return Base64.decodeBase64(encodedString);
    }

    public void uploadLocally(String encodedString) throws IOException {

        byte[] decodedBytes = Base64.decodeBase64(encodedString);
        Files.write(Paths.get("/Users/macbook/minio_image/"), decodedBytes);
    }
}
