package src.pipeline.selection;

import src.models.Individual;

import java.util.List;

public class FillAllSelection extends SelectionImpl {

    public FillAllSelection(double a, double b) {
        super(a, b);
    }

    @Override
    public List<Individual> execute(List<Individual> individuals, int populationSize) {
        List<Individual> newPopulation = super.select(individuals, populationSize);
        return newPopulation;
    }
}
