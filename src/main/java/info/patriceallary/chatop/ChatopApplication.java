package info.patriceallary.chatop;

import info.patriceallary.chatop.services.storage.FileSystemStorageService;
import jakarta.annotation.Resource;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

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
