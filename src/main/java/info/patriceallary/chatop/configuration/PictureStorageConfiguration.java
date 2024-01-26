package info.patriceallary.chatop.configuration;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Setter
@Getter
@Configuration
@ConfigurationProperties("picture-storage")
public class PictureStorageConfiguration {

    /**
     * Folder location for storing pictures files
     */
    private String location = "upload-dir";

}
