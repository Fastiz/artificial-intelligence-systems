package src.files;

import src.files.Exceptions.MissingParameterException;
import src.files.Exceptions.NoValidInputException;
import src.pipeline.cutCriterion.*;
import src.pipeline.mutation.*;
import src.pipeline.recombination.crossoverFunctions.*;
import src.pipeline.selection.FillAllSelection;
import src.pipeline.selection.FillParentSelection;
import src.pipeline.selection.Selection;
import src.pipeline.selection.selectionFunctions.*;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ConfigReader {
    private final Map<String, String> properties;

    private final Mutation mutation;
    private final CrossoverFunction crossoverFunction;
    private final SelectionFunction selectionFunction;
    private final Selection selection;
    private final CutCriterion cutCriterion;

    public ConfigReader(String path) throws IOException, NoValidInputException, MissingParameterException {
        this.properties = new HashMap<>();
        try(BufferedReader bf = new BufferedReader(new FileReader(path))){

            for(String line=bf.readLine(); line != null; line=bf.readLine()){
                if (!line.isEmpty() && line.toCharArray()[0] != '#') {
                    String[] values = line.split("=");
                    properties.put(values[0].replaceAll("\\s+",""), values[1].replaceAll("\\s+",""));
                }
            }
        }

        cutCriterion = getCutCriterionFromFile();
        selectionFunction = getSelectionFunctionFromFile();
        selection = getSelectionFromFile();
        crossoverFunction = getCrossoverFunctionFromFile();
        mutation = getMutationFromFile();
    }

    private Selection getSelectionFromFile() throws NoValidInputException, MissingParameterException {
        int selection = Integer.parseInt(properties.get("selection"));

        switch (selection) {
            case 1: {
                if(!properties.containsKey("a"))
                    throw new MissingParameterException("a");
                if(!properties.containsKey("b"))
                    throw new MissingParameterException("b");
                double a = Integer.parseInt(properties.get("a"));
                double b = Integer.parseInt(properties.get("b"));
                return new FillAllSelection(a ,b);
            }
            case 2:
                if(!properties.containsKey("a"))
                    throw new MissingParameterException("a");
                if(!properties.containsKey("b"))
                    throw new MissingParameterException("b");
                double a = Integer.parseInt(properties.get("a"));
                double b = Integer.parseInt(properties.get("b"));
                return new FillParentSelection(a ,b);
        }
        throw new NoValidInputException("selection function");
    }

    private SelectionFunction getSelectionFunctionFromFile() throws NoValidInputException, MissingParameterException {
        int selectionFunction = Integer.parseInt(properties.get("selectionFunction"));

        switch (selectionFunction) {
            case 1: {
                if(!properties.containsKey("selectionParameter"))
                    throw new MissingParameterException("selectionParameter");
                if(!properties.containsKey("selectionParameter2"))
                    throw new MissingParameterException("selectionParameter2");
                if(!properties.containsKey("selectionParameter3"))
                    throw new MissingParameterException("selectionParameter3");

                double T0 = Double.parseDouble(properties.get("selectionParameter"));
                double TC = Double.parseDouble(properties.get("selectionParameter2"));
                double k = Double.parseDouble(properties.get("selectionParameter3"));
                return new BoltzmannSelection(T0, TC, k);
            }
            case 2:
                if(!properties.containsKey("selectionParameter"))
                    throw new MissingParameterException("selectionParameter");
                int M = Integer.parseInt(properties.get("selectionParameter"));
                return new DeterministicTournamentSelection(M);
            case 3:
                return new EliteSelection();
            case 4:
                return new RankingSelection();
            case 5:
                return new RouletteSelection();
            case 6:
                return new StochasticTournamentSelection();
            case 7:
                return new UniversalSelection();
        }
        throw new NoValidInputException("selection function");
    }

    private CutCriterion getCutCriterionFromFile() throws NoValidInputException, MissingParameterException {
        int criterion = Integer.parseInt(properties.get("cutMethod"));

        switch (criterion) {
            case 1: {
                if(!properties.containsKey("cutParameter"))
                    throw new MissingParameterException("cutParameter");
                int time = Integer.parseInt(properties.get("cutParameter"));
                return new TimeCut(time);
            }
            case 2:
                if(!properties.containsKey("cutParameter"))
                    throw new MissingParameterException("cutParameter");
                int generationAmount = Integer.parseInt(properties.get("cutParameter"));
                return new GenerationAmountCut(generationAmount);
            case 3:
                if(!properties.containsKey("cutParameter"))
                    throw new MissingParameterException("cutParameter");
                int acceptableFitness = Integer.parseInt(properties.get("cutParameter"));
                return new AcceptableSolutionCut(acceptableFitness);
            case 4:
                if(!properties.containsKey("cutParameter"))
                    throw new MissingParameterException("cutParameter");
                if(!properties.containsKey("cutParameter2"))
                    throw new MissingParameterException("cutParameter2");
                int generationToAnalize = Integer.parseInt(properties.get("cutParameter"));
                double error = Double.parseDouble(properties.get("cutParameter2"));
                return new NoFitnessChangeCut(generationToAnalize, error);

            case 5:
                if(!properties.containsKey("cutParameter"))
                    throw new MissingParameterException("cutParameter");
                if(!properties.containsKey("cutParameter2"))
                    throw new MissingParameterException("cutParameter2");
                int generationNumber = Integer.parseInt(properties.get("cutParameter"));
                double percentageChange = Double.parseDouble(properties.get("cutParameter2"));
                return new EstructureCut(generationNumber, percentageChange);
        }
        throw new NoValidInputException("cut criterion");
    }

    private CrossoverFunction getCrossoverFunctionFromFile() throws NoValidInputException {
        int crossover = Integer.parseInt(properties.get("crossover"));

        switch (crossover) {
            case 1:
                return new OnePointCross();
            case 2:
                return new TwoPointCross();
            case 3:
                return new RingCross();
            case 4:
                return new UniformCross();
        }
        throw new NoValidInputException("crossover");
    }

    private Mutation getMutationFromFile() throws NoValidInputException {
        int mutation = Integer.parseInt(properties.get("mutation"));
        double probability = Double.parseDouble(properties.get("mutationProbability"));

        switch (mutation) {
            case 1:
                return new SingleGenMutation(probability);
            case 2:
                return new LimitatedMultiGenMutation(probability);
            case 3:
                return new UniformMultiGenMutation(probability);
            case 4:
                return new CompleteMutation(probability);
        }
        throw new NoValidInputException("mutation");
    }

    public Mutation getMutation() {
        return mutation;
    }

    public CrossoverFunction getCrossoverFunction() {
        return crossoverFunction;
    }

    public SelectionFunction getSelectionFunction() {
        return selectionFunction;
    }

    public Selection getSelection() {
        return selection;
    }

    public CutCriterion getCutCriterion() {
        return cutCriterion;
    }

    private String getValue(String key){
        return properties.get(key);
    }
}
