package src.pipeline.selection.selectionFunctions;

import src.models.Individual;
import src.pipeline.selection.fitnessFunctions.FitnessFunction;

import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class RankingSelection implements SelectionFunction{
    @Override
    public List<Individual> select(List<Individual> individuals, FitnessFunction fitnessFunction, int resultSize) {
        PriorityQueue<Individual> pq = new PriorityQueue<>((o1, o2) -> Double.compare(fitnessFunction.calculate(o1), fitnessFunction.calculate(o2)));
        pq.addAll(individuals);

        List<Double> values;

        values = pq.stream().map(fitnessFunction::calculate).collect(Collectors.toList());

        int size = values.size();
        values = IntStream.range(0, values.size())
                .mapToObj(i -> newFitness(i+1, size))
                .collect(Collectors.toList());

        values = Util.accValues(Util.relativeValues(values));

        return Util.roulette(individuals, values, resultSize);

    }

    private Double newFitness(int i, int N){
        return (N - i)/(double)N;
    }
}
