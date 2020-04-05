package back.algorithms;

import back.AlgorithmSolution;
import back.interfaces.Algorithm;
import back.interfaces.Game;

import java.util.*;

public class IDDFS implements Algorithm {

    private int expandedNodes;
    private Game gameSolved;
    private int maxDepth;

    public IDDFS(int maxDepth) {
        if (maxDepth < 0)
            maxDepth = 1;
        this.maxDepth = maxDepth;
    }

    public int getMaxDepth() {
        return maxDepth;
    }

    public void setMaxDepth(int maxDepth) {
        if (maxDepth < 0)
            maxDepth = 1;
        this.maxDepth = maxDepth;
    }

    public AlgorithmSolution run(Game game) {
        this.gameSolved = null;
        expandedNodes = 0;
        boolean result = false;

        Queue<Game> gamesToAnalize = new LinkedList<>();
        gamesToAnalize.add(game);
        Queue<Game> nextIterationGames = new LinkedList<>();

        long startTime = System.currentTimeMillis();

        /* En gameToAnalize pongo los que voy a analizar, en nextIterationGames pongo los que se pasan de depth cuando hago dfs,
        entonces cuando me retorna, en la proxima iteracion, los que van a analizar son los que se pasaron.
         */
        for (int i = 0; i < maxDepth & !result; i++) {
            while (!gamesToAnalize.isEmpty()) {
                Game gameToAnalize = gamesToAnalize.poll();
                result = recursiveIDDFS(gameToAnalize, nextIterationGames, i);
            }
            gamesToAnalize.addAll(nextIterationGames);
            nextIterationGames.clear();
        }

        long endTime = System.currentTimeMillis();

        AlgorithmSolution solution;
        if (result)
            solution = new AlgorithmSolution(this.expandedNodes, nextIterationGames.size(), this.gameSolved, endTime - startTime);
        else
            solution = new AlgorithmSolution(false, this.expandedNodes, endTime - startTime);

        return solution;
    }

    private boolean recursiveIDDFS(Game game, Queue<Game> nextIterationGames, int depthLeft) {

        if (depthLeft < 0) {
            nextIterationGames.add(game);
            return false;
        }

        if (game.gameFinished()) {
            this.gameSolved = game;
            return true;
        }

        List<Game> children = game.calculateChildrenWithStack();
        for (int i = 0; i < children.size(); i++) {
            Game gameChild = children.get(i);
            if (recursiveIDDFS(gameChild, nextIterationGames, depthLeft - 1)) {
                if (i + 1 == children.size())
                    this.expandedNodes++;
                return true;
            }
        }

        this.expandedNodes++;
        return false;
    }
}