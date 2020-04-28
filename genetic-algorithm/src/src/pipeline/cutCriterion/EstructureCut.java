package src.pipeline.cutCriterion;

import src.models.Individual;

import java.util.List;

public class EstructureCut implements CutCriterion {

    // Numero de generaciones a comparar
    private int generationsAmount;
    // Cantidad de individuos que si coincide corta
    private double changePercentage;

    public EstructureCut(int generationsAmount, double changePercentage) {
        this.changePercentage = changePercentage;
        this.generationsAmount = generationsAmount;
    }

    @Override
    public boolean shouldEnd(int generationNumber, List<Double> fitnessHistorial, List<List<Individual>> generations) {
        if(generationNumber - 1 < generationsAmount)
            return false;

        return shouldEndCompareAll(generations);
    }

    private boolean shouldEndCompareStartAndEnd(List<List<Individual>> generations) {
        int endIndex = generations.size() - 1;
        int startIndex = endIndex - generationsAmount;

        return coincidencesBetweenGenerations(generations.get(startIndex), generations.get(endIndex)) >= changePercentage;
    }

    private boolean shouldEndCompareAll(List<List<Individual>> generations) {
        for(int i = generations.size() - generationsAmount - 1; i < generations.size() - 1; i++) {
            for(int j = i; j < generations.size(); j++) {
                double coincidencesPercentage = coincidencesBetweenGenerations(generations.get(i), generations.get(j));
                if(coincidencesPercentage < changePercentage)
                    return false;
            }
        }
        return true;
    }

    private double coincidencesBetweenGenerations(List<Individual> generation1, List<Individual> generation2) {
        int totalIndividuals = generation1.size();
        int coincidences = 0;
        for(Individual individual : generation1) {
            if(generation2.contains(individual)) {
                coincidences++;
            }
        }
        return (double)coincidences / totalIndividuals;
    }
}
