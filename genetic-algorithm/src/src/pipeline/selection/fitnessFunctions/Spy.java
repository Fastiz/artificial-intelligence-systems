package src.pipeline.selection.fitnessFunctions;

import src.models.Gen;
import src.pipeline.selection.FitnessFunction;

public class Spy implements FitnessFunction {
    @Override
    public double calculate(Gen gen) {
        return 0.8*Util.attack(gen) + 0.3*Util.defense(gen);
    }
}
