package src.pipeline.mutation;

import src.models.Alleles;
import src.models.Individual;

import java.util.List;
import java.util.Random;

public class SingleGenMutation implements Mutation {

    private double probability;
    private int generation;

    public SingleGenMutation(double probability) {
        this.probability = probability;
        this.generation = 0;
    }

    @Override
    public List<Individual> execute(List<Individual> individuals, Alleles alleles) {
        this.generation++;
        for(Individual individual : individuals) {
            if(individual.getGeneration() == generation) {
                if (Math.random() < probability) {
                    int genIndex = (new Random()).nextInt(Individual.maxLocus);
                    Utils.applyMutation(individual, genIndex, alleles);
                }
            }
        }

        return individuals;
    }
}
