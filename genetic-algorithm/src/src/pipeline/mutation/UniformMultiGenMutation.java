package src.pipeline.mutation;

import src.models.Alleles;
import src.models.Individual;

import java.util.List;

public class UniformMultiGenMutation implements Mutation {
    private double probability;
    private int generation;

    public UniformMultiGenMutation(double probability) {
        this.generation = 0;
        this.probability = probability;
    }

    @Override
    public List<Individual> execute(List<Individual> individuals, Alleles alleles) {
        generation++;
        for (Individual individual : individuals) {
            if (generation == individual.getGeneration()) {
                for (int genIndex = 0; genIndex < Individual.maxLocus; genIndex++) {
                    if (Math.random() < probability) {
                        Utils.applyMutation(individual, genIndex, alleles);
                    }
                }
            }
        }
        return individuals;
    }
}
