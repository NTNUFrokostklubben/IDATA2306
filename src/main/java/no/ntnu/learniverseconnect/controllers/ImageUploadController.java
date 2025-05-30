package no.ntnu.learniverseconnect.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import no.ntnu.learniverseconnect.security.swagger.SecuredEndpoint;
import org.springframework.beans.factory.annotation.Value;
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

  @Value("${app.uploadBaseUrl}")
  private String uploadBaseUrl;

  /**
   * Uploads an image to the server and returns the URL of the uploaded image.
   *
   * @param file image file to upload
   * @return URL of the uploaded image
   */
  @Operation(
      summary = "Upload an image",
      description = "Uploads an image to the server and returns the URL of the uploaded image"
  )
  @ApiResponses({
      @ApiResponse(
          responseCode = "200",
          description = "Image uploaded successfully"
      ),
      @ApiResponse(
          responseCode = "500",
          description = "Failed to upload image"
      )
  })
  @SecuredEndpoint
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
      String imageUrl = uploadBaseUrl + fileName;
      return ResponseEntity.status(HttpStatus.OK).body(imageUrl);
    } catch (IOException e) {
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to upload image");
    }
  }
}

