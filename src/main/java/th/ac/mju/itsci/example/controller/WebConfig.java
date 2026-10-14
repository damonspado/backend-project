package th.ac.mju.itsci.example.controller;   // หรือ controller ก็ได้

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.nio.file.Paths;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("http://localhost:3000")
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
                .allowedHeaders("*")
                .allowCredentials(true);
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // ✅ ใช้ absolute path → ไม่ขึ้นกับ working directory
        String uploadPath = Paths.get("uploads").toAbsolutePath().toUri().toString();
        
        System.out.println(">>> Serving /uploads/** from: " + uploadPath);
        System.out.println(">>> Folder exists: " + java.nio.file.Files.exists(Paths.get("uploads")));
        
        System.out.println(">>> Working dir: " + System.getProperty("user.dir"));
        System.out.println(">>> Uploads exists: " + java.nio.file.Files.exists(java.nio.file.Paths.get("uploads")));
        registry.addResourceHandler("/uploads/**")
                .addResourceLocations(uploadPath);
    }
}