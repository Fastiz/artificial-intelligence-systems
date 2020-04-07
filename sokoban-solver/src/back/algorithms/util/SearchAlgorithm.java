package back.algorithms.util;

import back.AlgorithmSolution;
import back.interfaces.Game;
import back.interfaces.Heuristic;

import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;

public class SearchAlgorithm {
    private int expandedNodes;
    private Game gameSolved;
    private HashSet<Game> hashSet;
    private SearchCollection<Game> collection;
    private Heuristic heuristic = null;
    private String name;

    public SearchAlgorithm(String name) {
        this.name = name;
    }

    public AlgorithmSolution run(Game game, Comparator<Game> comparator) {
        this.collection = new SearchCollection<>(comparator);
        return runAux(game);
    }

    public AlgorithmSolution run(Game game, Comparator<Game> comparator, Heuristic heuristic) {
        this.heuristic = heuristic;
        game.setHeuristicValue(heuristic.evaluate(game));
        return run(game, comparator);
    }

    public AlgorithmSolution run(Game game, boolean FIFO) {
        this.collection = new SearchCollection<>(FIFO);
        return runAux(game);
    }

    private AlgorithmSolution runAux(Game game) {
        hashSet = new HashSet<>();
        expandedNodes = 0;
        gameSolved = null;

        this.collection.add(game);
        this.hashSet.add(game);

        long startTime = System.currentTimeMillis();
        boolean result = search();
        long endTime = System.currentTimeMillis();

        AlgorithmSolution solution;
        if (result)
            solution = new AlgorithmSolution(this.name, this.expandedNodes, collection.size(), this.gameSolved, endTime - startTime);
        else
            solution = new AlgorithmSolution(this.name, false, this.expandedNodes, endTime - startTime);

        this.heuristic = null;
        return solution;
    }

    private boolean search() {

        while (!collection.isEmpty()) {
            Game game = collection.pop();

            if (game.gameFinished()) {
                this.gameSolved = game;
                return true;
            }

            AtomicBoolean childAdded = new AtomicBoolean(false);
            game.calculateChildren().stream()
                    .filter(child -> !hashSet.contains(child))
                    .forEach(child -> {
                        childAdded.set(true);
                        if (heuristic != null)
                            child.setHeuristicValue(heuristic.evaluate(child));
                        collection.add(child);
                        hashSet.add(child);
                    });

            if(childAdded.get())
                this.expandedNodes++;
        }

        return false;
    }

}
