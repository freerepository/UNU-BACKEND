package com.akashkumar.unu.Slider.Controller;

import com.akashkumar.unu.ExceptionHandling.ApiResponse;
import com.akashkumar.unu.Slider.interfaces.CloudinaryServices;
import com.akashkumar.unu.Utilities.Urls;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

@RestController
@RequestMapping(Urls.Baseurl+"/upload")
public class SingleImageUploadController {
    @Autowired
    CloudinaryServices cloudinaryServices;
    @PostMapping
    ResponseEntity<?> uploadImage(@RequestParam("file")MultipartFile file){
        try {
            String imageUrl = cloudinaryServices.uploadFile(file);
            ApiResponse<?> apiResponse = new ApiResponse<>("Image Uploaded Successfully",imageUrl);
            return ResponseEntity.status(HttpStatus.OK).body(apiResponse);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Upload failed");
        }
    }
}
