package com.akashkumar.unu.Utilities.Config;

import com.cloudinary.Cloudinary;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class cloudinaryConfig {
    @Bean
    public Cloudinary cloudinary(){
        Map<String, String> config = new HashMap<>();
        config.put("cloud_name", "dbajmphxn");
        config.put("api_key", "497382877766585");
        config.put("api_secret", "GAYD9YqJwPZxOPN6BtwfPI7exVc");
        return new Cloudinary(config);
    }
}
