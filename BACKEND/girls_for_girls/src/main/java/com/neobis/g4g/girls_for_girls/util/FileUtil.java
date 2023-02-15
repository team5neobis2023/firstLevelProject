package com.neobis.g4g.girls_for_girls.util;

import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

public class FileUtil {
    private static Path findFile(Path dirPath, String code) {
        try {
            return Files
                    .list(dirPath)
                    .filter(file -> file.getFileName().toString().startsWith(code))
                    .findAny()
                    .orElse(null);
        } catch (IOException ioe) {
            return null;
        }
    }

    public static String save(String name, MultipartFile file) throws IOException {
        Path path = Paths.get("uploads");

        if (!Files.exists(path)) {
            Files.createDirectories(path);
        }

        String code = RandomStringUtils.randomAlphanumeric(8);

        try (InputStream inputStream = file.getInputStream()) {
            Path filePath = path.resolve(code + "-" + name);
            Files.copy(inputStream, filePath, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException ioe) {
            throw new IOException("Could not save file: " + name, ioe);
        }
        return code;
    }

    public static Resource getFileAsResource(String code) throws IOException {
        Path dirPath = Paths.get("uploads");
        Path foundFile = findFile(dirPath, code);

        if (foundFile != null) {
            return new UrlResource(foundFile.toUri());
        }
        return null;
    }

    public static boolean delete(String code) throws IOException {
        Path dirPath = Paths.get("uploads");
        Path foundFile = findFile(dirPath, code);

        if (foundFile != null) {
            Files.delete(foundFile);
            return true;
        }
        return false;
    }
}
