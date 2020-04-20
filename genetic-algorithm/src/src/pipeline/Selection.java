package src.pipeline;

import src.models.Gen;
import src.pipeline.selection.FitnessFunction;

import java.util.List;

public interface Selection {
    public void setFitnessFunction(FitnessFunction fitnessFunction);

    public List<Gen> execute(List<Gen> genes);
}
