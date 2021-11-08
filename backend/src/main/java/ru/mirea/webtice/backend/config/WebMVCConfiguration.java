package ru.mirea.webtice.backend.config;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.server.ErrorPage;
import org.springframework.boot.web.server.WebServerFactoryCustomizer;
import org.springframework.boot.web.servlet.server.ConfigurableServletWebServerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.modelmapper.config.Configuration.AccessLevel;

@Configuration
public class WebMVCConfiguration implements WebMvcConfigurer {

    @Value(value = "${webtice.backend.CORS.domains}")
    private String allowedDomains;

    @Bean
    public WebServerFactoryCustomizer<ConfigurableServletWebServerFactory> webServerCustomizer() {
        return container -> container.addErrorPages(new ErrorPage(HttpStatus.NOT_FOUND, "/"));
    }

    @Bean
    public ModelMapper modelMapper() {
        ModelMapper mapper = new ModelMapper();
        mapper.getConfiguration()
                .setMatchingStrategy(MatchingStrategies.STRICT)
                .setFieldMatchingEnabled(true)
                .setSkipNullEnabled(true)
                .setFieldAccessLevel(AccessLevel.PRIVATE);
        return mapper;
    }

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins(allowedDomains.split(","));
    }

}
