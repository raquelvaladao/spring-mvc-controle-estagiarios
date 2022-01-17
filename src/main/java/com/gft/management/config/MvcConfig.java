package com.gft.management.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.nio.file.Path;
import java.nio.file.Paths;

@Configuration
public class MvcConfig implements WebMvcConfigurer {


    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        exporDiretorio("starters-photos", registry);
    }

    private void exporDiretorio(String diretorio, ResourceHandlerRegistry registry) {
        Path diretorioUpload = Paths.get(diretorio);
        String pathUpload = diretorioUpload.toFile().getAbsolutePath();

        if (diretorio.startsWith("../")) diretorio = diretorio.replace("../", "");

        registry.addResourceHandler("/" + diretorio + "/**").addResourceLocations("file:/"+ pathUpload + "/");
    }
}