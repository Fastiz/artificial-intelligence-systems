package src.pipeline.selection.selectionFunctions;

import src.models.Individual;
import src.pipeline.selection.fitnessFunctions.FitnessFunction;

import java.util.ArrayList;
import java.util.List;

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
        List<Individual> r = new ArrayList<>(M);
        int i = 0;
        int j;
        for (Individual individual : individuals) {
            if (i < M) {
                r.set(i++, individual);
            }else {
                j = (int)(Math.random()*(i++ - 1) + 1);
                if (j <= M)
                    r.set(j, individual);
            }

        }
        return r;
    }
}