package info.patriceallary.chatop.domain.bo;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.java.Log;
import lombok.extern.slf4j.Slf4j;

import java.net.URI;
import java.nio.file.Path;

@Getter
@Setter
@Slf4j
public class StorageLocation {

    private final String path;
    private final String directory;

    public StorageLocation(String path, String directory) {
        this.path = path;
        this.directory = directory;
        log.info("Storage Location : "+ getPath().toString());
    }

    public Path getPath() {
        return Path.of(this.path, this.directory);
    }

    public URI getUri() {
        return getPath().toUri();
    }
}
