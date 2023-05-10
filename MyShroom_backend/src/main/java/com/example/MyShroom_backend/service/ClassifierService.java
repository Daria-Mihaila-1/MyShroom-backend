package com.example.MyShroom_backend.service;

import com.example.MyShroom_backend.dto.ClassifierRequestDto;
import com.example.MyShroom_backend.dto.ClassifierResponseDto;

import java.util.Map;

public interface ClassifierService {
    Map<String, Double> predict(ClassifierRequestDto dto);

}
