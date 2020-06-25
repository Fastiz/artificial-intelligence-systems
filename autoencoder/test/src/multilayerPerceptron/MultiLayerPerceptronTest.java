package src.multilayerPerceptron;

import java.util.*;

public class MultiLayerPerceptronTest {

    public static void evenBetween0And10(){
        MultiLayerPerceptron multiLayerPerceptron = (new MultiLayerPerceptron.Builder())
                .setActivationFunction(Math::tanh)
                .setActivationFunctionDerivative(p->1-Math.pow(Math.tanh(p), 2))
                .setAlpha(0.1)
                .setInnerLayersDimensions(Arrays.asList(3, 3))
                .setInDim(1)
                .setOutDim(1)
                .create();

        List<List<List<Double>>> values = new ArrayList<>(10);
        for(int i=0; i<10; i++){
            values.add(
                    Arrays.asList(
                            Collections.singletonList((double) i),
                            Collections.singletonList(i % 2 == 0 ? 1.0 : -1.0)
                    )
            );
        }

        Random rnd = new Random();
        int itNum = 10000;
        for(int i=0; i<itNum; i++){
            int randIndex = rnd.nextInt(values.size()-1);

            multiLayerPerceptron.step(values.get(randIndex).get(0), values.get(randIndex).get(1));
        }

        for(List<List<Double>> val : values){
            Double correctValue = val.get(1).get(0),
                    obtainedValue = multiLayerPerceptron.classify(val.get(0)).get(0) > 0 ? 1.0 : -1.0;

            System.out.println(String.format("%s : %s", correctValue, obtainedValue));
            assert correctValue.equals(obtainedValue);
        }
    }

    public static void and(){
        MultiLayerPerceptron multiLayerPerceptron = (new MultiLayerPerceptron.Builder())
                .setActivationFunction(p->Math.tanh(3*p))
                .setActivationFunctionDerivative(p->3*(1-Math.pow(Math.tanh(p), 2)))
                .setAlpha(0.00001)
                .setInnerLayersDimensions(Arrays.asList(2, 2))
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
            int randIndex = rnd.nextInt(values.size()-1);

            multiLayerPerceptron.step(values.get(randIndex).get(0), values.get(randIndex).get(1));
        }

        for(List<List<Double>> val : values){
            Double correctValue = val.get(1).get(0),
                    obtainedValue = multiLayerPerceptron.classify(val.get(0)).get(0) > 0 ? 1.0 : -1.0;

            System.out.println(String.format("%s : %s", correctValue, obtainedValue));
            assert correctValue.equals(obtainedValue);
        }

    }
}
