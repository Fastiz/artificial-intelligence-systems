package src.pipeline.selection.fitnessFunctions;

import src.models.Individual;
import src.pipeline.selection.FitnessFunction;

public class Warrior implements FitnessFunction {
    @Override
    public double calculate(Individual individual) {
        return 0.6*Util.attack(individual) + 0.6*Util.defense(individual);
    }
}
