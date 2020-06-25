package src.multilayerPerceptron;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BinaryOperator;

public class Utils {

    public static List<List<Double>> transpose(List<List<Double>> list){
        List<List<Double>> transposed = new ArrayList<>(list.get(0).size());

        for(int j=0; j<list.get(0).size(); j++){
            List<Double> aux = new ArrayList<>(list.size());
            for(int i=0; i<list.size(); i++){
                aux.add(list.get(i).get(j));
            }
            transposed.add(aux);
        }

        return transposed;
    }

    public static double dotProduct(List<Double> a, List<Double> b){
        if(a.size() != b.size())
            throw new IllegalArgumentException("Dot product can only we applied to lists of the same dimension." +
                    String.format("Received dimensions %s and %s.", a.size(), b.size()));

        double result = 0;
        for (int i=0; i<a.size(); i++){
            result += a.get(i)*b.get(i);
        }

        return result;
    }

    public static List<Double> elementwiseOperation(List<Double> a, List<Double> b, BinaryOperator<Double> operation){
        if(a.size() != b.size())
            throw new IllegalArgumentException("Elementwise subtraction can only we applied to lists of the same dimension." +
                    String.format("Received dimensions %s and %s.", a.size(), b.size()));

        List<Double> result = new ArrayList<>(a.size());
        for (int i=0; i<a.size(); i++){
            result.add(operation.apply(a.get(i), b.get(i)));
        }

        return result;
    }
}
