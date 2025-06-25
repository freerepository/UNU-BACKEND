package com.akashkumar.unu.Slider.Entity;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Data
@Document(collection = "Sliders")
public class SliderImages {
    @Id
    private String id ;
    private List<SliderListData> sliderList = new ArrayList<>();
}
