package src.pipeline.cutCriterion;

import src.models.Individual;

import java.util.List;

public class AcceptableSolutionCut implements CutCriterion {

    private double acceptableFitness;

    public AcceptableSolutionCut(double acceptableFitness) {
        this.acceptableFitness = acceptableFitness;
    }

    @Override
    public boolean shouldEnd(int generationNumber, List<Double> fitnessHistorial, List<List<Individual>> generations) {
        if(fitnessHistorial.isEmpty())
            return false;
        return fitnessHistorial.get(fitnessHistorial.size()-1) >= acceptableFitness;
    }
}
