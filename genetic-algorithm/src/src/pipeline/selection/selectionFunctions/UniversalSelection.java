package src.pipeline.selection.selectionFunctions;

import src.models.Individual;
import src.pipeline.selection.fitnessFunctions.FitnessFunction;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class UniversalSelection implements SelectionFunction {

    @Override
    public List<Individual> select(List<Individual> individuals, FitnessFunction fitnessFunction, int resultSize) {
        List<Individual> result = new ArrayList<>();

        List<Double> values = Util.accValues(
                Util.relativeValues(
                        individuals
                            .stream()
                            .map(fitnessFunction::calculate)
                            .collect(Collectors.toList())
                    )
                );

        for(int i=0; i<resultSize; i++){
            double r = (Math.random()-i)/resultSize;
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