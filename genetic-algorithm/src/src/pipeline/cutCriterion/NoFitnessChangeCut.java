package src.pipeline.cutCriterion;

import src.models.Individual;

import java.util.List;

public class NoFitnessChangeCut implements CutCriterion {
    private int generationsAmount;
    private double error;

    public NoFitnessChangeCut(int generationsAmount, double error) {
        this.generationsAmount = generationsAmount;
        this.error = error;
    }

    public NoFitnessChangeCut(int generationsAmount) {
        this.generationsAmount = generationsAmount;
        this.error = Double.MIN_VALUE;
    }

    @Override
    public boolean shouldEnd(int generationNumber, List<Double> fitnessHistorial, List<List<Individual>> generations) {
        if(fitnessHistorial.size() < generationsAmount)
            return false;

       List<Double> lastestFitness = fitnessHistorial.subList(fitnessHistorial.size() - generationsAmount - 1, fitnessHistorial.size());

//        Compara si evoluciono con una diferencia mayor al error
//        for(int i = 0; i <lastestFitness.size() - 1; i++) {
//            if(Math.abs(lastestFitness.get(i) - lastestFitness.get(i+1)) - error > 0)
//                return false;
//        }

        // Compara si hay una diferencia mayor al error entre todos los ultimos N elementos,
        for(int i = 0; i <lastestFitness.size() - 1; i++) {
            for(int j = i; j < lastestFitness.size(); j ++) {
                if (Math.abs(lastestFitness.get(i) - lastestFitness.get(j)) - error > 0)
                    return false;
            }
        }

        return true;
    }
}
