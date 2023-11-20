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

    @PostMapping("/add-img")
    public ResponseEntity<?> addImg(@RequestBody AddMushroomImgDto dto) {

        return ResponseEntity.ok(this.mushroomImgService.addImg(dto));
    }

    @GetMapping("/get-all-by-mushroomType/{mushroomType}")
    public ResponseEntity<?> getAllByMushroomType(@PathVariable String mushroomType) {
        String mushroomTypeUpperCase = mushroomType.toUpperCase();
        MushroomType foundMushroomType = switch (mushroomTypeUpperCase) {
            case "AGARICUS" -> MushroomType.AGARICUS;
            case "AMANITA" -> MushroomType.AMANITA;
            case "BOLETUS" -> MushroomType.BOLETUS;
            case "CORTINARIUS" -> MushroomType.CORTINARIUS;
            case "ENTOLOMA" -> MushroomType.ENTOLOMA;
            case "HYGROCYBE" -> MushroomType.HYGROCYBE;
            case "LACTARIUS" -> MushroomType.LACTARIUS;
            case "RUSSULA" -> MushroomType.RUSSULA;
            case "SUILLUS" -> MushroomType.SUILLUS;
            default -> null;
        };
        return ResponseEntity.ok(this.mushroomImgService.findAllByMushroomType(foundMushroomType));
    }
}
