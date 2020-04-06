package back.algorithms;

import back.AlgorithmSolution;
import back.algorithms.util.Utils;
import back.interfaces.Game;
import back.interfaces.Heuristic;
import back.interfaces.InformedAlgorithm;

import java.util.*;

public class IDAStar implements InformedAlgorithm {

    private int expandedNodes;
    private Game gameSolved;
    private Heuristic heuristic;
    private int newLimit;
    private HashMap<Game, Integer> hashMap;
    private Comparator<Game> gameComparator = (g1, g2) -> {
        int f1 = g1.getHeuristicValue() + g1.getDepth();
        int f2 = g2.getHeuristicValue() + g2.getDepth();
        return f1 - f2;
    };

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
            solution = new AlgorithmSolution(this.getName() , this.expandedNodes, 1, this.gameSolved, endTime - startTime);
        else
            solution = new AlgorithmSolution(this.getName() , false, this.expandedNodes, endTime - startTime);

        return solution;
    }

    private boolean recursiveAStarSearch(Game game, int limit) {
        this.hashMap.put(game, game.getDepth());

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


        List<Game> children = game.calculateChildren();
        for(int i = 0; i < children.size(); i++) {
            Game gameChild = children.get(i);
            if(Utils.checkIfHashMapContainsElementAndReplace(this.hashMap, gameChild)) {
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
        return heuristic.evaluate(game) + game.getDepth();
    }

    @Override
    public String getName(){
        return "IDA*";
    }

}
