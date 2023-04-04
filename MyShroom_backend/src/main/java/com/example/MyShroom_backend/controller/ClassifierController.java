package com.example.MyShroom_backend.controller;


import ai.djl.MalformedModelException;
import ai.djl.inference.Predictor;
import ai.djl.modality.Classifications;
import ai.djl.modality.cv.Image;
import ai.djl.modality.cv.ImageFactory;

import ai.djl.repository.zoo.*;
import ai.djl.translate.TranslateException;

import com.example.MyShroom_backend.Classifier.MyTranslator;
import com.example.MyShroom_backend.dto.ClassifierRequestDto;
import com.example.MyShroom_backend.dto.ClassifierResponseDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.io.*;
import java.nio.file.Paths;
import java.util.ArrayList;

import java.util.Base64;
import java.util.List;




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
            System.out.println(result);

            // Return the prediction as a JSON string
            return ResponseEntity.ok(new ClassifierResponseDto("{\"scores\": " + result.getAsString() + "}"));

        } catch (TranslateException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (ModelNotFoundException | MalformedModelException e) {
            throw new RuntimeException(e);

        }
    }

}
