package com.akashkumar.unu.Slider.interfaces;

import com.akashkumar.unu.Slider.Entity.SliderImages;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface SliderService {
    SliderImages upload(String caption, MultipartFile file) throws IOException;
    List<SliderImages> getAll();
}
