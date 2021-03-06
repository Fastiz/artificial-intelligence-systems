package src.pipeline.cutCriterion;

import src.models.Individual;

import java.util.List;

public class GenerationAmountCut implements CutCriterion {

    private int maxGenerationNumber;

    public GenerationAmountCut(int maxGenerationNumber) {
        this.maxGenerationNumber = maxGenerationNumber;
    }

    @Override
    public boolean shouldEnd(int generationNumber, List<Double> fitnessHistorial, List<List<Individual>> generations) {
        return generationNumber >= maxGenerationNumber;
    }
}
