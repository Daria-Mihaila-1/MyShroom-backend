package com.example.MyShroom_backend.controller;

import com.example.MyShroom_backend.dto.AddMushroomImgDto;
import com.example.MyShroom_backend.enums.MushroomType;
import com.example.MyShroom_backend.service.MushroomImgService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "http://localhost:4200", maxAge = 3600, allowCredentials="true")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/mushroom-img")
public class MushroomImgController {

    private final MushroomImgService mushroomImgService;
    @GetMapping("/get-all")
    public ResponseEntity<?> getAll() {
        return ResponseEntity.ok(this.mushroomImgService.findAll());
    }

    @PostMapping("add-img")
    public ResponseEntity<?> addImg(@RequestBody AddMushroomImgDto dto) {

        return ResponseEntity.ok(this.mushroomImgService.addImg(dto));
    }

    @GetMapping("/get-all-by-mushroomType/{mushroomType}")
    public ResponseEntity<?> getAllByMushroomType(@PathVariable String mushroomType) {
        String mushroomTypeUpperCase = mushroomType.toUpperCase();
        MushroomType foundMushroomType = null;
        switch (mushroomTypeUpperCase) {
            case "AGARICUS" :
                foundMushroomType = MushroomType.AGARICUS;
                break;
            case "AMANITA" :
                foundMushroomType = MushroomType.AMANITA;
                break;
            case "BOLETUS" :
                foundMushroomType = MushroomType.BOLETUS;
                break;
            case "CORTINARIUS" :
                foundMushroomType = MushroomType.CORTINARIUS;
                break;
            case "ENTOLOMA" :
                foundMushroomType = MushroomType.ENTOLOMA;
                break;
            case "HYGROCYBE" :
                foundMushroomType = MushroomType.HYGROCYBE;
                break;
            case "LACTARIUS" :
                foundMushroomType = MushroomType.LACTARIUS;
                break;
            case "RUSSULA" :
                foundMushroomType = MushroomType.RUSSULA;
                break;
            case "SUILLUS" :
                foundMushroomType = MushroomType.SUILLUS;
                break;
        }
        return ResponseEntity.ok(this.mushroomImgService.findAllByMushroomType(foundMushroomType));
    }
}
