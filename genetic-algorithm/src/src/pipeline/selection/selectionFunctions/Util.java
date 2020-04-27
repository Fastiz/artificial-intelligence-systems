package src.pipeline.selection.selectionFunctions;

import src.models.Individual;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Util {
    public static List<Double> relativeValues(List<Double> fitnessValues){
        double sum = fitnessValues.stream().mapToDouble(Double::doubleValue).sum();
        return fitnessValues.stream().map(p->p/sum).collect(Collectors.toList());
    }

    public static List<Double> accValues(List<Double> fitnessValues){
        Double[] arr = fitnessValues.toArray(new Double[0]);
        Arrays.parallelPrefix(arr, Double::sum);
        return new ArrayList<>(Arrays.asList(arr));
    }

    public static List<Individual> roulette(List<Individual> individuals, List<Double> values, int resultSize){
        List<Individual> result = new ArrayList<>();

        for(int i=0; i<resultSize; i++){
            double r = Math.random();
            for(int j = 0; j<values.size(); j++){
                if(j == 0){
                    if(r <= values.get(0)){
                        result.add(individuals.get(j));
                    }
                }else if(j==values.size()-1) {
                    result.add(individuals.get(j));
                    break;
                }else if( r > values.get(j-1) && r <= values.get(j)){
                    result.add(individuals.get(j));
                    break;
                }
            }
        }

        return result;
    }
}
