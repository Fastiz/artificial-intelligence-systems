package src.pipeline.selection.selectionFunctions;

import src.models.Individual;
import src.pipeline.selection.fitnessFunctions.FitnessFunction;

import java.util.ArrayList;

import java.util.List;
import java.util.PriorityQueue;

public class EliteSelection implements SelectionFunction {
    @Override
    public List<Individual> select(List<Individual> individuals, FitnessFunction fitnessFunction, int resultSize) {
        PriorityQueue<Individual> pq = new PriorityQueue<>(individuals.size(), (o1, o2) -> {
            double fit1 = fitnessFunction.calculate(o1), fit2 = fitnessFunction.calculate(o2);
            return -Double.compare(fit1, fit2);
        });
        pq.addAll(individuals);

        List<Individual> results = new ArrayList<>(resultSize);
        for(int i=0; i<resultSize; i++){
            results.add(pq.poll());
        }

        return results;
    }
}
