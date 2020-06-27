package src.multilayerPerceptron;

import java.util.Comparator;
import java.util.List;
import java.util.stream.IntStream;

public class AutoEncoder {
    private final MultiLayerPerceptron perceptron;

    private int latentSpaceLayerIndex;

    public AutoEncoder(MultiLayerPerceptron perceptron){
        this.perceptron = perceptron;

        setLatentSpaceLayerIndex();
    }

    public List<Double> encode(List<Double> value){
        return perceptron.classify(value, 0, latentSpaceLayerIndex+1);
    }

    public List<Double> decode(List<Double> value){
        return perceptron.classify(value, latentSpaceLayerIndex+1, perceptron.getInnerLayersDimensions().size() + 1);
    }

    public void step(List<Double> pattern){
        this.perceptron.step(pattern, pattern);
    }

    private void setLatentSpaceLayerIndex(){
        List<Integer> layerDims = perceptron.getInnerLayersDimensions();

        this.latentSpaceLayerIndex = IntStream.range(0, layerDims.size()).boxed()
                .min(Comparator.comparingInt(layerDims::get))
                .orElseThrow(IllegalArgumentException::new);
    }

    public MultiLayerPerceptron getPerceptron() {
        return perceptron;
    }
}
