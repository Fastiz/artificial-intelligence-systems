package src.pipeline.selection.fitnessFunctions;

import src.models.Gen;
import src.pipeline.selection.FitnessFunction;

public class Defender implements FitnessFunction {
    @Override
    public double calculate(Gen gen) {
        return 0.3*Util.attack(gen) + 0.8*Util.defense(gen);
    }
}
