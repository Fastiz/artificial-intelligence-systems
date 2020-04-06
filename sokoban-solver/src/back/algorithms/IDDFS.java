package back.algorithms;

import back.AlgorithmSolution;
import back.algorithms.util.Utils;
import back.interfaces.Algorithm;
import back.interfaces.Game;

import java.util.*;

public class IDDFS implements Algorithm {

    private HashMap<Game, Integer> hashMap;

    private boolean remainingToSearch;

    private int visitedNodes;
    private int expandedNodes;
    private Game gameSolved;
    private int maxDepth;

    public IDDFS() {
        hashMap = new HashMap<>();

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


        long startTime = System.currentTimeMillis();

        for (int i = 0; i < maxDepth && this.remainingToSearch && !result; i++) {
            this.expandedNodes = 0;
            this.remainingToSearch = false;
            result = recursiveIDDFS(game, i);
        }

        long endTime = System.currentTimeMillis();

        AlgorithmSolution solution;
        if (result)
            solution = new AlgorithmSolution(this.getName(), this.expandedNodes, 1, this.gameSolved, endTime - startTime);
        else
            solution = new AlgorithmSolution(this.getName(), false, this.expandedNodes, endTime - startTime);

        return solution;
    }

    private boolean recursiveIDDFS(Game game, int depthLeft) {
        this.visitedNodes++;
        this.hashMap.put(game, game.getDepth());

        List<Game> children = game.calculateChildren();

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

            if(Utils.checkIfHashMapContainsElementAndReplace(this.hashMap, gameChild)){
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

    @Override
    public String getName(){
        return "IDDFS";
    }

}