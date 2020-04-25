package src.pipeline.selection;

import src.models.Individual;
import src.pipeline.Selection;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;

public class BestFitnessSelection implements Selection {
    private FitnessFunction fitnessFunction;

    @Override
    public void setFitnessFunction(FitnessFunction fitnessFunction) {
        this.fitnessFunction = fitnessFunction;
    }

    @Override
    public List<Individual> execute(List<Individual> genes, int populationSize) {
        PriorityQueue<Individual> pq = new PriorityQueue<>(genes.size(), new Comparator<Individual>() {
            @Override
            public int compare(Individual o1, Individual o2) {
                double fit1 = fitnessFunction.calculate(o1), fit2 = fitnessFunction.calculate(o2);
                return Double.compare(fit1, fit2);
            }
        });
        pq.addAll(genes);

        List<Individual> bestGenes = new ArrayList<>();
        int aux=populationSize;
        while(aux != 0){
            bestGenes.add(pq.poll());
            aux--;
        }

        return bestGenes;
    }
}
