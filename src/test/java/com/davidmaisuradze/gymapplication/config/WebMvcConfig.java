package com.davidmaisuradze.gymapplication.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@Configuration
@EnableWebMvc
@Import(ApplicationConfig.class)
@ComponentScan(basePackages = "com.davidmaisuradze.gymapplication")
public class WebMvcConfig {

}
