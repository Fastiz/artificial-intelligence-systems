package back.algorithms;

import back.AlgorithmSolution;
import back.algorithms.util.SearchAlgorithm;
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
        AlgorithmSolution solution = null;

        SearchAlgorithm searchAlgorithm = new SearchAlgorithm(this.getName());
        long startTime = System.currentTimeMillis();
        for (int i = 0; i < maxDepth && !result; i++) {
            this.expandedNodes = 0;
            solution = searchAlgorithm.run(game, i);
            result = solution.isGoalFound();
        }

        long endTime = System.currentTimeMillis();

        if(result)
            solution.setProcessingTime(endTime - startTime);
        return solution;
    }

    @Override
    public String getName(){
        return "IDDFS";
    }

}