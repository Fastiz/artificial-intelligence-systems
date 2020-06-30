package src;

import src.multilayerPerceptron.AutoEncoder;
import src.multilayerPerceptron.MultiLayerPerceptron;
import src.multilayerPerceptron.Utils;

import java.awt.*;
import java.util.*;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Main {

    public static void main(String[] args){
        encodeDecodeForFont(2, 0.1, 20);
    }

    private static void encodeDecodeForFont(int fontNum, double noiseFactor, int limit){

        List<LetterData> trainingData = FontManager.getFont(fontNum, noiseFactor, limit);
        AutoEncoder autoEncoder = getEncoder(trainingData.get(0).getInput().size(), Arrays.asList(10,2,10));

        stochasticTraining(autoEncoder, trainingData, 200000);

        double totalError = 0;
        for (LetterData c : trainingData){
            List<Double> encoded = autoEncoder.encode(c.getInput());
            List<Double> encodeDecode = autoEncoder.decode(encoded);

            List<Double> encodeDecodeRounded = encodeDecode.stream()
                    .mapToDouble(v->v>0?1.0:-1.0).boxed().collect(Collectors.toList());

            System.out.println("\n\n-------------");

            printLetter(c.getInput());

            System.out.println("\n");

            printLetter(encodeDecodeRounded);

            System.out.println("\n");

            System.out.println(encoded);

            double diff = IntStream.range(0, encodeDecode.size()).mapToDouble(i->Math.abs(encodeDecodeRounded.get(i) - c.getInput().get(i))).sum();
            totalError+=diff/2;
        }

        System.out.println(String.format("Average difference is %s out of %s (%s)",
                totalError/trainingData.size(),
                trainingData.get(0).getInput().size(),
                totalError/trainingData.size()/trainingData.get(0).getInput().size()));

    }

    private static void stochasticTraining(AutoEncoder ae, List<LetterData> trainingData, int itNum){
        Random rnd = new Random();
        for(int it=0; it<itNum; it++){
            int randIndex = rnd.nextInt(trainingData.size());

            LetterData letterData = trainingData.get(randIndex);
            ae.step(letterData.getInput(), letterData.getOutput());
        }
    }

    private static void printLetter(List<Double> letter){
        int rowSize = 4;

        int rowIndex=0;
        for(Double c : letter){
            System.out.print(c.equals(-1.0) ? " " : "x");

            if(rowIndex == rowSize){
                System.out.print("\n");
                rowIndex = 0;
            }else{
                rowIndex++;
            }
        }
    }

    private static AutoEncoder getEncoder(int dim, List<Integer> innerDims){
        double alpha = 0.001;
        double li = 1;
        int numberOfIterationsToReachAlpha = 10000;
        double a = 10, b = - Math.log(alpha * (1/a)) / numberOfIterationsToReachAlpha;

        return new AutoEncoder(
                (new MultiLayerPerceptron.Builder())
                        .setActivationFunction((x) -> Math.tanh(li * x))
                        .setActivationFunctionDerivative(p->li*(1-Math.pow(Math.tanh(p), 2)))
//                        .setTemperatureFunction(s->a*Math.exp(-b*s))
                        .setAlpha(alpha)
                        .setInnerLayersDimensions(innerDims)
                        .setInDim(dim)
                        .setOutDim(dim)
                        .create()
        );
    }
}