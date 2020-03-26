package back.algorithms;

import back.ResultPrinter;
import back.game.Action;
import back.game.Game;

import java.util.List;
import java.util.Stack;

public class IDDFS {

    private int expandedNodes;
    private Game gameSolved;

    public void runAlgorithm(Game game, int maxDepth) {
        this.gameSolved = null;
        expandedNodes = 0;
        Stack<Game> gamesStack = new Stack<>();
        gamesStack.push(game);

        boolean result = false;

        long startTime = System.nanoTime();
        for(int i = 0; i < maxDepth & !result; i++) {
            result = recursiveIDDFS(gamesStack, maxDepth);
        }
        long endTime = System.nanoTime();

        if(result)
            ResultPrinter.printResult(expandedNodes, gamesStack.size(), gameSolved, endTime - startTime);
        else
            ResultPrinter.printNoSolutionFound(expandedNodes, gamesStack.size(), endTime - startTime);

    }

    private boolean recursiveIDDFS(Stack<Game> stack, int depthLeft) {

        //Creo que isEmpty no deberia pasar
        if(stack.isEmpty() || depthLeft == 0)
            return false;

        Game game = stack.pop();
        if(game.gameFinished()){
            this.gameSolved = game;
            return true;
        }

        List<Action> availableActions = game.getAvailableActions();
        for(int i = 0; i < availableActions.size(); i++) {
            Action action = availableActions.get(i);
            stack.push(game.applyActionAndClone(action));
            if(recursiveIDDFS(stack, depthLeft - 1)) {
                if(i + 1 == availableActions.size())
                    this.expandedNodes++;
                return true;
            }
        }

        this.expandedNodes++;
        return false;
    }
}