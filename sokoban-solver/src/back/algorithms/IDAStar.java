package back.algorithms;

import back.AlgorithmSolution;
import back.algorithms.util.Utils;
import back.interfaces.Game;
import back.interfaces.Heuristic;
import back.interfaces.InformedAlgorithm;

import javax.rmi.CORBA.Util;
import java.util.*;

public class IDAStar implements InformedAlgorithm {

    private int expandedNodes;
    private Game gameSolved;
    private Heuristic heuristic;
    private int newLimit;
    private HashMap<Game, Integer> hashMap;
    private Stack<Game> stack;

    public IDAStar(Heuristic heuristic) {
        this.heuristic = heuristic;
        this.hashMap = new HashMap<>();
    }

    public IDAStar(){

    }

    public Heuristic getHeuristic() {
        return heuristic;
    }

    @Override
    public void setHeuristic(Heuristic heuristic) {
        this.heuristic = heuristic;
    }

    @Override
    public AlgorithmSolution run(Game game) {
        this.newLimit = -1;
        this.gameSolved = null;
        boolean result = false;
        this.stack = new Stack<>();

        long startTime = System.currentTimeMillis();

        int limit = heuristic.evaluate(game);
        boolean quit = false;

        while(!result) {
            newLimit = -1;
            expandedNodes = 0;
            stack.push(game);
            result = searchIDAStar(limit);
            limit = newLimit;
        }

        long endTime = System.currentTimeMillis();

        AlgorithmSolution solution;
        //TODO: numberOfBorderNodes
        if(result)
            solution = new AlgorithmSolution(this.getName() , this.expandedNodes, stack.size(), this.gameSolved, endTime - startTime);
        else
            solution = new AlgorithmSolution(this.getName() , false, this.expandedNodes, endTime - startTime);

        return solution;
    }

    private boolean searchIDAStar(int limit) {
        while (!stack.isEmpty()) {
            Game game = stack.pop();

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

            game.calculateChildren().stream().filter(child -> Utils.checkIfHashMapContainsElementAndReplace(hashMap, child)).forEach(child -> {
                child.setHeuristicValue(heuristic.evaluate(child));
                stack.add(child);
                hashMap.put(child, child.getDepth());
            });

            this.expandedNodes++;
        }

        return false;
    }

    private int getFunctionValue(Game game) {
        return game.getHeuristicValue() + game.getDepth();
    }

    @Override
    public String getName(){
        return "IDA*";
    }

}
