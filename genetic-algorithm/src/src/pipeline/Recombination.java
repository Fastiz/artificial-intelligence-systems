package src.pipeline;

import src.models.Gen;
import src.pipeline.crossover.CrossoverFunction;

import java.util.List;

public interface Recombination {

    public void setCrossoverFunction(CrossoverFunction crossoverFunction);

    public List<Gen> execute(List<Gen> genes);
}
