package me.deit.server.user;

import me.deit.server.exceptions.UserExceptions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/user/pictures")
public class PictureController {
    @Autowired
    private PictureRepository pictureRepository;

    @GetMapping(value = "/all", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getPicturesByUserId(@RequestParam String userId) {

        List<Picture> pictures =  pictureRepository.findPicturesByUserId(Integer.parseInt(userId));

        return ResponseEntity.ok(pictures);
    }

    @PostMapping(value="/", consumes = "multipart/form-data")
    public ResponseEntity<?> savePicture(@RequestParam("pictureFile") MultipartFile pictureFile, String userId) throws IOException {
        String type = pictureFile.getContentType();
        byte[] pictureBytes = pictureFile.getBytes();

        Picture picture = new Picture(pictureBytes, type, Integer.parseInt(userId));

        if (pictureBytes.length <= 0) {
            return new ResponseEntity<>(UserExceptions.USER_PICTURES_NOT_CHOSEN, HttpStatus.NOT_FOUND);
        }

        pictureRepository.save(picture);

        return ResponseEntity.ok(HttpStatus.OK);
    }
}