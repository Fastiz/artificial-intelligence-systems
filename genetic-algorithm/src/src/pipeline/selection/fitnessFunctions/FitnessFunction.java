package src.pipeline.selection.fitnessFunctions;

import src.models.Individual;

public interface FitnessFunction {
    public double calculate(Individual individual);
}
