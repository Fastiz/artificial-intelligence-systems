package src.pipeline.selection.selectionFunctions;

import src.models.Individual;
import src.pipeline.selection.fitnessFunctions.FitnessFunction;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class RouletteSelection implements SelectionFunction {


    @Override
    public List<Individual> select(List<Individual> individuals, FitnessFunction fitnessFunction, int resultSize) {
        List<Double> fitnessValues = individuals.stream().map(fitnessFunction::calculate).collect(Collectors.toList());

        List<Double> accumValues = Util.accValues(fitnessValues);

        return Util.roulette(individuals, accumValues, resultSize);
    }


}
