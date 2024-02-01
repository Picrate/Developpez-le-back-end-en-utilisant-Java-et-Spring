package info.patriceallary.chatop.services.storage;

import info.patriceallary.chatop.configuration.SpringConfiguration;
import info.patriceallary.chatop.exception.StorageException;
import info.patriceallary.chatop.exception.StorageFileNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URI;
import java.nio.file.*;

@Service
@Slf4j
public class FileSystemStorageService implements StorageService {

    private final Path rootLocation;

        public FileSystemStorageService(SpringConfiguration configuration) {



        if (configuration.getPictureStorageLocation().getPath().toString().isEmpty()) {
            throw new StorageException("File upload location can not be Empty.");
        }
        this.rootLocation = configuration.getPictureStorageLocation().getPath();
        log.info("FileStorageLocation : "+this.rootLocation.toString());
    }

    @Override
    public void init() {
        try {
            log.info("Setting Picture Upload Location to : "+rootLocation);
            if(!Files.exists(rootLocation)) {
                Files.createDirectories(rootLocation);
            }
        }
        catch (IOException e) {
            throw new StorageException("Could not initialize storage", e);
        }
    }

    @Override
    public void store(MultipartFile file, String destinationFilename) {

        try {

            if (file.isEmpty()) {
                throw new StorageException("Failed to store empty file.");
            }

                Path destinationFile = this.rootLocation.resolve(
                                Paths.get(destinationFilename))
                        .normalize().toAbsolutePath();

                // Check if attempting to store outside rootDirectory
                if (!destinationFile.getParent().equals(this.rootLocation.toAbsolutePath())) {
                    throw new StorageException("Cannot store file outside current directory");
                }

                try (InputStream inputStream = file.getInputStream()) {
                    Files.copy(inputStream, destinationFile, StandardCopyOption.REPLACE_EXISTING);
                }


        } catch (IOException e) {
            throw new StorageException("Failed to store file.", e);
        }
    }

    @Override
    public Path load(String filename) {
        return rootLocation.resolve(filename);
    }

    public URI getURI(String filename) {
            return rootLocation.resolve(filename).toUri();
        }

    @Override
    public Resource loadAsResource(String filename) {
        Path file = load(filename);
        try {
            Resource resource = new UrlResource(file.toUri());
            if (resource.exists() || resource.isReadable()) {
                return resource;
            } else {
                throw new StorageFileNotFoundException("Could not read file: " + filename);
            }
        } catch (MalformedURLException e) {
            throw new StorageFileNotFoundException("Could not read file: " + filename, e);
        }
    }

    @Override
    public void delete(String filename) {
        Path file = load(filename);
        try {
            if (UrlResource.from(file.toUri()).exists()) {
                Files.delete(file);
            }
        } catch (IOException e) {
            throw new StorageFileNotFoundException("Could not delete file :" + filename, e);
        }
    }
}
