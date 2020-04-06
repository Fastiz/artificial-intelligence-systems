package back.algorithms.util;

import back.AlgorithmSolution;
import back.game.GameImplementation;
import back.interfaces.Algorithm;
import back.interfaces.Game;
import back.interfaces.Heuristic;

import java.util.*;

public class SearchAlgorithm {
    private int expandedNodes;
    private Game gameSolved;
    private HashSet<Game> hashSet;
    private SearchCollection<Game> collection;
    private Heuristic heuristic = null;
    private String name;
    private int limit;

    public SearchAlgorithm(String name){
        this.name = name;
    }

    public AlgorithmSolution run(Game game, Comparator<Game> comparator) {
        this.collection = new SearchCollection<>(comparator);
        this.limit = -1;
        return runAux(game);
    }

    public AlgorithmSolution run(Game game, Comparator<Game> comparator, Heuristic heuristic) {
        this.heuristic = heuristic;
        game.setHeuristicValue(heuristic.evaluate(game));
        this.limit = -1;
        return run(game, comparator);
    }

    public AlgorithmSolution run(Game game, boolean FIFO) {
        this.collection = new SearchCollection<>(FIFO);
        this.limit = -1;
        return runAux(game);
    }

    public AlgorithmSolution run(Game game, int limit) {
        this.collection = new SearchCollection<>(true);
        this.limit = limit;
        return runAux(game);
    }

    private AlgorithmSolution runAux(Game game) {
        hashSet = new HashSet<>();
        expandedNodes = 0;
        gameSolved = null;

        this.collection.add(game);

        long startTime = System.currentTimeMillis();
        boolean result = search();
        long endTime = System.currentTimeMillis();

        AlgorithmSolution solution;
        if(result)
            solution = new AlgorithmSolution(this.name, this.expandedNodes, collection.size(), this.gameSolved, endTime - startTime);
        else
            solution = new AlgorithmSolution(this.name, false, this.expandedNodes, endTime - startTime);

        this.heuristic = null;
        return solution;
    }

    public boolean search() {
        while (!collection.isEmpty()) {
            Game game = collection.pop();

            if(limit != -1 && limit <= game.getDepth())
                return false;

            if(game.gameFinished()){
                this.gameSolved = game;
                return true;
            }

            hashSet.add(game);

            game.calculateChildren().stream().filter(child -> !hashSet.contains(child)).forEach(child -> {
                if(heuristic != null)
                    child.setHeuristicValue(heuristic.evaluate(child));
                collection.add(child);
            });

            this.expandedNodes++;
            System.out.println(expandedNodes);
        }

        return false;
    }

}
