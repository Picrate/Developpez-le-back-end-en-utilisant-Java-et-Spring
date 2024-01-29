package info.patriceallary.chatop.configuration;

import info.patriceallary.chatop.domain.bo.StorageLocation;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableWebMvc
@Slf4j
public class SpringConfiguration implements WebMvcConfigurer {

    @Value("${picture.directory.location}")
    String pictureLocation;
    @Value("${picture.directory.name}")
    String pictureDirectoryName;
    @Value("${picture.uri}")
    String pictureBaseURL;

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
}
