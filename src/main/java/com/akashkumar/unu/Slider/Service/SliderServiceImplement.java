package com.akashkumar.unu.Slider.Service;

import com.akashkumar.unu.Slider.Entity.SliderImages;
import com.akashkumar.unu.Slider.Entity.SliderListData;
import com.akashkumar.unu.Slider.Repository.SliderRepository;
import com.akashkumar.unu.Slider.interfaces.SliderService;
import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
public class SliderServiceImplement implements SliderService {

    @Autowired
    private Cloudinary cloudinary;

    @Autowired
    private SliderRepository sliderRepository;

    @Override
    public SliderImages upload(String caption, MultipartFile file) throws IOException {

        //create a folder
        Map options = ObjectUtils.asMap("folder","UNU/SliderListData", "use_filename",true, "unique_filename", true);
        Map uploadResult = cloudinary.uploader().upload(file.getBytes(), options);

//        Map uploadResult = cloudinary.uploader().upload(file.getBytes(), ObjectUtils.emptyMap());
        String imageUrl = uploadResult.get("secure_url").toString();

        SliderListData newSliderListData = new SliderListData();
        newSliderListData.setCaption(caption);
        newSliderListData.setImageUrl(imageUrl);

        SliderImages sliderImages = sliderRepository.findAll().stream().findFirst().orElse(null);

        if (sliderImages == null) {
            sliderImages = new SliderImages();
            sliderImages.setId(UUID.randomUUID().toString());
        }

        sliderImages.getSliderList().add(newSliderListData);
        return sliderRepository.save(sliderImages);
    }

    @Override
    public List<SliderImages> getAll() {
        return sliderRepository.findAll();
    }
}


