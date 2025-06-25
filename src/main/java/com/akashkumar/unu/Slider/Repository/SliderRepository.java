package com.akashkumar.unu.Slider.Repository;

import com.akashkumar.unu.Slider.Entity.SliderImages;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SliderRepository extends MongoRepository<SliderImages, String> {}

