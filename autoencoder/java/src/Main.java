package src;

import src.multilayerPerceptron.AutoEncoder;
import src.multilayerPerceptron.MultiLayerPerceptron;
import src.multilayerPerceptron.Utils;

import java.awt.*;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Main {

    public static void main(String[] args){
//        (new HSV(0.9f, 0.6f, 0.5f, 0.5f)).run();

        (new HSV("starry_night.jpg")).run();

//        encodeDecodeForFont(2, 0);
    }

    private static void encodeDecodeForFont(int fontNum, double noiseFactor){

        FontManager fm = new FontManager();
        List<List<Double>> font = fm.getFont(fontNum, noiseFactor);
        AutoEncoder autoEncoder = getEncoder(font.get(0).size(), Arrays.asList(60,15,2,15,60));


        stochasticTraining(autoEncoder, font, fm.getOutput(), 200000);


        try(BufferedWriter bf = new BufferedWriter(new FileWriter("data"))){
            double totalError = 0;
            String[] fontNames = fm.getFontNames(fontNum);


            for (int i=0; i<font.size(); i++){
                List<Double> c = font.get(i);

                List<Double> encoded = autoEncoder.encode(c);
                List<Double> encodeDecode = autoEncoder.decode(encoded);

                List<Double> encodeDecodeRounded = encodeDecode.stream()
                        .mapToDouble(v->v>0?1.0:-1.0).boxed().collect(Collectors.toList());

                System.out.println("\n\n-------------");

                printLetter(c);

                System.out.println("\n");

                printLetter(encodeDecodeRounded);

                System.out.println("\n");

                System.out.println(encoded);

                double diff = IntStream.range(0, encodeDecode.size()).mapToDouble(s->Math.abs(encodeDecodeRounded.get(s) - c.get(s))).sum();
                totalError+=diff/2;

                if(Double.valueOf(noiseFactor).equals(0.0) && i < fontNames.length){
                    bf.write(encoded.get(0) + " " + encoded.get(1) + " " + fontNames[i] + "\n");
                }else{
                    bf.write(encoded.get(0) + " " + encoded.get(1) + "\n");
                }
            }

            System.out.println(String.format("Average difference is %s out of %s (%s)",
                    totalError/font.size(),
                    font.get(0).size(),
                    totalError/font.size()/font.get(0).size()));
        }catch (IOException e){
            System.err.print(e);
            return;
        }




    }

    private static void stochasticTraining(AutoEncoder ae, List<List<Double>> trainingData, List<List<Double>> output, int itNum){
        Random rnd = new Random();
        for(int it=0; it<itNum; it++){
            int randIndex = rnd.nextInt(trainingData.size());

            ae.step(trainingData.get(randIndex), output.get(randIndex));
        }
    }

    private static void printLetter(List<Double> letter){
        int rowSize = 5;

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
        int numberOfIterationsToReachAlpha = 10000;
        double a = 10, b = - Math.log(alpha * (1/a)) / numberOfIterationsToReachAlpha;

        return new AutoEncoder(
                (new MultiLayerPerceptron.Builder())
                        .setActivationFunction((x) -> Math.tanh(x))
                        .setActivationFunctionDerivative(p->(1-Math.pow(Math.tanh(p), 2)))
//                        .setTemperatureFunction(s->a*Math.exp(-b*s))
                        .setAlpha(alpha)
                        .setInnerLayersDimensions(innerDims)
                        .setInDim(dim)
                        .setOutDim(dim)
                        .create()
        );
    }
}