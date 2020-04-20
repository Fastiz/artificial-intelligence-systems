package src.pipeline.crossover;

import src.models.Gen;

public interface CrossoverFunction {
    public Gen cross(Gen gen1, Gen gen2);
}
