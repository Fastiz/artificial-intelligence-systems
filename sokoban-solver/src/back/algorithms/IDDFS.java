package back.algorithms;

import back.AlgorithmSolution;
import back.interfaces.Algorithm;
import back.interfaces.Game;

import java.util.*;

public class IDDFS implements Algorithm {

    private HashSet<Game> hashSet;

    private boolean remainingToSearch;

    private int visitedNodes;
    private int expandedNodes;
    private Game gameSolved;
    private int maxDepth;

    public IDDFS() {
        hashSet = new HashSet<>();

        this.remainingToSearch = true;

        this.maxDepth = Integer.MAX_VALUE;
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


        long startTime = System.nanoTime();

        /* En gameToAnalize pongo los que voy a analizar, en nextIterationGames pongo los que se pasan de depth cuando hago dfs,
        entonces cuando me retorna, en la proxima iteracion, los que van a analizar son los que se pasaron.
         */
        for (int i = 0; i < maxDepth && this.remainingToSearch && !result; i++) {
            this.expandedNodes = 0;
            this.remainingToSearch = false;
            result = recursiveIDDFS(game, i);
            hashSet.clear();
        }

        long endTime = System.nanoTime();

        AlgorithmSolution solution;
        if (result)
            solution = new AlgorithmSolution(this.expandedNodes, 1, this.gameSolved, endTime - startTime);
        else
            solution = new AlgorithmSolution(false, this.expandedNodes, endTime - startTime);

        return solution;
    }

    private boolean recursiveIDDFS(Game game, int depthLeft) {
        this.visitedNodes++;
        this.hashSet.add(game);

        List<Game> children = game.calculateChildrenWithStack();

        if (depthLeft < 0) {
            this.remainingToSearch = this.remainingToSearch || !children.isEmpty();
            return false;
        }

        if (game.gameFinished()) {
            this.gameSolved = game;
            return true;
        }

        for (int i = 0; i < children.size(); i++) {
            Game gameChild = children.get(i);

            if(!this.hashSet.contains(gameChild)){
                if (recursiveIDDFS(gameChild, depthLeft - 1)) {
                    if (i + 1 == children.size())
                        this.expandedNodes++;
                    return true;
                }
            }
        }

        this.expandedNodes++;
        return false;
    }
}