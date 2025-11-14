package com.example.controller;

import com.example.domain.response.file.ResUploadFileDTO;
import com.example.service.FileService;
import com.example.util.annotation.ApiMessage;
import com.example.util.error.StorageException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.URISyntaxException;
import java.time.Instant;
import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class FileController {
    private final FileService fileService;

    public FileController(FileService fileService) {
        this.fileService = fileService;
    }

    @Value("${fuinain.upload-file.base-uri}")
    private String baseURI;

    @PostMapping("/files")
    @ApiMessage("upload file")
    public ResponseEntity<ResUploadFileDTO> upload(
            @RequestParam("file") MultipartFile file,
            @RequestParam("folder") String folder
    ) throws URISyntaxException, IOException, StorageException {
        // Validate
        if (file == null || file.isEmpty()) throw new StorageException("File is empty.");
        String fileName = file.getOriginalFilename();
        List<String> allowedExtensions = Arrays.asList("pdf", "jpg", "jpeg", "png", "doc", "docx");
        boolean isValid = allowedExtensions.stream().anyMatch(i -> fileName.toLowerCase().endsWith(i));
        if (!isValid) throw new StorageException("File type is not allowed.");

        // Create directory if not exist
        this.fileService.createDirectory(baseURI + folder);

        // Store file
        String uploadFile = this.fileService.store(file, folder);

        // Prepare response
        ResUploadFileDTO res = new ResUploadFileDTO();
        res.setFileName(uploadFile);
        res.setUploadedAt(Instant.now());
        return  ResponseEntity.ok().body(res);
    }
}
