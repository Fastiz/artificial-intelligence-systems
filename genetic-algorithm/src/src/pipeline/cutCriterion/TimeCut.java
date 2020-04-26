package src.pipeline.cutCriterion;

import src.models.Individual;

import java.util.List;

public class TimeCut implements CutCriterion {
    private long duration;
    private long startTime;

    public TimeCut(int seconds) {
        duration = seconds * 1000;
        startTime = System.currentTimeMillis();
    }

    @Override
    public boolean shouldEnd(int generationNumber, List<Double> fitnessHistorial, List<List<Individual>> generations) {
        return duration < System.currentTimeMillis() - startTime;
    }
}
