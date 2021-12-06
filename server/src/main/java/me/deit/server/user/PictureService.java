package me.deit.server.user;

import me.deit.server.exceptions.UserExceptions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Component("PictureService")
public class PictureService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PictureRepository pictureRepository;

    public List<Picture> findPicturesByUserId(Integer id) {
        return pictureRepository.findPicturesByUserId(id);
    }

    public PictureService() {
    }

    public ResponseEntity<?> savePictureByUserEmail(MultipartFile pictureFile, String email) throws IOException {
        Long userId = userRepository.getUserIdByEmail(email);
        String type = pictureFile.getContentType();
        byte[] pictureBytes = pictureFile.getBytes();

        Picture picture = new Picture(pictureBytes, type, Integer.parseInt(userId.toString()));

        if (pictureBytes.length <= 0) {
            return new ResponseEntity<>(UserExceptions.USER_PICTURES_NOT_CHOSEN, HttpStatus.NOT_FOUND);
        }

        pictureRepository.save(picture);

        return ResponseEntity.ok(HttpStatus.OK);
    }

    public void deletePictureById(Long id) {
        pictureRepository.deletePictureById(id);
    }
}
