package back.Algorithms;

import back.Interfaces.Heuristic;
import back.ResultPrinter;
import back.game.Action;
import back.game.Game;

import java.util.*;

public class IDAStar {

    private int expandedNodes;
    private Game gameSolved;
    private Stack<Game> stack;
    private PriorityQueue<Game> exceededQueue;
    private Heuristic heuristic;

    public void runAlgorithm(Game game, Heuristic heuristic) {
        this.heuristic = heuristic;
        this.expandedNodes = 0;
        this.gameSolved = null;
        this.stack = new Stack<>();

        Comparator<Game> gameComparator = (g1, g2) -> {
            int f1 = g1.getEstimatedCost() + g1.getAcumulatedCost();
            int f2 = g2.getEstimatedCost() + g2.getAcumulatedCost();
            return f1 - f2;
        };
        this.exceededQueue = new PriorityQueue<>(gameComparator);
        game.setEstimatedCost(heuristic.evaluate(game));
        exceededQueue.add(game);

        long startTime = System.nanoTime();
        boolean result = queueAStarSearch();
        long endTime = System.nanoTime();

        if (result)
            ResultPrinter.printResult(expandedNodes, stack.size() + exceededQueue.size(), gameSolved, endTime - startTime);
        else
            ResultPrinter.printNoSolutionFound(expandedNodes, stack.size() + exceededQueue.size(), endTime - startTime);
    }

    private boolean queueAStarSearch() {
        boolean result = false;
        while(!exceededQueue.isEmpty() & !result) {
            Game smallestFunctionValueGame = exceededQueue.peek();
            //ACA EL STACK SE SUPONE QUE ESTA VACIO!
            stack.addAll(exceededQueue);
            exceededQueue.clear();
            result = recursiveAStarSearch(getFunctionValue(smallestFunctionValueGame));
        }
        return result;
    }

    private boolean recursiveAStarSearch(int limit) {

        //Este caso no deberia pasar creo
        if(stack.isEmpty())
            return false;

        Game game = stack.pop();
        if(getFunctionValue(game) >= limit) {
            this.exceededQueue.add(game);
            return false;
        }

        if(game.gameFinished()){
            this.gameSolved = game;
            return true;
        }

        List<Action> availableActions = game.getAvailableActions();
        for(int i = 0; i < availableActions.size(); i++) {
            Action action = availableActions.get(i);
            stack.push(game.applyActionAndClone(action, this.heuristic));
            if(recursiveAStarSearch(limit)) {
                if(i + 1 == availableActions.size())
                    this.expandedNodes++;
                return true;
            }
        }

        this.expandedNodes++;
        return false;
    }

    private int getFunctionValue(Game game) {
        return game.getEstimatedCost() + game.getAcumulatedCost();
    }

}
