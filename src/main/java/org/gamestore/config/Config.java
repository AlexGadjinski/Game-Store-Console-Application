package org.gamestore.config;

import jakarta.validation.Validation;
import jakarta.validation.Validator;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.BufferedReader;
import java.io.InputStreamReader;

@Configuration
public class Config {
    @Bean
    public ModelMapper createModelMapper() {
        return new ModelMapper();
    }

    @Bean
    public BufferedReader createBufferedReader() {
        return new BufferedReader(new InputStreamReader(System.in));
    }

    @Bean
    public Validator createValidator() {
        return Validation.buildDefaultValidatorFactory().getValidator();
//        return Validation.byDefaultProvider()
//                .configure()
//                .messageInterpolator(new ParameterMessageInterpolator())
//                .buildValidatorFactory()
//                .getValidator();
    }
}
