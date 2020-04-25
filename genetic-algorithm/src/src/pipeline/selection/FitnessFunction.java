package src.pipeline.selection;

import src.models.Individual;

public interface FitnessFunction {
    public double calculate(Individual individual);
}
