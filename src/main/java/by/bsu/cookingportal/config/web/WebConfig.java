package by.bsu.cookingportal.config.web;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

  private final String[] allowedOrigins = {
    "https://dev-sa06-cooking-2.jarvis.syberry.net",
    "https://qa-sa06-cooking-2.jarvis.syberry.net",
    "https://prod-sa06-cooking-2.jarvis.syberry.net",
    "http://localhost:8080"
  };

  @Bean
  public WebMvcConfigurer corsConfigurer() {
    return new WebMvcConfigurer() {
      @Override
      public void addCorsMappings(CorsRegistry registry) {
        registry
            .addMapping("/**")
            .allowedMethods("GET", "PUT", "POST", "PATCH", "DELETE", "OPTIONS")
            .allowedHeaders("*")
            .allowedOrigins(allowedOrigins);
      }
    };
  }
}
