package src.pipeline.selection.selectionFunctions;

import src.models.Individual;
import src.pipeline.selection.fitnessFunctions.FitnessFunction;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;

public class EliteSelection implements SelectionFunction {
    @Override
    public List<Individual> select(List<Individual> individuals, FitnessFunction fitnessFunction, int resultSize) {
        PriorityQueue<Individual> pq = new PriorityQueue<>(individuals.size(), (o1, o2) -> {
            double fit1 = fitnessFunction.calculate(o1), fit2 = fitnessFunction.calculate(o2);
            return Double.compare(fit1, fit2);
        });
        pq.addAll(individuals);

        List<Individual> result = new ArrayList<>();

        int remainingSpots = resultSize;
        for(int i=0; remainingSpots>0; i++){
            int n = (int)Math.ceil((resultSize - i)/ (float) individuals.size());

            Individual current = pq.poll();

            for(int j=n; j>0; j--){
                result.add(current);
            }

            remainingSpots-=n;
        }

        return result;
    }
}
