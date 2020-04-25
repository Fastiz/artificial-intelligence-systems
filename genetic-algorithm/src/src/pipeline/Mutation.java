package src.pipeline;

import src.models.Alleles;
import src.models.Individual;

import java.util.List;

public interface Mutation {
    public List<Individual> execute(List<Individual> genes, Alleles alleles);
}
