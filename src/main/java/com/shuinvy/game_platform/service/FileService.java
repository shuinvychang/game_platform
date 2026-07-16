package com.shuinvy.game_platform.service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@Service
public class FileService {

    private final String uploadDir = "upload";

    public String save(MultipartFile file) throws IOException {
        // Create upload directory if it doesn't exist
        Path uploadPath = Paths.get(uploadDir);
        if (!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
        }
        String originalName = file.getOriginalFilename();
        String extension = "";
        if (originalName != null && originalName.contains(".")) {
            extension = originalName.substring(originalName.lastIndexOf("."));
        }
        String fileName = UUID.randomUUID() + extension;
        // Save to uplaod directory
        Path target = uploadPath.resolve(fileName);
        Files.copy(file.getInputStream(), target);
        return fileName;
    }

    public boolean delete(String fileName) throws IOException {
        Path path = Paths.get(uploadDir, fileName);
        return Files.deleteIfExists(path);
    }
}
