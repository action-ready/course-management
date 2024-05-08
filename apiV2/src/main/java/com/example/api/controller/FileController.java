package com.example.api.controller;


import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/files")
public class FileController {


    @GetMapping("/excel/templates/Import")
    public void getImport(HttpServletResponse response) throws IOException {
        try {
            File file = new File("src/main/resources/templates/StudentTemplate.xlsx");
            if (file.exists()) {
                response.setContentType("application/octet-stream");
                response.setHeader("Content-Disposition", "attachment; filename=" + file.getName());
                Files.copy(file.toPath(), response.getOutputStream());
            } else {
                response.sendError(HttpServletResponse.SC_NOT_FOUND);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            response.getOutputStream().flush();
        }
    }
}