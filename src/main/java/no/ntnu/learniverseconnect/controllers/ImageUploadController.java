package no.ntnu.learniverseconnect.controllers;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 * Controller for handling image uploads.
 */
@RestController
@RequestMapping("/images")
@CrossOrigin(origins = "http://localhost:3000")
public class ImageUploadController {

  private final String uploadDir = "/usr/share/nginx/html/uploads/images/";


  /**
   * Uploads an image to the server and returns the URL of the uploaded image.
   *
   * @param file image file to upload
   * @return URL of the uploaded image
   */
  @PostMapping("/upload")
  public ResponseEntity<String> uploadImage(@RequestParam("file") MultipartFile file) {
    // Ensure the uploads directory exists
    File uploadDirectory = new File(uploadDir);
    if (!uploadDirectory.exists()) {
      uploadDirectory.mkdirs();
    }

    try {
      // Get the original file name
      String fileName = file.getOriginalFilename();
      Path filePath = Paths.get(uploadDir, fileName);

      // Save the file locally
      Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

      // Return the URL of the uploaded image (you can return the relative path)
      String imageUrl = "http://localhost:8081/uploads/images/" + fileName;
      return ResponseEntity.status(HttpStatus.OK).body(imageUrl);
    } catch (IOException e) {
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to upload image");
    }
  }
}

