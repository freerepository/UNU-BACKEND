package com.akashkumar.unu.Slider.interfaces;

import org.springframework.web.multipart.MultipartFile;

public interface CloudinaryServices {
    String uploadFile(MultipartFile multipartFile) throws Exception;
}
