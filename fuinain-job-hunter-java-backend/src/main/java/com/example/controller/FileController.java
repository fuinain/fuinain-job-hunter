package com.example.controller;

import com.example.domain.response.file.ResUploadFileDTO;
import com.example.service.FileService;
import com.example.util.annotation.ApiMessage;
import com.example.util.error.StorageException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileNotFoundException;
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
    public ResponseEntity<ResUploadFileDTO> upload(@RequestParam("file") MultipartFile file, @RequestParam("folder") String folder) throws URISyntaxException, IOException, StorageException {
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
        return ResponseEntity.ok().body(res);
    }

    @GetMapping("/files")
    @ApiMessage("download file")
    public ResponseEntity<Resource> download(
            @RequestParam(name = "fileName", required = false) String fileName,
            @RequestParam(name = "folder", required = false) String folder)
            throws StorageException, URISyntaxException, FileNotFoundException {
        if (fileName == null || folder == null) throw new StorageException("File name or folder is missing.");

        long fileLength = this.fileService.getFileLength(fileName, folder);
        if (fileLength == 0) throw new StorageException("File not found or is empty.");

        InputStreamResource resource = this.fileService.getResource(fileName, folder);

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + fileName + "\"")
                .contentLength(fileLength)
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .body(resource);
    }
}
