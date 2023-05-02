package com.example.MyShroom_backend.controller;


import ai.djl.MalformedModelException;
import ai.djl.inference.Predictor;
import ai.djl.modality.Classifications;
import ai.djl.modality.cv.Image;
import ai.djl.modality.cv.ImageFactory;

import ai.djl.repository.zoo.*;
import ai.djl.translate.TranslateException;

import ai.djl.util.Pair;
import com.example.MyShroom_backend.Classifier.MyTranslator;
import com.example.MyShroom_backend.dto.ClassifierRequestDto;
import com.example.MyShroom_backend.dto.ClassifierResponseDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.io.*;
import java.nio.file.Paths;
import java.util.*;

import java.util.stream.Collectors;


@CrossOrigin(origins = "http://localhost:4200", maxAge = 3600, allowCredentials="true")
@RestController
@RequestMapping("/api/classifier")
public class ClassifierController {
    private static final List<String> CLASSES = new ArrayList<String>(){
        {
            add("Agaricus");
            add("Amanita");
            add("Boletus");
            add("Cortinarius");
            add("Entoloma");
            add("Hygrocybe");
            add("Lactarius");
            add("Russula");
            add("Suillus");
        }
    };
    @PostMapping("/predict")
    public ResponseEntity<?> predict(@RequestBody ClassifierRequestDto dto) throws IOException {

        System.out.println(dto.getBase64Img());
        byte[] imgBytes = Base64.getDecoder().decode(dto.getBase64Img());;
        ByteArrayInputStream bis = new ByteArrayInputStream(imgBytes);

        MyTranslator translator = new MyTranslator();
        // Define the criteria for loading the model
        Criteria<Image, Classifications> criteria = Criteria.builder()
                .setTypes(Image.class, Classifications.class)
                .optModelPath(Paths.get("C:\\Users\\Daria\\IdeaProjects\\MyShroom_backend\\MyShroom_backend\\src\\main\\java\\com\\example\\MyShroom_backend\\Classifier\\models"))
                .optModelName("traced_model_resnet34_with_weighted_sampler_a4_6728.pt")
                .optEngine("PyTorch")
                .optTranslator(translator)
                .build();
        System.out.println(criteria.getEngine());

        // Load the model
        try (ZooModel<Image, Classifications> model = criteria.loadModel()) {

            Predictor<Image, Classifications> predictor = model.newPredictor(translator);
            System.out.println("am construit predictoru");
            Image input = ImageFactory.getInstance().fromInputStream(bis);
            bis.close();

            System.out.println("image saved");
            // Perform inference on an input image
            Classifications result = predictor.predict(input);
            System.out.println("ceva" + result);


            Map<String, Double> resultMap;
            resultMap = result.topK()
                    .stream()
                    .collect(Collectors.toMap(Classifications.Classification::getClassName,Classifications.Classification::getProbability));


            List<Map.Entry<String, Double>> list = new ArrayList<>(resultMap.entrySet());
            list.sort(Map.Entry.comparingByValue());

            Map<String, Double> newMap = new LinkedHashMap<>();
            for (int index = 1; index < 4; index++) {
                newMap.put(list.get(list.size()-index).getKey(), list.get(list.size()-index).getValue());
            }

            System.out.println(newMap);
            // Return the prediction as a JSON string
            return ResponseEntity.ok(new ClassifierResponseDto(newMap));

        } catch (TranslateException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (ModelNotFoundException | MalformedModelException e) {
            throw new RuntimeException(e);

        }
    }

}
