package src.pipeline.selection;

import src.models.Gen;
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
    public List<Gen> execute(List<Gen> genes, int populationSize) {
        PriorityQueue<Gen> pq = new PriorityQueue<>(genes.size(), new Comparator<Gen>() {
            @Override
            public int compare(Gen o1, Gen o2) {
                double fit1 = fitnessFunction.calculate(o1), fit2 = fitnessFunction.calculate(o2);
                return Double.compare(fit1, fit2);
            }
        });
        pq.addAll(genes);

        List<Gen> bestGenes = new ArrayList<>();
        int aux=populationSize;
        while(aux != 0){
            bestGenes.add(pq.poll());
            aux--;
        }

        return bestGenes;
    }
}
