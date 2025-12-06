package com.example.ModaMint_Backend.dto.response.image;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import lombok.experimental.FieldDefaults;

/**
 * DTO response trả về URL ảnh sau khi upload lên Cloudinary
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ImageUploadResponse {
    
    /**
     * URL ảnh đã upload lên Cloudinary
     * Frontend sẽ lấy URL này để lưu vào database
     */
    @JsonProperty("imageUrl")
    String imageUrl;
}
