package com.example.demo.util;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Base64;

public class FileUtil {

    public static String fileToBase64(MultipartFile file) throws IOException {
        StringBuilder stringBuilder = new StringBuilder();
        byte[] fileByte = file.getBytes();
        String encode = Base64.getEncoder().encodeToString(fileByte);
        return encode;
    }

    public static String filesToBase64(MultipartFile[] files) throws IOException {
        StringBuilder stringBuilder = new StringBuilder();
        for (MultipartFile file : files) {
            byte[] fileBytes = file.getBytes();
            String base64Image = Base64.getEncoder().encodeToString(fileBytes);
            stringBuilder.append(base64Image);
        }
        return stringBuilder.toString();
    }

}
