package com.Urban_Steps.start.controller;
import com.Urban_Steps.start.dto.HomeDTO;
import com.Urban_Steps.start.dto.ProfileDTO;
import com.Urban_Steps.start.service.HomePageService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/home")
@CrossOrigin("http://localhost:5173")
public class HomePageController {

    private final HomePageService homePageService;

    public HomePageController(HomePageService homePageService) {
        this.homePageService = homePageService;
    }

    @GetMapping
    public ResponseEntity<HomeDTO> getHomePageData() {
        HomeDTO homePageData = homePageService.getHomePageData();
        return ResponseEntity.ok(homePageData);
    }

    @GetMapping("/profile/{buyerId}")
    public ResponseEntity<ProfileDTO> getProfileData(@PathVariable Long buyerId) {
        ProfileDTO profileData = homePageService.getProfileData(buyerId);
        return ResponseEntity.ok(profileData);
    }
}
