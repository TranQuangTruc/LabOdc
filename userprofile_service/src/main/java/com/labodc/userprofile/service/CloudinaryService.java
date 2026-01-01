package com.labodc.userprofile.service;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;
import java.util.UUID;

@Service
@Slf4j
public class CloudinaryService {
    
    @Autowired
    private Cloudinary cloudinary;
    
    public String uploadAvatar(MultipartFile file, Long userId) throws IOException {
        if (file.isEmpty()) {
            throw new IllegalArgumentException("File is empty");
        }
        
        // Validate file type
        String contentType = file.getContentType();
        if (contentType == null || !contentType.startsWith("image/")) {
            throw new IllegalArgumentException("File must be an image");
        }
        
        // Generate unique public ID
        String publicId = "labodc/avatars/user_" + userId + "_" + UUID.randomUUID();
        
        try {
            // Upload to Cloudinary
            Map<String, Object> uploadResult = cloudinary.uploader().upload(
                    file.getBytes(),
                    ObjectUtils.asMap(
                            "public_id", publicId,
                            "folder", "labodc/avatars",
                            "transformation", ObjectUtils.asMap(
                                    "width", 400,
                                    "height", 400,
                                    "crop", "fill",
                                    "gravity", "face"
                            )
                    )
            );
            
            String url = (String) uploadResult.get("secure_url");
            log.info("Avatar uploaded successfully for user {}: {}", userId, url);
            return url;
            
        } catch (IOException e) {
            log.error("Failed to upload avatar for user {}: {}", userId, e.getMessage());
            throw new IOException("Failed to upload avatar: " + e.getMessage());
        }
    }
    
    public void deleteAvatar(String avatarUrl) {
        if (avatarUrl == null || avatarUrl.isEmpty()) {
            return;
        }
        
        try {
            // Extract public ID from URL
            String publicId = extractPublicId(avatarUrl);
            cloudinary.uploader().destroy(publicId, ObjectUtils.emptyMap());
            log.info("Avatar deleted successfully: {}", publicId);
        } catch (Exception e) {
            log.error("Failed to delete avatar: {}", e.getMessage());
        }
    }
    
    private String extractPublicId(String url) {
        // Extract public_id from Cloudinary URL
        // Example: https://res.cloudinary.com/xxx/image/upload/v123/labodc/avatars/user_1.jpg
        // Result: labodc/avatars/user_1
        int startIndex = url.indexOf("/labodc/");
        int endIndex = url.lastIndexOf(".");
        if (startIndex != -1 && endIndex != -1) {
            return url.substring(startIndex + 1, endIndex);
        }
        return url;
    }
}