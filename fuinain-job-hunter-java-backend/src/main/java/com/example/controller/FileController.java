package com.example.controller;

import com.example.service.FileService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.URISyntaxException;

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
    public void upload(
            @RequestParam("file") MultipartFile file,
            @RequestParam("type") String folder
    ) throws URISyntaxException, IOException {
        this.fileService.createDirectory(baseURI + folder);

        this.fileService.store(file, folder);
    }
}
