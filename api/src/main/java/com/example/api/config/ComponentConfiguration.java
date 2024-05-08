package com.example.api.config;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ComponentConfiguration {

    @Bean
    public ModelMapper initModelMapper() {
        ModelMapper mm = new ModelMapper();
        mm.getConfiguration().setAmbiguityIgnored(true);
        return mm;
    }
}
