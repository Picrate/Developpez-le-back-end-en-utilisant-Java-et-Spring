package info.patriceallary.chatop.configuration;

import info.patriceallary.chatop.domain.bo.StorageLocation;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableWebMvc
@Slf4j
public class SpringConfiguration implements WebMvcConfigurer {

    @Value("${picture.directory.location}")
    String pictureLocation;
    @Value("${picture.directory.name}")
    String pictureDirectoryName;

    @Bean
    public ModelMapper modelMapper(){
        return new ModelMapper();
    }

    /**
     * Folder location for storing pictures files
     */
    @Bean
    public StorageLocation getPictureStorageLocation() {
        return new StorageLocation(pictureLocation, pictureDirectoryName);
    }

    /*
        ResourceHandler For pictures on /picture/**
     */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry
                .addResourceHandler("/pictures/**")
                .addResourceLocations(getPictureStorageLocation().getUri().normalize().toString());
    }


    @Bean
    public OpenAPI openAPI() {
        return new OpenAPI().addSecurityItem(new SecurityRequirement().
                        addList("Bearer Authentication"))
                .components(new Components().addSecuritySchemes
                        ("Bearer Authentication", createAPIKeyScheme()))
                .info(new Info().title("Chatop Rental REST API")
                        .description("Chatop Home Rental Application OpenApi 3.0 Documentation.")
                        .version("1.0").contact(new Contact().name("Patrice ALLARY")
                                .email( "dev@allary-guillery.fr").url("https://github.com/Picrate/Developpez-le-back-end-en-utilisant-Java-et-Spring"))
                        .license(new License().name("OpenApi 3.0")
                                .url("https://swagger.io/specification/")));
    }

    private SecurityScheme createAPIKeyScheme() {
        return new SecurityScheme().type(SecurityScheme.Type.HTTP)
                .bearerFormat("JWT")
                .scheme("bearer");
    }


}
