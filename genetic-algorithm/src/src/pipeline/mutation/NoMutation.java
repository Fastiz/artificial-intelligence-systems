package src.pipeline.mutation;

import src.models.Alleles;
import src.models.Gen;
import src.pipeline.Mutation;

import java.util.List;

public class NoMutation implements Mutation {
    @Override
    public List<Gen> execute(List<Gen> genes, Alleles alleles) {
        return genes;
    }
}
