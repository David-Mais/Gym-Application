package com.davidmaisuradze.gymapplication.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@PropertySource("classpath:application.properties")
@ComponentScan(basePackages = "com.davidmaisuradze.gymapplication")
@EnableTransactionManagement
public class ApplicationConfig {
}
