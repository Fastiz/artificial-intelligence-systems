package src.pipeline;

import src.models.Individual;
import src.pipeline.selection.fitnessFunctions.FitnessFunction;

import java.util.List;

public interface Selection {
    public void setFitnessFunction(FitnessFunction fitnessFunction);

    public List<Individual> execute(List<Individual> genes, int populationSize);
}
