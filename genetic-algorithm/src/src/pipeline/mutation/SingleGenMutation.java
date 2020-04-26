package src.pipeline.mutation;

import src.models.Alleles;
import src.models.Individual;

import java.util.List;
import java.util.Random;

public class SingleGenMutation implements Mutation {

    private double probability;

    public SingleGenMutation(double probability) {
        this.probability = probability;
    }

    @Override
    public List<Individual> execute(List<Individual> individuals, Alleles alleles) {
        for(Individual individual : individuals) {
            if(Math.random() < probability) {
                int genIndex = (new Random()).nextInt(Individual.maxLocus);
                Utils.applyMutation(individual, genIndex, alleles);
            }
        }

        return individuals;
    }
}
