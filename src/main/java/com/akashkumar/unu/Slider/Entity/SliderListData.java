package com.akashkumar.unu.Slider.Entity;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor @NoArgsConstructor
public class SliderListData {
    private String id = UUID.randomUUID().toString();
    private String caption;
    private String imageUrl;
}
