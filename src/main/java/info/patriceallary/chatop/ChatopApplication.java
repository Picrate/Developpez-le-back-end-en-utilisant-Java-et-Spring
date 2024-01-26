package info.patriceallary.chatop;

import info.patriceallary.chatop.configuration.PictureStorageConfiguration;
import info.patriceallary.chatop.services.FileSystemStorageService;
import info.patriceallary.chatop.services.StorageService;
import jakarta.annotation.Resource;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class ChatopApplication implements CommandLineRunner{

	@Resource
	FileSystemStorageService storageService;

	public static void main(String[] args) {SpringApplication.run(ChatopApplication.class, args);}
	@Override
	public void run(String... args) throws Exception {
		storageService.init();
	}
}
