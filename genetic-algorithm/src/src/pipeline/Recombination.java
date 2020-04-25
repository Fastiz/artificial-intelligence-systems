package src.pipeline;

import src.models.Alleles;
import src.models.Individual;
import src.pipeline.crossover.CrossoverFunction;

import java.util.List;

public interface Recombination {

    public void setCrossoverFunction(CrossoverFunction crossoverFunction);

    public List<Individual> execute(List<Individual> genes, Alleles alleles);
}
