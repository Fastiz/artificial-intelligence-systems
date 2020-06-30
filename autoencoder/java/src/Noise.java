package src;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Noise {

    public static List<Double> saltAndPepper(List<Double> list, double noiseFactor) {
        return applyNoise(list, noiseFactor, false);
    }

    public static List<Double> gaussianNoise(List<Double> list, double noiseFactor) {
        return applyNoise(list, noiseFactor, true);
    }

    private static List<Double> applyNoise(List<Double> list, double noiseFactor, boolean gaussian) {
        List<Double> noiseList = new ArrayList<>();
        Random random = new Random();
        for (Double bit : list) {
            //No estoy tan seguro si esta bien que la meia sea 0 en guassiano
            double randomNumber = gaussian ? random.nextGaussian() : random.nextDouble();
            if (randomNumber < noiseFactor)  {
                noiseList.add(invertedBit(bit));
            } else {
                noiseList.add(bit);
            }
        }
        return noiseList;
    }

    private static double invertedBit(double bit) {
        return bit == -1.0 ? 1.0 : -1.0;
    }
}