package co.edu.uniquindio.application.services;

import org.springframework.web.multipart.MultipartFile;
import java.util.Map;

public interface ImageService {
    Map upload(MultipartFile file) throws Exception;
    Map delete(String id) throws Exception;
}