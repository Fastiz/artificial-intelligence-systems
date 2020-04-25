package src.pipeline.selection.fitnessFunctions;

import src.models.Individual;
import src.pipeline.selection.FitnessFunction;

public class Archer implements FitnessFunction {
    @Override
    public double calculate(Individual individual) {
        return 0.9*Util.attack(individual) + 0.1*Util.defense(individual);
    }
}
