package br.com.bth8.cineforce.service;


import br.com.bth8.cineforce.config.BucketConfig;
import com.google.cloud.storage.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
@RequiredArgsConstructor
public class GcsService {

    private final Storage storage;
    private final BucketConfig bucketConfig;

    public String uploadFile(MultipartFile file) throws IOException {
        String fileName = bucketConfig.getSubdirectory() + "/" + file.getOriginalFilename();
        Blob blob = storage.create(
                BlobInfo.newBuilder(bucketConfig.getBucketName(), fileName).build(),
                file.getBytes()
        );
        return blob.getMediaLink();
    }


    public Blob getFile(String fileName) {
        return storage.get(bucketConfig.getBucketName(), fileName);
    }
}
