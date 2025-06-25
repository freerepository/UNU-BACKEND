package com.akashkumar.unu.Slider.Response;

import com.akashkumar.unu.Slider.Entity.SliderImages;
import com.akashkumar.unu.Slider.Entity.SliderListData;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
public class SliderResponse {
    String message;
    List<SliderListData> sliderList;
}
