package src.pipeline.selection.fitnessFunctions;

import src.models.Individual;

public class Spy implements FitnessFunction {
    @Override
    public double calculate(Individual individual) {
        return 0.8*Util.attack(individual) + 0.3*Util.defense(individual);
    }
}
