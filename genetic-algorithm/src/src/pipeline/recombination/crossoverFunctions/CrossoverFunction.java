package src.pipeline.recombination.crossoverFunctions;

import src.models.Individual;

import java.util.List;

public interface CrossoverFunction {
    public List<Individual> cross(Individual individual1, Individual individual2);
}
