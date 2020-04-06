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

    private Stack<Game> stack;
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
        this.stack = new Stack<>();
        this.gameSolved = null;
        expandedNodes = 0;
        boolean result = false;

        SearchAlgorithm searchAlgorithm = new SearchAlgorithm(this.getName());
        long startTime = System.currentTimeMillis();
        for (int i = 0; i < maxDepth && remainingToSearch && !result; i++) {
            if(i > 78)
                System.out.println();
            stack.push(game);
            remainingToSearch = false;
            this.expandedNodes = 0;
            result = searchIDDFS(i);
        }

        long endTime = System.currentTimeMillis();

        AlgorithmSolution solution;
        if(result)
            solution = new AlgorithmSolution(this.getName() , this.expandedNodes, stack.size(), this.gameSolved, endTime - startTime);
        else
            solution = new AlgorithmSolution(this.getName() , false, this.expandedNodes, endTime - startTime);
       return solution;
    }

    private boolean searchIDDFS(int limit) {
        while (!stack.isEmpty()) {
            Game game = stack.pop();

            if(game.getDepth() < limit) {

                if (game.gameFinished()) {
                    this.gameSolved = game;
                    return true;
                }

                game.calculateChildren().stream().filter(child -> Utils.checkIfHashMapContainsElementAndReplace(hashMap, child)).forEach(child -> {
                    stack.add(child);
                    hashMap.put(child, child.getDepth());
                });

                this.expandedNodes++;
            } else {
                remainingToSearch = true;
            }
        }

        return false;
    }

    @Override
    public String getName(){
        return "IDDFS";
    }

}