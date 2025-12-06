package com.example.ModaMint_Backend.service;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.example.ModaMint_Backend.exception.AppException;
import com.example.ModaMint_Backend.exception.ErrorCode;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;
import java.util.UUID;

/**
 * Service xử lý upload ảnh lên Cloudinary
 * Không liên quan đến entity Product hay ProductVariant
 * Chỉ đơn giản upload file và trả về URL
 */
@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class ImageUploadService {

    Cloudinary cloudinary;

    // Các loại file được phép upload
    private static final String[] ALLOWED_CONTENT_TYPES = {
            "image/jpeg",
            "image/jpg",
            "image/png",
            "image/gif",
            "image/webp"
    };

    // Giới hạn kích thước file: 10MB
    private static final long MAX_FILE_SIZE = 10 * 1024 * 1024;

    /**
     * Upload ảnh lên Cloudinary
     * 
     * @param file MultipartFile từ request
     * @return URL ảnh đã upload (secure_url)
     * @throws AppException nếu có lỗi trong quá trình upload
     */
    public String uploadImage(MultipartFile file) {
        // Validate file
        validateFile(file);

        try {
            // Tạo tên file unique để tránh trùng lặp
            String publicId = generatePublicId(file.getOriginalFilename());

            // Upload lên Cloudinary
            Map<String, Object> uploadResult = cloudinary.uploader().upload(
                    file.getBytes(),
                    ObjectUtils.asMap(
                            "public_id", publicId,
                            "folder", "products", // Lưu vào folder "products" trên Cloudinary
                            "resource_type", "image",
                            "overwrite", false
                    )
            );

            // Lấy secure_url từ response
            String secureUrl = (String) uploadResult.get("secure_url");
            
            log.info("Upload image successfully: {}", secureUrl);
            
            return secureUrl;

        } catch (IOException e) {
            log.error("Failed to upload image to Cloudinary", e);
            throw new AppException(ErrorCode.IMAGE_UPLOAD_FAILED);
        }
    }

    /**
     * Validate file trước khi upload
     * - Kiểm tra file không null
     * - Kiểm tra file không rỗng
     * - Kiểm tra content type
     * - Kiểm tra kích thước file
     * 
     * @param file MultipartFile cần validate
     * @throws AppException nếu file không hợp lệ
     */
    private void validateFile(MultipartFile file) {
        // Kiểm tra file null hoặc rỗng
        if (file == null || file.isEmpty()) {
            throw new AppException(ErrorCode.FILE_EMPTY);
        }

        // Kiểm tra content type
        String contentType = file.getContentType();
        if (contentType == null || !isAllowedContentType(contentType)) {
            throw new AppException(ErrorCode.INVALID_FILE_TYPE);
        }

        // Kiểm tra kích thước file
        if (file.getSize() > MAX_FILE_SIZE) {
            throw new AppException(ErrorCode.FILE_TOO_LARGE);
        }
    }

    /**
     * Kiểm tra content type có được phép không
     * 
     * @param contentType Content type của file
     * @return true nếu được phép, false nếu không
     */
    private boolean isAllowedContentType(String contentType) {
        for (String allowedType : ALLOWED_CONTENT_TYPES) {
            if (allowedType.equalsIgnoreCase(contentType)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Tạo public_id unique cho ảnh
     * Format: timestamp_uuid_filename
     * 
     * @param originalFilename Tên file gốc
     * @return Public ID unique
     */
    private String generatePublicId(String originalFilename) {
        String timestamp = String.valueOf(System.currentTimeMillis());
        String uuid = UUID.randomUUID().toString().substring(0, 8);
        
        // Loại bỏ extension và ký tự đặc biệt
        String cleanFilename = originalFilename
                .replaceAll("\\.[^.]+$", "") // Xóa extension
                .replaceAll("[^a-zA-Z0-9]", "_"); // Thay ký tự đặc biệt bằng _
        
        return timestamp + "_" + uuid + "_" + cleanFilename;
    }

    /**
     * Xóa ảnh từ Cloudinary (optional, nếu cần)
     * 
     * @param publicId Public ID của ảnh cần xóa
     * @return true nếu xóa thành công
     */
    public boolean deleteImage(String publicId) {
        try {
            Map<String, Object> result = cloudinary.uploader().destroy(publicId, ObjectUtils.emptyMap());
            String resultStatus = (String) result.get("result");
            
            log.info("Delete image result: {}", resultStatus);
            
            return "ok".equals(resultStatus);
        } catch (IOException e) {
            log.error("Failed to delete image from Cloudinary", e);
            return false;
        }
    }
}
