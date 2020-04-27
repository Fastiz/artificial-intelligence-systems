package src.pipeline.selection.fitnessFunctions;

import src.models.Individual;

public class Warrior implements FitnessFunction {
    @Override
    public double calculate(Individual individual) {
        double val =  0.6*Util.attack(individual) + 0.6*Util.defense(individual);
        return val;
    }
}
