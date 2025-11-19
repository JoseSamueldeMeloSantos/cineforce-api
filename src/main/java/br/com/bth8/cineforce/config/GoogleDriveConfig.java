package br.com.bth8.cineforce.config;

import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.services.drive.Drive;
import com.google.api.services.drive.DriveScopes;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.FileInputStream;
import java.util.List;

@Configuration
public class GoogleDriveConfig {

    @Bean
    public Drive driveService() throws Exception {
        var httpTransport = GoogleNetHttpTransport.newTrustedTransport();
        var jsonFactory = GsonFactory.getDefaultInstance();

        GoogleCredential credential = GoogleCredential
                .fromStream(new FileInputStream("src/main/resources/cineforce-key.json"))
                .createScoped(List.of(DriveScopes.DRIVE));

        return new Drive.Builder(httpTransport, jsonFactory, credential)
                .setApplicationName("Drive API Spring")
                .build();
    }
}
