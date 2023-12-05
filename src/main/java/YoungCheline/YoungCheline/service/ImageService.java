package YoungCheline.YoungCheline.service;

import YoungCheline.YoungCheline.dto.AddMenuUrlDto;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.URL;
import java.util.Map;

public interface ImageService {

    Map<String, URL> uploadProfile(MultipartFile file, String userName,String bucket) throws IOException;
    AddMenuUrlDto uploadMenu(MultipartFile file, String bucket, String restaurantId, String menu) throws IOException;
}
