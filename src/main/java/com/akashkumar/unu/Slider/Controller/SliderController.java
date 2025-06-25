package com.akashkumar.unu.Slider.Controller;

import com.akashkumar.unu.ExceptionHandling.ApiResponse;
import com.akashkumar.unu.Slider.Entity.SliderImages;
import com.akashkumar.unu.Slider.Response.SliderResponse;
import com.akashkumar.unu.Slider.interfaces.SliderService;
import com.akashkumar.unu.Utilities.Urls;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping(Urls.Baseurl+"/admin/slider")
public class SliderController {

    @Autowired
    private SliderService sliderService;

    @PostMapping("/upload")
    public ResponseEntity<?> uploadOne(@RequestParam("caption") String caption, @RequestParam("file") MultipartFile file) throws IOException {
        SliderImages sliderImages =  sliderService.upload(caption, file);
        SliderResponse sliderResponse = new SliderResponse("Slider Image Uploaded Successfully", sliderImages.getSliderList());
        return ResponseEntity.status(HttpStatus.OK).body(sliderResponse);
    }

    @GetMapping
    public ResponseEntity<List<SliderImages>> getAll() {
        return ResponseEntity.ok(sliderService.getAll());
    }
}
