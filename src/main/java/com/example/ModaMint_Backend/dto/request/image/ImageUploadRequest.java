package com.example.ModaMint_Backend.dto.request.image;

import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.web.multipart.MultipartFile;

/**
 * DTO để nhận file ảnh upload từ frontend
 * Sử dụng với @ModelAttribute hoặc @RequestPart trong Controller
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ImageUploadRequest {
    
    /**
     * File ảnh được upload từ form-data
     * Frontend gửi với key "file"
     */
    MultipartFile file;
}
