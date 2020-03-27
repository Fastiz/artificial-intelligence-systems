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
    private Comparator<Game> gameComparator = (g1, g2) -> {
        int f1 = g1.getEstimatedCost() + g1.getAccumulatedCost();
        int f2 = g2.getEstimatedCost() + g2.getAccumulatedCost();
        return f1 - f2;
    };

    IDAStar(Heuristic heuristic) {
        this.heuristic = heuristic;
    }

    public Heuristic getHeuristic() {
        return heuristic;
    }

    public void setHeuristic(Heuristic heuristic) {
        this.heuristic = heuristic;
    }

    public AlgorithmSolution run(Game game) {
        this.gameSolved = null;
        this.expandedNodes = 0;
        boolean result = false;

        Queue<Game> gamesToAnalize = new LinkedList<>();
        gamesToAnalize.add(game);
        PriorityQueue<Game> nextIterationGames = new PriorityQueue<>(gameComparator);

        long startTime = System.nanoTime();

        int limit = heuristic.evaluate(game);

        while(!result & !gamesToAnalize.isEmpty()) {
            while (!gamesToAnalize.isEmpty()) {
                Game gameToAnalize = gamesToAnalize.poll();
                result = recursiveAStarSearch(gameToAnalize, nextIterationGames, limit);
            }
            if(!nextIterationGames.isEmpty()) {
                limit = getFunctionValue(nextIterationGames.peek());
                gamesToAnalize.addAll(nextIterationGames);
                nextIterationGames.clear();
            }
        }

        long endTime = System.nanoTime();

        AlgorithmSolution solution;
        if(result)
            solution = new AlgorithmSolution(this.expandedNodes, gamesToAnalize.size() + nextIterationGames.size(), this.gameSolved, endTime - startTime);
        else
            solution = new AlgorithmSolution(false, this.expandedNodes, endTime - startTime);

        return solution;
    }

    private boolean recursiveAStarSearch(Game game, PriorityQueue<Game> nextIterationGames, int limit) {

        if (limit < getFunctionValue(game)) {
            nextIterationGames.add(game);
            return false;
        }

        if(game.gameFinished()){
            this.gameSolved = game;
            return true;
        }

        List<Game> children = game.calculateChildrenWithStack();
        for(int i = 0; i < children.size(); i++) {
            Game gameChild = children.get(i);
            if(recursiveAStarSearch(gameChild, nextIterationGames, limit)) {
                if(i + 1 == children.size())
                    this.expandedNodes++;
                return true;
            }
        }

        this.expandedNodes++;
        return false;
    }

    private int getFunctionValue(Game game) {
        return game.getEstimatedCost() + game.getAccumulatedCost();
    }

}
