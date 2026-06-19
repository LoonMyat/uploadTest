package com.LM.uploadTest.controller;

import java.io.IOException;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.LM.uploadTest.entity.Artwork;
import com.LM.uploadTest.repo.ArtworkRepo;

@Controller
public class ArtworkController {

    private final ArtworkRepo artworkRepo;

    public ArtworkController(ArtworkRepo artworkRepo){
        this.artworkRepo = artworkRepo;
    }

    @GetMapping("/")
    public String gallery(Model model){
        model.addAttribute(
            "artworks", artworkRepo.findAll()
        );

        return "gallery";
    }

    @GetMapping("/upload")
    public String uploadPage(){
        return "upload";
    }

    @PostMapping("/upload")
    public String upload(
        @RequestParam String title,
        @RequestParam MultipartFile image
    ) throws IOException{
        Artwork artwork = new Artwork();

        artwork.setTitle(title);
        artwork.setContentType(image.getContentType());
        artwork.setImageData(image.getBytes());

        artworkRepo.save(artwork);

        return "redirect:/";
    }

    @GetMapping("/image/{id}")
    public ResponseEntity<byte[]> image(@PathVariable Long id){
        Artwork artwork = artworkRepo.findById(id).orElseThrow();

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(artwork.getContentType()))
                .body(artwork.getImageData());
    }



}
