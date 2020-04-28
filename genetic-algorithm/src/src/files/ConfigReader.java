package src.files;

import src.files.Exceptions.MissingParameterException;
import src.files.Exceptions.NoValidInputException;
import src.pipeline.cutCriterion.*;
import src.pipeline.mutation.*;
import src.pipeline.recombination.ConsecutivePairsRecombination;
import src.pipeline.recombination.Recombination;
import src.pipeline.recombination.crossoverFunctions.*;
import src.pipeline.selection.FillAllSelection;
import src.pipeline.selection.FillParentSelection;
import src.pipeline.selection.Selection;
import src.pipeline.selection.fitnessFunctions.*;
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
    private final Recombination recombination;
    private final Selection selection;
    private final CutCriterion cutCriterion;
    private final FitnessFunction fitnessFunction;

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
        mutation = getMutationFromFile();
        selection = getSelectionFromFile();
        fitnessFunction = getFitnessFunctionFromFile();

        recombination = new ConsecutivePairsRecombination(1, 0);
        recombination.setCrossoverFunctions(getCrossoverFunctionFromFile(), null);

        selection.setSelectionFunctions(getSelectionFunctionFromFile(), null);
        selection.setFitnessFunction(fitnessFunction);
    }

    private FitnessFunction getFitnessFunctionFromFile() throws NoValidInputException, MissingParameterException {
        String key = "fitnessFunction";
        if(properties.containsKey(key)) {
            int fitnessFunction = Integer.parseInt(properties.get(key));

            switch (fitnessFunction) {
                case 1:
                    return new Archer();
                case 2:
                    return new Defender();
                case 3:
                    return new Spy();
                case 4:
                    return new Warrior();
            }
        }
        throw new NoValidInputException(key);
    }

    private Selection getSelectionFromFile() throws NoValidInputException, MissingParameterException {
        String key = "selection";
        if(properties.containsKey(key)) {
            int selection = Integer.parseInt(properties.get(key));
            switch (selection) {
                case 1: {
                    if (!properties.containsKey("a"))
                        throw new MissingParameterException("a");
                    if (!properties.containsKey("b"))
                        throw new MissingParameterException("b");
                    double a = Integer.parseInt(properties.get("a"));
                    double b = Integer.parseInt(properties.get("b"));
                    return new FillAllSelection(a, b);
                }
                case 2:
                    if (!properties.containsKey("a"))
                        throw new MissingParameterException("a");
                    if (!properties.containsKey("b"))
                        throw new MissingParameterException("b");
                    double a = Integer.parseInt(properties.get("a"));
                    double b = Integer.parseInt(properties.get("b"));
                    return new FillParentSelection(a, b);
            }
        }
        throw new NoValidInputException(key);
    }

    private SelectionFunction getSelectionFunctionFromFile() throws NoValidInputException, MissingParameterException {
        String key = "selectionFunction";
        if(properties.containsKey(key)) {
        int selectionFunction = Integer.parseInt(properties.get(key));
        String parameter1 = "selectionParameter";
        String parameter2 = "selectionParameter2";
        String parameter3 = "selectionParameter3";
        switch (selectionFunction) {
            case 1: {
                if (!properties.containsKey(parameter1))
                    throw new MissingParameterException(parameter1);
                if (!properties.containsKey(parameter2))
                    throw new MissingParameterException(parameter2);
                if (!properties.containsKey(parameter3))
                    throw new MissingParameterException(parameter3);

                double T0 = Double.parseDouble(properties.get(parameter1));
                double TC = Double.parseDouble(properties.get(parameter2));
                double k = Double.parseDouble(properties.get(parameter3));
                return new BoltzmannSelection(T0, TC, k);
            }
            case 2:
                if (!properties.containsKey(parameter1))
                    throw new MissingParameterException(parameter1);
                int M = Integer.parseInt(properties.get(parameter1));
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
    }
        throw new NoValidInputException(key);
    }

    private CutCriterion getCutCriterionFromFile() throws NoValidInputException, MissingParameterException {
        String key = "cutMethod";
        if(properties.containsKey(key)) {
            int criterion = Integer.parseInt(properties.get(key));

            String parameter1 = "cutParameter";
            String parameter2 = "cutParameter2";
            switch (criterion) {
                case 1: {
                    if (!properties.containsKey(parameter1))
                        throw new MissingParameterException(parameter1);
                    int time = Integer.parseInt(properties.get(parameter1));
                    return new TimeCut(time);
                }
                case 2:
                    if (!properties.containsKey(parameter1))
                        throw new MissingParameterException(parameter1);
                    int generationAmount = Integer.parseInt(properties.get(parameter1));
                    return new GenerationAmountCut(generationAmount);
                case 3:
                    if (!properties.containsKey(parameter1))
                        throw new MissingParameterException(parameter1);
                    int acceptableFitness = Integer.parseInt(properties.get(parameter1));
                    return new AcceptableSolutionCut(acceptableFitness);
                case 4:
                    if (!properties.containsKey(parameter1))
                        throw new MissingParameterException(parameter1);
                    if (!properties.containsKey(parameter2))
                        throw new MissingParameterException(parameter2);
                    int generationToAnalize = Integer.parseInt(properties.get(parameter1));
                    double error = Double.parseDouble(properties.get(parameter2));
                    return new NoFitnessChangeCut(generationToAnalize, error);
                case 5:
                    if (!properties.containsKey(parameter1))
                        throw new MissingParameterException(parameter1);
                    if (!properties.containsKey(parameter2))
                        throw new MissingParameterException(parameter2);
                    int generationNumber = Integer.parseInt(properties.get(parameter1));
                    double percentageChange = Double.parseDouble(properties.get(parameter2));
                    return new EstructureCut(generationNumber, percentageChange);
            }
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

    public Recombination getRecombination() {
        return recombination;
    }

    public Selection getSelection() {
        return selection;
    }

    public FitnessFunction getFitnessFunction() {
        return fitnessFunction;
    }

    public CutCriterion getCutCriterion() {
        return cutCriterion;
    }

    private String getValue(String key){
        return properties.get(key);
    }
}
