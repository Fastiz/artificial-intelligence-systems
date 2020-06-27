package src.multilayerPerceptron;

import java.util.*;

public class MultiLayerPerceptronTest {

    public static void and(){
        MultiLayerPerceptron multiLayerPerceptron = (new MultiLayerPerceptron.Builder())
                .setActivationFunction(p->Math.tanh(3*p))
                .setActivationFunctionDerivative(p->3*(1-Math.pow(Math.tanh(p), 2)))
                .setAlpha(0.01)
                .setInnerLayersDimensions(Arrays.asList(8, 8))
                .setInDim(2)
                .setOutDim(1)
                .create();

        List<List<List<Double>>> values = new ArrayList<>(10);
        for(int i=-1; i<=1; i+=2){
            for(int j=-1; j<=1; j+=2){
                boolean iBool = i == 1, jBool = j == 1;
                values.add(Arrays.asList(
                        Arrays.asList((double) i, (double) j),
                        Collections.singletonList(iBool && jBool ? 1.0 : -1.0)
                ));
            }
        }

        Random rnd = new Random();
        int itNum = 100000;
        for(int i=0; i<itNum; i++){
            int randIndex = rnd.nextInt(values.size());

            multiLayerPerceptron.step(values.get(randIndex).get(0), values.get(randIndex).get(1));
        }

        for(List<List<Double>> val : values){
            Double correctValue = val.get(1).get(0),
                    obtainedValue = multiLayerPerceptron.classify(val.get(0)).get(0) > 0 ? 1.0 : -1.0;

            assert correctValue.equals(obtainedValue);
        }

    }
}
