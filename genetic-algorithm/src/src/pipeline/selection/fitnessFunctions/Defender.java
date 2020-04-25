package src.pipeline.selection.fitnessFunctions;

import src.models.Individual;
import src.pipeline.selection.FitnessFunction;

public class Defender implements FitnessFunction {
    @Override
    public double calculate(Individual individual) {
        return 0.3*Util.attack(individual) + 0.8*Util.defense(individual);
    }
}
