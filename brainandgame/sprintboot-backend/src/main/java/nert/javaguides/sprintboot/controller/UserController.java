package nert.javaguides.sprintboot.controller;
import nert.javaguides.sprintboot.dto.SocialMediaLinksDTO;
import nert.javaguides.sprintboot.repository.UserRepository;
import nert.javaguides.sprintboot.util.JwtUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/users")
@CrossOrigin(origins = {"http://localhost:3000", "http://localhost:60965"})
public class UserController {

    @Autowired
    private UserRepository userRepository;

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @PutMapping("/{id}/social-media-links")
    public ResponseEntity<?> updateSocialMediaLinks(
            @PathVariable Long id,
            @RequestBody SocialMediaLinksDTO socialMediaLinksDTO) {

        return userRepository.findById(id).map(user -> {
            user.setLinkedInLink(socialMediaLinksDTO.getLinkedInLink());
            user.setInstagramLink(socialMediaLinksDTO.getInstagramLink());
            userRepository.save(user);
            return ResponseEntity.ok(Map.of("message", "Social media links updated successfully"));
        }).orElse(ResponseEntity.status(404).body(Map.of("error", "User not found")));
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getUserProfile(@PathVariable Long id) {
        logger.info("Get user profile for user id {}", id);
        return userRepository.findById(id).map(user -> {
            Map<String, Object> profile = new HashMap<>();
            profile.put("username", user.getUsername());
            profile.put("linkedInLink", user.getLinkedInLink());
            profile.put("instagramLink", user.getInstagramLink());
            return ResponseEntity.ok(profile);
        }).orElse(ResponseEntity.status(404).body(Map.of("error", "User not found")));
    }
}

