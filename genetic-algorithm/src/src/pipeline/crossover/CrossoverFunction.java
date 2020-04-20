package src.pipeline.crossover;

import src.models.Gen;

import java.util.List;

public interface CrossoverFunction {
    public List<Gen> cross(Gen gen1, Gen gen2);
}
