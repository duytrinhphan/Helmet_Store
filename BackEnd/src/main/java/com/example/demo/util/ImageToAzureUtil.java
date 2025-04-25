package com.example.demo.util;

import com.microsoft.azure.storage.CloudStorageAccount;
import com.microsoft.azure.storage.StorageException;
import com.microsoft.azure.storage.blob.CloudBlobClient;
import com.microsoft.azure.storage.blob.CloudBlobContainer;
import com.microsoft.azure.storage.blob.CloudBlockBlob;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileInputStream;
import java.io.IOException;
import java.net.URISyntaxException;
import java.security.InvalidKeyException;

@Component
public class ImageToAzureUtil {

    @Autowired
    private CloudBlobClient cloudBlobClient;

    @Value("${azure.storage.container-name}")
    private String containerName;

    @Value("${azure.storage.connection-string}")
    private String connectionString;

    // upload image len azure
    public String uploadImage(MultipartFile file) throws IOException, URISyntaxException, StorageException {
        String fileName = file.getOriginalFilename();
        CloudBlockBlob blob = cloudBlobClient.getContainerReference(containerName).getBlockBlobReference(fileName);
        blob.upload(file.getInputStream(), file.getSize());
        return blob.getUri().toString();
    }

    public String uploadImageToAzure(String imagePath) throws URISyntaxException, StorageException, IOException, InvalidKeyException {
        CloudStorageAccount storageAccount = CloudStorageAccount.parse(connectionString);
        CloudBlobClient blobClient = storageAccount.createCloudBlobClient();
        CloudBlobContainer container = blobClient.getContainerReference(containerName);

        String fileName = this.getImageFileName(imagePath);
        // Tạo một đối tượng CloudBlockBlob để lưu trữ ảnh trên Azure Blob Storage
        CloudBlockBlob blob = container.getBlockBlobReference(fileName);

        // Đọc dữ liệu từ tệp ảnh
        try (FileInputStream fileInputStream = new FileInputStream(imagePath)) {
            // Tải lên ảnh lên Azure Blob Storage
            blob.upload(fileInputStream, -1);
        }

        return blob.getUri().toString();
    }

    private String getImageFileName(String imageUrl) {
        // Lấy phần cuối cùng của đường dẫn làm tên tệp ảnh
        int lastIndexOfSlash = imageUrl.lastIndexOf("/");
        return imageUrl.substring(lastIndexOfSlash + 1);
    }

}
