package src.pipeline.selection.selectionFunctions;

import src.models.Individual;
import src.pipeline.selection.fitnessFunctions.FitnessFunction;

import java.util.ArrayList;
import java.util.List;

public class UniversalSelection implements SelectionFunction {

    @Override
    public List<Individual> select(List<Individual> individuals, FitnessFunction fitnessFunction, int resultSize) {
        List<Double> accumValues = accValues(individuals, fitnessFunction);

        List<Individual> result = new ArrayList<>();

        for(int i=0; i< resultSize; i++){
            double r = (Math.random()+i)/(float)resultSize;
            for(int j=1; j<accumValues.size(); j++){
                if( r > accumValues.get(j-1) && r <= accumValues.get(j)){
                    result.add(individuals.get(j));
                    break;
                }
            }
        }

        return result;
    }

    private List<Double> accValues(List<Individual> individuals, FitnessFunction fitnessFunction){
        List<Double> result = new ArrayList<>();

        double curr=0;
        for(Individual ind : individuals){
            double val = curr + fitnessFunction.calculate(ind);
            result.add(val);
            curr = val;
        }
        return result;
    }
}