package src.pipeline.mutation;
import src.models.Alleles;
import src.models.Individual;

import java.util.List;

public class UniformMultiGenMutation implements Mutation {
    private double probability;

    public UniformMultiGenMutation(double probability) {
        this.probability = probability;
    }

    @Override
    public List<Individual> execute(List<Individual> individuals, Alleles alleles) {
        for(Individual individual : individuals) {
            for(int genIndex = 0; genIndex < Individual.maxLocus; genIndex++) {
                if(Math.random() < probability) {
                    Utils.applyMutation(individual, genIndex, alleles);
                }
            }
        }
        return individuals;
    }
}
