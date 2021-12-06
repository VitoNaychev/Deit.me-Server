package me.deit.server.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.security.Principal;
import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/user/pictures")
public class PictureController {
    @Autowired
    private PictureService pictureService;

    @GetMapping(value = "/all", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getPicturesByUserId(@RequestParam String userId) {

        List<Picture> pictures =  pictureService.findPicturesByUserId(Integer.parseInt(userId));

        return ResponseEntity.ok(pictures);
    }

    @PostMapping(value="/", consumes = "multipart/form-data")
    public ResponseEntity<?> savePicture(Principal principal, @RequestParam("pictureFile") MultipartFile pictureFile) throws IOException {
        String email = principal.getName();
        ResponseEntity<?> response;
        try {
            response = pictureService.savePictureByUserEmail(pictureFile, email);
        } catch (IOException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }

        return response;
    }

    @DeleteMapping(value="/delete")
    public ResponseEntity<?> deletePicture(@RequestParam String pictureId) {
        pictureService.deletePictureById(Long.parseLong(pictureId));

        return ResponseEntity.ok(HttpStatus.OK);
    }
}