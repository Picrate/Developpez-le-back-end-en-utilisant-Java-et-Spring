/**
 * Picture Validation & Management Service
 * Used to follow OWASP security recommendations about File Uploads
 * https://cheatsheetseries.owasp.org/cheatsheets/File_Upload_Cheat_Sheet.html
 */

package info.patriceallary.chatop.services;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileNotFoundException;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.UUID;

@Service
@Slf4j
public class PictureManager {

    /**
     * Cleanup & encode filename
     * Sanitize filename By renaming it with a unique random name & encode in Base64
     * @param file
     * @return BASE64 Encoded Unique Filename
     * @throws FileNotFoundException
     */
    public String sanitizeAndEncodeFilename(MultipartFile file) throws FileNotFoundException {
        // Encode filename to Base64
        if (file != null && !file.isEmpty()) {
            String newFileName = createUniqueFilename(this.getExtension(file));
            log.info("Unique filename : "+newFileName);
            log.info("Encoded fileName : "+this.encodeFileName(newFileName));
            return this.encodeFileName(newFileName);
        } else {
            throw new FileNotFoundException();
        }
    }
    private String encodeFileName(String filename) {
        return Base64.getEncoder().encodeToString(filename.getBytes(StandardCharsets.UTF_8));
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

}
