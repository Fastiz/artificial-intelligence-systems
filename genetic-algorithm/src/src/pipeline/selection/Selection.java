package src.pipeline.selection;

import src.models.Individual;
import src.pipeline.selection.fitnessFunctions.FitnessFunction;
import src.pipeline.selection.selectionFunctions.SelectionFunction;

import java.util.List;

public interface Selection {
    public void setFitnessFunction(FitnessFunction fitnessFunction);

    public void setSelectionFunctions(SelectionFunction selectionFunction1, SelectionFunction selectionFunction2);

    public List<Individual> execute(List<Individual> individuals, int populationSize);
}
