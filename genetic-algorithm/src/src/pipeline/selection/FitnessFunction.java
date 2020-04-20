package src.pipeline.selection;

import src.models.Gen;

public interface FitnessFunction {
    public double calculate(Gen gen);
}
