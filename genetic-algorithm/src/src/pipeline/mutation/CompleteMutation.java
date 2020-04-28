package src.pipeline.mutation;

import src.models.Alleles;
import src.models.Individual;

import java.util.List;

public class CompleteMutation implements Mutation {
    private double probability;
    private int generation;

    public CompleteMutation(double probability) {
        this.probability = probability;
        this.generation = 0;
    }

    @Override
    public List<Individual> execute(List<Individual> individuals, Alleles alleles) {
        this.generation++;
        for (Individual individual : individuals) {
            if(individual.getGeneration() == generation) {
                if (Math.random() < probability) {
                    for (int genIndex = 0; genIndex < Individual.maxLocus; genIndex++) {
                        Utils.applyMutation(individual, genIndex, alleles);
                    }
                }
            }
        }
        return individuals;
    }
}
