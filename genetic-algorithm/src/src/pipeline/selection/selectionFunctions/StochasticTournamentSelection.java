package src.pipeline.selection.selectionFunctions;

import src.models.Individual;
import src.pipeline.selection.fitnessFunctions.FitnessFunction;

import java.util.ArrayList;
import java.util.List;

public class StochasticTournamentSelection implements SelectionFunction{
    @Override
    public List<Individual> select(List<Individual> individuals, FitnessFunction fitnessFunction, int resultSize) {
        List<Individual> results = new ArrayList<>(resultSize);
        for(int i=0; i<resultSize; i++){
            double threshold = Math.random()/2 + 0.5;
            double r = Math.random();

            Individual ind1 = individuals.get((int)(Math.random()*individuals.size())),
                    ind2 = individuals.get((int)(Math.random()*individuals.size()));

            double fit1 = fitnessFunction.calculate(ind1),
                    fit2 = fitnessFunction.calculate(ind2);

            Individual result;
            if(r > threshold){
                result = fit1>fit2 ? ind2: ind1;
            }else{
                result = fit1>fit2 ? ind1: ind2;
            }

            results.add(result);
        }
        return results;
    }
}
