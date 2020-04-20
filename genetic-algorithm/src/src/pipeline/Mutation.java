package src.pipeline;

import src.models.Gen;

import java.util.List;

public interface Mutation {
    public List<Gen> execute(List<Gen> genes);
}
