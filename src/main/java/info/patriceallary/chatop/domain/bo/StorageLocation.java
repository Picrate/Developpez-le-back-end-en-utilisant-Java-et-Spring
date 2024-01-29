package info.patriceallary.chatop.domain.bo;

import lombok.Getter;
import lombok.Setter;

import java.net.URI;
import java.nio.file.Path;

@Getter
@Setter
public class StorageLocation {

    private final String path;
    private final String directory;

    public StorageLocation(String path, String directory) {
        this.path = path;
        this.directory = directory;
    }

    public Path getPath() {
        return Path.of(this.path, this.directory);
    }

    public URI getUri() {
        return getPath().toUri();
    }
}
