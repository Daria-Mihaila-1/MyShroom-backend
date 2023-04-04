package com.example.MyShroom_backend.Classifier;

import ai.djl.Model;
import ai.djl.modality.Classifications;
import ai.djl.modality.cv.Image;
import ai.djl.modality.cv.util.NDImageUtils;
import ai.djl.ndarray.NDArray;
import ai.djl.ndarray.NDList;
import ai.djl.translate.*;
import java.util.ArrayList;
import java.util.List;


public class MyTranslator implements Translator<Image, Classifications> {


    private static final List<String> CLASSES;
    static {
        // Define the class labels for the model
        CLASSES = new ArrayList<>();
        CLASSES.add("Agaricus");
        CLASSES.add("Amanita");
        CLASSES.add("Boletus");
        CLASSES.add("Cortinarius");
        CLASSES.add("Entoloma");
        CLASSES.add("Hygrocybe");
        CLASSES.add("Lactarius");
        CLASSES.add("Russula");
        CLASSES.add("Suillus");
    }


    @Override
    public NDList processInput(TranslatorContext ctx, Image input) {
        // Convert Image to NDArray
        NDArray array = input.toNDArray(ctx.getNDManager());

        //define mean and std for the tensor normalization
        float[] mean = new float[]{0.485f, 0.456f, 0.406f};
        float[] std = new float[]{0.229f, 0.224f, 0.225f};

        // Apply transformations on the NDArray of the Image object
        //Resize(224, 224) --> toTensor() --> Normalize(mean, std)
        NDList processedInput =new NDList(NDImageUtils.normalize(NDImageUtils.toTensor(NDImageUtils.resize(array, 224, 224)),mean,std));
        return processedInput;
    }

    @Override
    public Classifications processOutput(TranslatorContext ctx, NDList list) {

        // Create a Classifications with the output probabilities
        NDArray probabilities = list.singletonOrThrow().softmax(0);

        return new Classifications(CLASSES, probabilities);
    }

    @Override
    public Batchifier getBatchifier() {
        // The Batchifier describes how to combine a batch together
        // Stacking, the most common batchifier, takes N [X1, X2, ...] arrays to a single [N, X1, X2, ...] array
        return Batchifier.STACK;
    }

}