package com.example.ModaMint_Backend.controller;

import com.example.ModaMint_Backend.dto.response.ApiResponse;
import com.example.ModaMint_Backend.dto.response.image.ImageUploadResponse;
import com.example.ModaMint_Backend.service.ImageUploadService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

/**
 * Controller xử lý upload ảnh
 * Endpoint độc lập, không liên quan đến Product hay ProductVariant
 * Frontend upload ảnh trước, nhận URL, sau đó mới tạo sản phẩm
 */
@RestController
@RequestMapping("/images")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class ImageUploadController {

    ImageUploadService imageUploadService;

    /**
     * Upload ảnh lên Cloudinary
     * 
     * Endpoint: POST /api/images/upload
     * Content-Type: multipart/form-data
     * 
     * @param file File ảnh upload từ form-data với key "file"
     * @return Response chứa imageUrl
     * 
     * Example Response:
     * {
     *   "code": 1000,
     *   "message": "Upload ảnh thành công",
     *   "result": {
     *     "imageUrl": "https://res.cloudinary.com/demo/image/upload/v1234567890/products/image.jpg"
     *   }
     * }
     */
    @PostMapping(value = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ApiResponse<ImageUploadResponse> uploadImage(
            @RequestPart("file") MultipartFile file) {
        
        log.info("Uploading image: {}, size: {} bytes", 
                file.getOriginalFilename(), 
                file.getSize());

        // Upload ảnh và nhận về URL
        String imageUrl = imageUploadService.uploadImage(file);

        // Tạo response
        ImageUploadResponse response = ImageUploadResponse.builder()
                .imageUrl(imageUrl)
                .build();

        return ApiResponse.<ImageUploadResponse>builder()
                .result(response)
                .message("Upload ảnh thành công")
                .build();
    }

    /**
     * Upload nhiều ảnh cùng lúc (optional, nếu cần)
     * 
     * Endpoint: POST /api/images/upload-multiple
     * Content-Type: multipart/form-data
     * 
     * @param files Mảng các file ảnh
     * @return Response chứa danh sách imageUrls
     */
    @PostMapping(value = "/upload-multiple", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ApiResponse<java.util.List<String>> uploadMultipleImages(
            @RequestPart("files") MultipartFile[] files) {
        
        log.info("Uploading {} images", files.length);

        // Upload từng ảnh và collect URLs
        java.util.List<String> imageUrls = java.util.Arrays.stream(files)
                .map(imageUploadService::uploadImage)
                .toList();

        return ApiResponse.<java.util.List<String>>builder()
                .result(imageUrls)
                .message("Upload " + imageUrls.size() + " ảnh thành công")
                .build();
    }
}
