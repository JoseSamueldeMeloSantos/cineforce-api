package br.com.bth8.cineforce.service;

import com.google.api.client.http.InputStreamContent;
import com.google.api.services.drive.Drive;
import com.google.api.services.drive.model.File;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.OutputStream;

@Service
@RequiredArgsConstructor
public class GoogleDriveService {

    private final Drive drive;


    public String uploadFile(MultipartFile multipartFile, String fileName) throws IOException {
        File fileMetadata = new File(); // Cria metadata do arquivo
        fileMetadata.setName(fileName);

        InputStreamContent mediaContent = new InputStreamContent(
                multipartFile.getContentType(), // Tipo MIME do arquivo
                multipartFile.getInputStream()  // Conteúdo do arquivo como InputStream
        );

        File uploadedFile = drive.files()  // Chamada para criar arquivo no Drive
                .create(fileMetadata, mediaContent) // Passa metadata e conteúdo
                .setFields("id, name") // Pede apenas id e nome no retorno
                .execute(); // Executa a requisição

        return uploadedFile.getId(); // Retorna o ID do arquivo criado
    }


    public void downloadFile(String fileId, OutputStream outputStream) throws IOException {
        drive.files() // Acessa a coleção de arquivos
                .get(fileId) // Seleciona o arquivo pelo ID
                .executeMediaAndDownloadTo(outputStream); // Faz download direto para o OutputStream
    }
}
