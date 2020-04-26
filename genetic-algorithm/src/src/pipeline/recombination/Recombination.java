package src.pipeline.recombination;

import src.models.Alleles;
import src.models.Individual;
import src.pipeline.recombination.crossoverFunctions.CrossoverFunction;

import java.util.List;

public interface Recombination {

    public void setCrossoverFunctions(CrossoverFunction crossoverFunction1, CrossoverFunction crossoverFunction2);

    public List<Individual> execute(List<Individual> genes, int childrenSize);
}
