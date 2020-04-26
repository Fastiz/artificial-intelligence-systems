package src.pipeline.mutation;

import src.models.Alleles;
import src.models.Individual;

import java.util.List;

public class NoMutation implements Mutation {
    @Override
    public List<Individual> execute(List<Individual> genes, Alleles alleles) {
        return genes;
    }
}
