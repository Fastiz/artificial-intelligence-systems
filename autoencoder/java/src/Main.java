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

    public static void main(String[] args) {
        errorCalculatorIterations(50000, 2);
    }

    private static void encodeDecodeForFont(int fontNum, double noiseFactor, int limit) {

        List<LetterData> trainingData = FontManager.getFont(fontNum, limit);
        AutoEncoder autoEncoder = getEncoder(trainingData.get(0).getInput().size(), Arrays.asList(60, 10, 2, 10, 60));


        stochasticTraining(autoEncoder, trainingData, 200000);

        try (BufferedWriter bf = new BufferedWriter(new FileWriter("data"))) {
            double totalError = 0;
            String[] fontNames = FontManager.getFontNames(fontNum);

            for (int i = 0; i < trainingData.size(); i++) {
                LetterData letterData = trainingData.get(i);
                List<Double> c = letterData.getInput();

                List<Double> encoded = autoEncoder.encode(c);
                List<Double> encodeDecode = autoEncoder.decode(encoded);

                List<Double> encodeDecodeRounded = encodeDecode.stream()
                        .mapToDouble(v -> v > 0 ? 1.0 : -1.0).boxed().collect(Collectors.toList());

                System.out.println("\n\n-------------");

                printLetter(c);

                System.out.println("\n");

                printLetter(encodeDecodeRounded);

                System.out.println("\n");

                System.out.println(encoded);

                double diff = IntStream.range(0, encodeDecode.size()).mapToDouble(s -> Math.abs(encodeDecodeRounded.get(s) - c.get(s))).sum();
                totalError += diff / 2;

                if (Double.valueOf(noiseFactor).equals(0.0) && i < fontNames.length) {
                    bf.write(encoded.get(0) + " " + encoded.get(1) + " " + fontNames[i] + "\n");
                } else {
                    bf.write(encoded.get(0) + " " + encoded.get(1) + "\n");
                }
            }

            System.out.println(String.format("Average difference is %s out of %s (%s)",
                    totalError / trainingData.size(),
                    trainingData.get(0).getInput().size(),
                    totalError / trainingData.size() / trainingData.get(0).getInput().size()));
        } catch (IOException e) {
            System.err.print(e);
            return;
        }
    }

    private static void errorCalculatorNoise(int fontNum) {
        List<LetterData> trainingData;
        try (BufferedWriter bf = new BufferedWriter(new FileWriter("./autoencoder/errorNoise"))) {
            for (double noiseFactor = 0.05; noiseFactor < 0.4; noiseFactor+=0.05) {
                trainingData = FontManager.getFont(fontNum, noiseFactor);
                AutoEncoder autoEncoder = getEncoder(trainingData.get(0).getInput().size(), Arrays.asList(15, 2, 15));
                for (int it = 0; it < 5000; it++) {
                    for (LetterData letterData : trainingData) {
                        autoEncoder.step(letterData.getInput(), letterData.getOutput());
                    }
                    double error = 0;
                    for (int ln = 0; ln < trainingData.size() / 2; ln++) {
                        LetterData letterData = trainingData.get(ln);
                        autoEncoder.step(letterData.getInput(), letterData.getOutput());
                        error += getECM(letterData.getInput(), autoEncoder.decode(autoEncoder.encode(letterData.getInput())));
                    }
                    error = Math.sqrt(error / (trainingData.size() * trainingData.get(0).getInput().size()));
                    bf.write(error + "\n");
                }
            }
        } catch (Exception e) {
            System.out.println(e);
            return;
        }
    }

    private static void errorCalculatorSublist(int fontNum) {
        List<LetterData> trainingData = FontManager.getFont(fontNum);

        try (BufferedWriter bf = new BufferedWriter(new FileWriter("./autoencoder/errorSublist"))) {
            for (int k = 1; k < trainingData.size(); k++) {
                trainingData = FontManager.getFont(fontNum, k);
                AutoEncoder autoEncoder = getEncoder(trainingData.get(0).getInput().size(), Arrays.asList(15, 2, 15));
                for (int it = 0; it < 5000; it++) {
                    for (LetterData letterData : trainingData) {
                        autoEncoder.step(letterData.getInput(), letterData.getOutput());
                    }
                    double error = 0;
                    for (LetterData letterData : trainingData) {
                        autoEncoder.step(letterData.getInput(), letterData.getOutput());
                        error += getECM(letterData.getInput(), autoEncoder.decode(autoEncoder.encode(letterData.getInput())));
                    }
                    error = Math.sqrt(error / (trainingData.size() * trainingData.get(0).getInput().size()));
                    bf.write(error + "\n");
                }
            }
        } catch (Exception e) {
            System.out.println(e);
            return;
        }
    }

    private static void errorCalculatorIterations(int maxIteration, int fontNum) {
        List<LetterData> trainingData = FontManager.getFont(fontNum);
        AutoEncoder autoEncoder = getEncoder(trainingData.get(0).getInput().size(), Arrays.asList(15, 2, 15));

        try (BufferedWriter bf = new BufferedWriter(new FileWriter("./autoencoder/errorIt"))) {
            for (int it = 0; it < maxIteration; it++) {
                for (LetterData letterData : trainingData) {
                    autoEncoder.step(letterData.getInput(), letterData.getOutput());
                }
                double error = 0;
                for (LetterData letterData : trainingData) {
                    autoEncoder.step(letterData.getInput(), letterData.getOutput());
                    error += getECM(letterData.getInput(), autoEncoder.decode(autoEncoder.encode(letterData.getInput())));
                }
                error = Math.sqrt(error / (trainingData.size() * trainingData.get(0).getInput().size()));
                bf.write(error + "\n");
            }
        } catch (Exception e) {
            System.out.println(e);
            return;
        }
    }

    private static double getECM(List<Double> original, List<Double> decoded) {
        double error = 0;
        for (int i = 0; i < original.size(); i++) {
            error += Math.pow(original.get(i) - decoded.get(i), 2);
        }
        return error;
    }

    private static void generateFont(int fontNum, int limit) {
        List<LetterData> trainingData = FontManager.getFont(fontNum, limit);
        AutoEncoder autoEncoder = getEncoder(trainingData.get(0).getInput().size(), Arrays.asList(10, 2, 10));


        epochTraining(autoEncoder, trainingData, 5000);

        List<Double> encodedMean = new ArrayList<>(2);
        encodedMean.add(0.0);
        encodedMean.add(0.0);
        for (LetterData letterData : trainingData) {
            printLetter(letterData.getInput());
            List<Double> encoded = autoEncoder.encode(letterData.getInput());
            System.out.println('\n');
            encodedMean.set(0, encodedMean.get(0) + encoded.get(0));
            encodedMean.set(1, encodedMean.get(1) + encoded.get(1));
        }

        encodedMean.set(0, encodedMean.get(0) / trainingData.size());
        encodedMean.set(1, encodedMean.get(1) / trainingData.size());

        List<Double> encodeDecodeRounded2 = autoEncoder.decode(encodedMean).stream()
                .mapToDouble(v -> v > 0 ? 1.0 : -1.0).boxed().collect(Collectors.toList());

        printLetter(encodeDecodeRounded2);
    }

    private static void epochTraining(AutoEncoder ae, List<LetterData> trainingData, int itNum) {
        for (int it = 0; it < itNum; it++) {
            for (LetterData letterData : trainingData) {
                ae.step(letterData.getInput(), letterData.getOutput());
            }
        }
    }

    private static void stochasticTraining(AutoEncoder ae, List<LetterData> trainingData, int itNum) {
        Random rnd = new Random();
        for (int it = 0; it < itNum; it++) {
            int randIndex = rnd.nextInt(trainingData.size());
            LetterData letterData = trainingData.get(randIndex);
            ae.step(letterData.getInput(), letterData.getOutput());
        }
    }

    private static void printLetter(List<Double> letter) {
        int rowSize = 4;

        int rowIndex = 0;
        for (Double c : letter) {
            System.out.print(c.equals(-1.0) ? " " : "x");

            if (rowIndex == rowSize) {
                System.out.print("\n");
                rowIndex = 0;
            } else {
                rowIndex++;
            }
        }
    }

    private static AutoEncoder getEncoder(int dim, List<Integer> innerDims) {
        double alpha = 0.001, btan = 1;
        int numberOfIterationsToReachAlpha = 10000;
        double a = 10, b = -Math.log(alpha * (1 / a)) / numberOfIterationsToReachAlpha;

        return new AutoEncoder(
                (new MultiLayerPerceptron.Builder())
                        .setActivationFunction((x) -> Math.tanh(btan * x))
                        .setActivationFunctionDerivative(p -> btan * (1 - Math.pow(Math.tanh(p), 2)))
//                        .setTemperatureFunction(s->a*Math.exp(-b*s))
                        .setAlpha(alpha)
                        .setInnerLayersDimensions(innerDims)
                        .setInDim(dim)
                        .setOutDim(dim)
                        .create()
        );
    }
}