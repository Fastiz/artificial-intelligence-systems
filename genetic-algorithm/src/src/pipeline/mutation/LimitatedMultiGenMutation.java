package src.pipeline.mutation;

import src.models.Alleles;
import src.models.Individual;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class LimitatedMultiGenMutation implements Mutation {
    private double probability;
    private List<Integer> allGenIndexes;
    private int generation;

    public LimitatedMultiGenMutation(double probability) {
        this.generation = 0;
        this.probability = probability;
        allGenIndexes = new ArrayList<>();
        for(int i = 0; i < Individual.maxLocus; i++) {
            allGenIndexes.add(i);
        }
    }

    @Override
    public List<Individual> execute(List<Individual> individuals, Alleles alleles) {
        this.generation++;
        for(Individual individual : individuals) {
            if(generation == individual.getGeneration()) {
                Collections.shuffle(allGenIndexes);

                int amountToMutate = 1 + (new Random()).nextInt(Individual.maxLocus - 1);
                List<Integer> genIndexes = allGenIndexes.subList(0, amountToMutate);

                for (Integer genIndex : genIndexes) {
                    if (Math.random() < probability) {
                        Utils.applyMutation(individual, genIndex, alleles);
                    }
                }
            }
        }
        return individuals;
    }
}
