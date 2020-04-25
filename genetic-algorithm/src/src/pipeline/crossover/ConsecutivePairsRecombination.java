package src.pipeline.crossover;

import src.models.Alleles;
import src.models.Individual;
import src.pipeline.Recombination;

import java.util.ArrayList;
import java.util.List;

public class ConsecutivePairsRecombination implements Recombination {
    private CrossoverFunction crossoverFunction;

    @Override
    public void setCrossoverFunction(CrossoverFunction crossoverFunction) {
        this.crossoverFunction = crossoverFunction;
    }

    @Override
    public List<Individual> execute(List<Individual> genes, Alleles alleles) {
        List<Individual> newGenes = new ArrayList<>(genes);
        for(int i=0; i<genes.size()/2; i++){
            newGenes.addAll(this.crossoverFunction.cross(genes.get(i*2), genes.get(i*2+1)));
        }
        return newGenes;
    }
}
