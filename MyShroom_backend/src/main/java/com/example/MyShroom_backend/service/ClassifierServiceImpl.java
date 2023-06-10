package com.example.MyShroom_backend.service;

import ai.djl.MalformedModelException;
import ai.djl.inference.Predictor;
import ai.djl.modality.Classifications;
import ai.djl.modality.cv.Image;
import ai.djl.modality.cv.ImageFactory;
import ai.djl.repository.zoo.Criteria;
import ai.djl.repository.zoo.ModelNotFoundException;
import ai.djl.repository.zoo.ZooModel;
import ai.djl.translate.TranslateException;
import com.example.MyShroom_backend.Classifier.MyTranslator;
import com.example.MyShroom_backend.dto.ClassifierRequestDto;
import com.example.MyShroom_backend.dto.ClassifierResponseDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class ClassifierServiceImpl implements ClassifierService {
    @Override
    public Map<String, Double> predict(ClassifierRequestDto dto) {

        byte[] imgBytes = Base64.getDecoder().decode(dto.getBase64Img());
        ;
        ByteArrayInputStream bis = new ByteArrayInputStream(imgBytes);

        MyTranslator translator = new MyTranslator();
        // Define the criteria for loading the model
        Criteria<Image, Classifications> criteria = Criteria.builder()
                .setTypes(Image.class, Classifications.class)
                .optModelPath(Paths.get("MyShroom_backend/src/main/java/com/example/MyShroom_backend/Classifier/models/traced_model_resnet34_a4_8237.pt"))
//                .optModelName("traced_model_resnet34_with_weighted_sampler_a4_6728.pt")
                .optEngine("PyTorch")
                .optTranslator(translator)
                .build();

        // Load the model
        try (ZooModel<Image, Classifications> model = criteria.loadModel()) {

            Predictor<Image, Classifications> predictor = model.newPredictor(translator);

            Image input = ImageFactory.getInstance().fromInputStream(bis);
            bis.close();

            // Perform inference on an input image
            Classifications result = predictor.predict(input);


            Map<String, Double> resultMap;
            resultMap = result.topK()
                    .stream()
                    .collect(Collectors.toMap(Classifications.Classification::getClassName, Classifications.Classification::getProbability));

            List<Map.Entry<String, Double>> list = new ArrayList<>(resultMap.entrySet());
            list.sort(Map.Entry.comparingByValue());

            Map<String, Double> newMap = new LinkedHashMap<>();
            for (int index = 1; index < 4; index++) {
                newMap.put(list.get(list.size() - index).getKey(), list.get(list.size() - index).getValue());
            }

            System.out.println(newMap);
            // Return the prediction as a JSON string
            return newMap;
        } catch (
                TranslateException e) {
            e.printStackTrace();
        } catch (ModelNotFoundException | MalformedModelException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return null;
    }
}
