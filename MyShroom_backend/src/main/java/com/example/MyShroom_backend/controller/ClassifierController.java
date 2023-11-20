package com.example.MyShroom_backend.controller;
import com.example.MyShroom_backend.dto.ClassifierRequestDto;
import com.example.MyShroom_backend.dto.ClassifierResponseDto;
import com.example.MyShroom_backend.enums.MushroomType;
import com.example.MyShroom_backend.service.ClassifierService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.io.*;


@CrossOrigin(origins = "http://localhost:4200", maxAge = 3600, allowCredentials="true")
@RestController
@RequestMapping("/api/classifier")
@RequiredArgsConstructor
public class ClassifierController {
    private static final MushroomType[] CLASSES = MushroomType.values();

    private final ClassifierService classifierService;
    @PostMapping("/predict")
    public ResponseEntity<?> predict(@RequestBody ClassifierRequestDto dto) throws IOException {

        return ResponseEntity.ok(new ClassifierResponseDto(this.classifierService.predict(dto)));
        }
    }


