package click.clearline.todoapi.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    // 아래 설정 변경 필요
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins(
                    "http://localhost:5173", 
                    "http://todo.clearline.click", 
                    "https://todo.clearline.click", 
                    "http://www.clearline.click:8100", 
                    "https://www.clearline.click:8100"
                )
                .allowedMethods("GET", "POST", "PUT", "PATCH", "DELETE", "HEAD", "OPTIONS")
                .allowedHeaders("Authorization", "Cathe-Control", "Content-Type")
                .maxAge(3600);
    }

}
