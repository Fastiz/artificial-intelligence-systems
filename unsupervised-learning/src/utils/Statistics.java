package utils;

import java.util.List;

public class Statistics {
    public static double std(List<Double> values){
        if(values.size() == 0)
            throw new IllegalArgumentException("No values to calculate std");

        double sum = 0.0, standardDeviation = 0.0;
        int length = values.size();

        for(double num : values) {
            sum += num;
        }

        double mean = sum/length;

        for(double num: values) {
            standardDeviation += Math.pow(num - mean, 2);
        }

        return Math.sqrt(standardDeviation/length);
    }

    public static double mean(List<Double> values){
        if(values.size() == 0)
            throw new IllegalArgumentException("No values to calculate mean");

        double sum = 0.0;

        for(double num : values) {
            sum += num;
        }

        return sum/values.size();
    }
}
