package src.pipeline.selection.fitnessFunctions;

import src.models.Gen;
import src.pipeline.selection.FitnessFunction;

public class Warrior implements FitnessFunction {
    @Override
    public double calculate(Gen gen) {
        return 0.6*Util.attack(gen) + 0.6*Util.defense(gen);
    }
}
