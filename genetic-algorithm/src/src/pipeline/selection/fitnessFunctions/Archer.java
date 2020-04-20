package src.pipeline.selection.fitnessFunctions;

import src.models.Gen;
import src.pipeline.selection.FitnessFunction;

public class Archer implements FitnessFunction {
    @Override
    public double calculate(Gen gen) {
        return 0.9*Util.attack(gen) + 0.1*Util.defense(gen);
    }
}
