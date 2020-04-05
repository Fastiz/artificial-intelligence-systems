package back.algorithms;

import back.AlgorithmSolution;
import back.interfaces.Algorithm;
import back.interfaces.Game;
import back.interfaces.Heuristic;

import java.util.*;

public class IDAStar implements Algorithm {

    private int expandedNodes;
    private Game gameSolved;
    private Heuristic heuristic;
    private HashSet<Game> hashSet;
    private int newLimit;
    private Comparator<Game> gameComparator = (g1, g2) -> {
        int f1 = g1.getEstimatedCost() + g1.getAccumulatedCost();
        int f2 = g2.getEstimatedCost() + g2.getAccumulatedCost();
        return f1 - f2;
    };

    public IDAStar(Heuristic heuristic) {
        this.heuristic = heuristic;
    }

    public Heuristic getHeuristic() {
        return heuristic;
    }

    public void setHeuristic(Heuristic heuristic) {
        this.heuristic = heuristic;
    }

    public AlgorithmSolution run(Game game) {
        this.newLimit = -1;
        this.hashSet = new HashSet<>();
        this.gameSolved = null;
        this.expandedNodes = 0;
        boolean result = false;

        long startTime = System.currentTimeMillis();

        int limit = heuristic.evaluate(game);
        boolean quit = false;

        while(!result & !quit) {
            newLimit = -1;
            result = recursiveAStarSearch(game, limit);

            if(newLimit < limit) {
                quit = true;
            }

            limit = newLimit;
        }

        long endTime = System.currentTimeMillis();

        AlgorithmSolution solution;
        //TODO: numberOfBorderNodes
        if(result)
            solution = new AlgorithmSolution(this.expandedNodes, 1, this.gameSolved, endTime - startTime);
        else
            solution = new AlgorithmSolution(false, this.expandedNodes, endTime - startTime);

        return solution;
    }

    private boolean recursiveAStarSearch(Game game, int limit) {

        int f = getFunctionValue(game);
        if (limit < f ) {
            if(f < newLimit || newLimit == -1)
                newLimit = f;
            return false;
        }

        if(game.gameFinished()){
            this.gameSolved = game;
            return true;
        }

        hashSet.add(game);

        List<Game> children = game.calculateChildrenWithStack();
        for(int i = 0; i < children.size(); i++) {
            Game gameChild = children.get(i);
            if(!hashSet.contains(gameChild)) {
                if (recursiveAStarSearch(gameChild, limit)) {
                    if (i + 1 == children.size())
                        this.expandedNodes++;
                    return true;
                }
            }
        }

        this.expandedNodes++;
        return false;
    }

    private int getFunctionValue(Game game) {
        return heuristic.evaluate(game) + game.getAccumulatedCost();
    }

}
