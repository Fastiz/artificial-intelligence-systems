package src.pipeline.selection.selectionFunctions;

import src.models.Individual;
import src.pipeline.selection.fitnessFunctions.FitnessFunction;

import java.util.ArrayList;
import java.util.List;

public class Util {
    public static List<Double> accValues(List<Double> fitnessValues){
        List<Double> result = new ArrayList<>();

        double curr=0;
        for(int i=0; i<fitnessValues.size(); i++){
            double val = curr + fitnessValues.get(i);
            result.add(val);
            curr = val;
        }
        return result;
    }

    public static List<Individual> roulette(List<Individual> individuals, List<Double> values, int resultSize){
        List<Individual> result = new ArrayList<>();

        for(int i=0; i< resultSize; i++){
            double r = Math.random();
            for(int j=1; j<values.size(); j++){
                if( r > values.get(j-1) && r <= values.get(j)){
                    result.add(individuals.get(j));
                    break;
                }
            }
        }

        return result;
    }
}
