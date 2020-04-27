package src.pipeline.recombination;

import src.models.Alleles;
import src.models.Individual;
import src.pipeline.recombination.crossoverFunctions.CrossoverFunction;
import src.pipeline.selection.fitnessFunctions.FitnessFunction;
import src.pipeline.selection.selectionFunctions.SelectionFunction;

import java.util.List;

public interface Recombination {

    public void setCrossoverFunction(CrossoverFunction crossoverFunction);

    public void setSelectionFunctions(SelectionFunction selectionFunction1, SelectionFunction selectionFunction2);

    public void setFitnessFunction(FitnessFunction fitnessFunction);

    public List<Individual> execute(List<Individual> genes, int childrenSize);
}
