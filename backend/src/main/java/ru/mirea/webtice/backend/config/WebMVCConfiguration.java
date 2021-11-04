//package ru.mirea.webtice.backend.config;
//
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.boot.web.server.WebServerFactoryCustomizer;
//import org.springframework.boot.web.servlet.server.ConfigurableServletWebServerFactory;
//import org.springframework.context.annotation.Bean;
//import org.springframework.http.HttpStatus;
//import org.springframework.web.servlet.config.annotation.CorsRegistry;
//import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
//
//public class WebMVCConfiguration implements WebMvcConfigurer {
//
////    @Value(value = "${webtice.backend.CORS.domains}")
////    private String allowedDomains;
////
////    @Bean
////    public WebServerFactoryCustomizer<ConfigurableServletWebServerFactory> webServerCustomizer() {
////        return container -> container.addErrorPages(new ErrorPage(HttpStatus.NOT_FOUND, "/"));
////    }
////
////    @Bean
////    public ModelMapper modelMapper() {
////        ModelMapper mapper = new ModelMapper();
////        mapper.getConfiguration()
////                .setMatchingStrategy(MatchingStrategies.STRICT)
////                .setFieldMatchingEnabled(true)
////                .setSkipNullEnabled(true)
////                .setFieldAccessLevel(AccessLevel.PRIVATE);
////        return mapper;
////    }
////
////    @Override
////    public void addCorsMappings(CorsRegistry registry) {
////        registry.addMapping("/**")
////                .allowedOrigins(allowedDomains.split(","));
////    }
//
//}
