/**
 * Picture Validation & Management Service
 * Used to follow OWASP security recommendations about File Uploads
 * https://cheatsheetseries.owasp.org/cheatsheets/File_Upload_Cheat_Sheet.html
 */

package info.patriceallary.chatop.services.tools;

import info.patriceallary.chatop.exception.StorageException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileNotFoundException;
import java.util.UUID;

@Service
@Slf4j
public class PictureManager {
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
        // Encode filename
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
    public boolean isValidPicture(MultipartFile file) {
        boolean isValid = false;
        if (file == null || file.getContentType() == null) {
            throw new StorageException("File or ContentType is Null !");
        }else if (!file.isEmpty() && !file.getContentType().isBlank()) {
               isValid = file.getContentType().matches("^image/((jpeg)|(jpg)|(png))$");
        }
        return isValid;
    }

    /**
     * Get MIME Type file extension
     * @param file the file to inspect
     * @return the correct MIME Type of the file
     */
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
