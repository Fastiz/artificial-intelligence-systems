package src.pipeline.mutation;

import src.models.Alleles;
import src.models.Individual;
import src.pipeline.Mutation;
import java.util.List;

public class CompleteMutation implements Mutation {
    private double probability;

    public CompleteMutation(double probability) {
        this.probability = probability;
    }

    @Override
    public List<Individual> execute(List<Individual> individuals, Alleles alleles) {
        for (Individual individual : individuals) {
            if (Math.random() < probability) {
                for (int genIndex = 0; genIndex < Individual.maxLocus; genIndex++) {
                    Utils.applyMutation(individual, genIndex, alleles);
                }
            }
        }
        return individuals;
    }
}
