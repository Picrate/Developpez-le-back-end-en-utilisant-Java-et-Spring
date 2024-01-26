package info.patriceallary.chatop.domain.bo;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StorageLocation {

    private String location;
    public StorageLocation(String location) {
        this.location = location;
    }
}
