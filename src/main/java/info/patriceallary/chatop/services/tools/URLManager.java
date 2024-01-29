package info.patriceallary.chatop.services.tools;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class URLManager {

    @Value("${picture.uri}")
    String pictureUrlFragment;

    // http://localhost:3001/api/rentals
    private StringBuilder getBaseURL (String requestURL) {
        String[] splits=requestURL.trim().split("/");
        return new StringBuilder(splits[0]).append("//").append(splits[2]);
    }
    public StringBuilder getPicturesBaseUrlFromRequestUrl(String requestUrl) {
        return getBaseURL(requestUrl).append(pictureUrlFragment);
    }

}
