/**
 * Picture Validation & Management Service
 * Used to follow OWASP security recommendations about File Uploads
 * https://cheatsheetseries.owasp.org/cheatsheets/File_Upload_Cheat_Sheet.html
 */

package info.patriceallary.chatop.services.tools;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileNotFoundException;
import java.util.UUID;

@Service
@Slf4j
public class PictureManager {

    @Value("${picture.uri}")
    private String pictureEndpoint;

    private final URLManager urlManager;

    public PictureManager(URLManager urlManager) {
        this.urlManager = urlManager;
    }

    /**
     * Cleanup & encode filename
     * Sanitize filename By renaming it with a unique random name & encode in Base64
     * @param file
     * @return the file renamed with a Encoded & Unique name
     * @throws FileNotFoundException
     */
    public String sanitizeAndEncodeFilename(MultipartFile file) throws FileNotFoundException {
        // Encode filename to Base64
        if (file != null && !file.isEmpty()) {
            return createUniqueFilename(this.getExtension(file));
        } else {
            throw new FileNotFoundException();
        }
    }

    /**
     * Check if :
     * - file is not null nor empty
     * - ContentType is not null nor empty
     * - ContentType correspond to JPEG, JPG or PNG     *
     * @param file
     * @return true if valid or false either
     */
    public Boolean isValidPicture(MultipartFile file) {
        Boolean isValid = false;
        if (file != null && !file.isEmpty()) {
            if (file.getContentType() != null && !file.getContentType().isBlank()) {
               isValid = file.getContentType().matches("^image/((jpeg)|(jpg)|(png))$");
            }
        }
        return isValid;
    }

    public String getExtension(MultipartFile file) {

        String extension = "N/A";
        if(file != null) {
            String contentType = file.getContentType();
            if (contentType != null && !contentType.isBlank()) {
                extension = switch (contentType) {
                    case "image/jpeg" -> "jpeg";
                    case "image/jpg" -> "jpg";
                    case "image/png" -> "png";
                    default -> extension;
                };
            }
        }
        return extension;
    }

    /**
     * Create Unique filename for file
     *
     * @param extension
     * @return
     */
    private String createUniqueFilename(String extension) {
        return UUID.randomUUID().toString() + "." + extension;
    }

    public String getPictureUrl(String filename, String requestURL) {
        return this.urlManager.getPicturesBaseUrlFromRequestUrl(requestURL).append(filename).toString();
    }

}
