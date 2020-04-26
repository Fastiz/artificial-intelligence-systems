package src.pipeline.selection.selectionFunctions;

import src.models.Individual;
import src.pipeline.selection.fitnessFunctions.FitnessFunction;

import java.util.List;

public interface SelectionFunction {
    public List<Individual> select(List<Individual> individuals, FitnessFunction fitnessFunction, int resultSize);
}
