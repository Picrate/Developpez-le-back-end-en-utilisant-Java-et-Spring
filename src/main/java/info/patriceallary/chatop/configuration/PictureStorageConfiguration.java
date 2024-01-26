package info.patriceallary.chatop.configuration;

import info.patriceallary.chatop.domain.bo.StorageLocation;
import lombok.Getter;
import lombok.Setter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Setter
@Getter
@Configuration
public class PictureStorageConfiguration {

    /**
     * Folder location for storing pictures files
     */    @Bean
    public StorageLocation getPictureStorageLocation() {
        return new StorageLocation("upload-dir");
    }


}
