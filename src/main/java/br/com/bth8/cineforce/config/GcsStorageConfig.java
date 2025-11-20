package br.com.bth8.cineforce.config;

import com.google.auth.oauth2.ServiceAccountCredentials;
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.StorageOptions;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;
import java.io.InputStream;

@Configuration
@RequiredArgsConstructor
public class GcsStorageConfig {

    private final BucketConfig bucketConfig;

    @Bean
    public Storage storage() throws IOException {
        // Pega o arquivo de credenciais JSON dentro do classpath
        InputStream serviceAccount = getClass()
                .getClassLoader()
                .getResourceAsStream("cineforce-key.json");

        return StorageOptions.newBuilder()
                .setCredentials(ServiceAccountCredentials.fromStream(serviceAccount))
                .build()
                .getService();
    }
}
