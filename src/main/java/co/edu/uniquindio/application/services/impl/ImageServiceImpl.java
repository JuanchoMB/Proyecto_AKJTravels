package co.edu.uniquindio.application.services.impl;

import co.edu.uniquindio.application.services.ImageService;
import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Service
public class ImageServiceImpl implements ImageService {

    private final Cloudinary cloudinary;

    public ImageServiceImpl(){
        Map<String, String> config = new HashMap<>();
        config.put("cloud_name", "dje3qr8tq");
        config.put("api_key", "693381465632364");
        config.put("api_secret", "5cICFV1EvZ-E8FPCAyNSh5WGUt0");
        cloudinary = new Cloudinary(config);
    }

    @Override
    public Map upload(MultipartFile image) throws Exception {
        File file = convert(image);
        try {
            // Sugerencias: usar el nombre original, no sobreescribir y guardar en carpeta
            Map<String, Object> options = ObjectUtils.asMap(
                    "folder", "AKJTravels",
                    "resource_type", "image",
                    "use_filename", true,
                    "unique_filename", true,
                    "overwrite", false
            );
            return cloudinary.uploader().upload(file, options);
        } finally {
            // limpia el archivo temporal
            if (file != null && file.exists()) {
                //noinspection ResultOfMethodCallIgnored
                file.delete();
            }
        }
    }

    @Override
    public Map delete(String id) throws Exception {
        return cloudinary.uploader().destroy(id, ObjectUtils.emptyMap());
    }

    private File convert(MultipartFile image) throws IOException {
        File file = File.createTempFile(image.getOriginalFilename(), null);
        FileOutputStream fos = new FileOutputStream(file);
        fos.write(image.getBytes());
        fos.close();
        return file;
    }
}