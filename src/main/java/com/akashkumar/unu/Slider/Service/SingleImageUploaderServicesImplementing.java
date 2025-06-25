package com.akashkumar.unu.Slider.Service;

import com.akashkumar.unu.Slider.interfaces.CloudinaryServices;
import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.Map;

@Service
public class SingleImageUploaderServicesImplementing implements CloudinaryServices {
    @Autowired
    private Cloudinary cloudinary;
    private String imageUrl;

    @Override
    public String uploadFile(MultipartFile multipartFile) throws Exception {
        File file = File.createTempFile("temp", multipartFile.getOriginalFilename());
        multipartFile.transferTo(file);

        Map options = ObjectUtils.asMap("folder","UNU/SliderListData", "use_filename",true, "unique_filename", true);
        Map uploadResult = cloudinary.uploader().upload(file, options);

        imageUrl =  uploadResult.get("secure_url").toString();
        return  imageUrl;
    }
}

