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

    public static void checkIfAllWeightsAreUpdated(){
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

        List<List<List<Double>>> weights = multiLayerPerceptron.getWeights();

        Random rnd = new Random();
        int itNum = 1000;
        for(int i=0; i<itNum; i++){
            int randIndex = rnd.nextInt(values.size());

            List<List<List<Double>>> oldWeights = copyWeights(weights);

            multiLayerPerceptron.step(values.get(randIndex).get(0), values.get(randIndex).get(1));

            assertDifferentWeights(oldWeights, weights);
        }
    }

    private static void assertDifferentWeights(List<List<List<Double>>> a, List<List<List<Double>>> b){
        for(int i=0; i<a.size(); i++){
            for(int j=0; j<a.get(i).size(); j++){
                for(int k=0; k<a.get(i).get(j).size(); k++){
                    assert !a.get(i).get(j).get(k).equals(b.get(i).get(j).get(k));
                }
            }
        }
    }

    private static List<List<List<Double>>> copyWeights(List<List<List<Double>>> toCopy){
        List<List<List<Double>>> copy = new ArrayList<>();
        for(List<List<Double>> a : toCopy){
            List<List<Double>> aCopy = new ArrayList<>();
            for(List<Double> b : a){
                List<Double> bCopy = new ArrayList<>(b);
                aCopy.add(bCopy);
            }
            copy.add(aCopy);
        }

        return copy;
    }
}
