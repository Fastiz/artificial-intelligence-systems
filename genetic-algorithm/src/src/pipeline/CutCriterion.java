package src.pipeline;

import src.models.Individual;

import java.util.List;

public interface CutCriterion {
    public boolean shouldEnd(int generationNumber, List<Double> fitnessHistorial, List<List<Individual>> generations);
}
