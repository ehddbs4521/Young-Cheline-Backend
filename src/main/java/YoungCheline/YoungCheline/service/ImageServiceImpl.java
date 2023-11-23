package YoungCheline.YoungCheline.service;

import YoungCheline.YoungCheline.entity.MenuImage;
import YoungCheline.YoungCheline.entity.MenuKey;
import YoungCheline.YoungCheline.entity.Profile;
import YoungCheline.YoungCheline.entity.Menu;
import YoungCheline.YoungCheline.repository.ImageRepository;
import YoungCheline.YoungCheline.repository.MenuImageRepository;
import YoungCheline.YoungCheline.repository.MenuRepository;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.ObjectMetadata;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class ImageServiceImpl implements ImageService {

    private final MenuRepository menuRepository;
    private final ImageRepository imageRepository;
    private final AmazonS3Client amazonS3Client;
    private final MenuImageRepository menuImageRepository;

    public Map<String, URL> uploadProfile(MultipartFile file, String userName, String bucket) throws IOException {
        Map<String, URL> profile = new HashMap<>();
        Optional<Profile> image = imageRepository.findByUserName(userName);
        String fileName = userName + "_" + file.getOriginalFilename();
        ObjectMetadata metadata = new ObjectMetadata();
        metadata.setContentType(file.getContentType());
        metadata.setContentLength(file.getSize());
        amazonS3Client.putObject(bucket, fileName, file.getInputStream(), metadata);
        URL url = amazonS3Client.getUrl(bucket, fileName);
        profile.put("file", url);
        image.get().setUrl(profile.get("file").toString());
        imageRepository.save(image.get());
        return profile;
    }

    public Map<String, String> uploadMenu(MultipartFile file, String bucket, String restaurantId, String menuName) throws IOException {
        Map<String, String> menus = new HashMap<>();
        Optional<Menu> menu = menuRepository.findByRestaurantIdAndMenuName(restaurantId, menuName);


        if (menu.isEmpty()) {
            String fileName = restaurantId + "_" + menuName + "_" + file.getOriginalFilename();
            ObjectMetadata metadata = new ObjectMetadata();
            metadata.setContentType(file.getContentType());
            metadata.setContentLength(file.getSize());
            amazonS3Client.putObject(bucket, fileName, file.getInputStream(), metadata);
            URL url = amazonS3Client.getUrl(bucket, fileName);
            menus.put("menuName", menuName);
            menus.put("url", url.toString());
            Menu menuInfo = new Menu();
            MenuImage menuImage = new MenuImage();
            MenuKey menuKey = new MenuKey();
            menuInfo.setRestaurantId(restaurantId);
            menuInfo.setMenuName(menuName);
            menuInfo.setUrl(menus.get("file"));
            menuKey.setMenuId(menuInfo.getMenuId());
            menuKey.setTime(LocalDateTime.now().toString());
            menuImage.setMenuKey(menuKey);
            menuImage.setUrl(menus.get("file"));
            menuRepository.save(menuInfo);
            menus.put("menuId", menuInfo.getMenuId().toString());
            return menus;
        } else {
            menus.put("menu", null);
            return menus;
        }
    }

    public void uploadMenuImage(MultipartFile file,String bucket,Integer menuId,String time) throws IOException {
        MenuImage menuImage = new MenuImage();
        MenuKey menuKey = new MenuKey();
        menuKey.setMenuId(menuId);
        menuKey.setTime(time);
        menuImage.setMenuKey(menuKey);
        String fileName = menuId + "_" + file.getOriginalFilename();
        ObjectMetadata metadata = new ObjectMetadata();
        metadata.setContentType(file.getContentType());
        metadata.setContentLength(file.getSize());
        amazonS3Client.putObject(bucket, fileName, file.getInputStream(), metadata);
        URL url = amazonS3Client.getUrl(bucket, fileName);
        menuImage.setUrl(url.toString());
        menuImageRepository.save(menuImage);
    }


}
