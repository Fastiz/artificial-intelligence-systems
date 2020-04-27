package src.pipeline.selection.selectionFunctions;

import src.models.Individual;
import src.pipeline.selection.fitnessFunctions.FitnessFunction;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class DeterministicTournamentSelection implements SelectionFunction {

    private final int M;

    public DeterministicTournamentSelection(int M){
        this.M = M;
    }

    @Override
    public List<Individual> select(List<Individual> individuals, FitnessFunction fitnessFunction, int resultSize) {
        List<Individual> results = new ArrayList<>(resultSize);
        for(int i=0; i<resultSize; i++){
            List<Individual> subset = randomSubset(individuals, M);

            Individual maxInd = null;
            double maxVal = 0;
            for(int j=0; j<M; j++){
                Individual currInd = subset.get(j);
                double currVal = fitnessFunction.calculate(currInd);

                if(maxInd == null || maxVal < currVal){
                    maxInd = currInd;
                    maxVal = currVal;
                }
            }

            results.add(maxInd);
        }
        return results;
    }

    private List<Individual> randomSubset(List<Individual> individuals, int M) {
        Collections.shuffle(individuals);
        return individuals.stream().limit(M).collect(Collectors.toList());
    }
}