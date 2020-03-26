package back.Algorithms;

import back.ResultPrinter;
import back.game.Action;
import back.game.Game;

import java.util.List;

public class DFS {

    private int expandedNodes = 0;
    private int visitedNodes = 0;

    public void runAlgorithm(Game game) {
        long startTime = System.nanoTime();
        boolean result = recursiveDFS(game);
        long endTime = System.nanoTime();

        if(result)
            ResultPrinter.printResult(expandedNodes, visitedNodes - expandedNodes, game, endTime - startTime);
        else
            ResultPrinter.printNoSolutionFound(expandedNodes, visitedNodes - expandedNodes, endTime - startTime);

    }

    private boolean recursiveDFS(Game game) {
        visitedNodes++;
        if(game.gameFinished()){
            return true;
        }

        List<Action> availableActions = game.getAvailableActions();
        for(int i = 0; i < availableActions.size(); i++) {
            Action action = availableActions.get(i);
            game.applyAction(action);
            if(recursiveDFS(game)) {
                if(i + 1 == availableActions.size())
                    this.expandedNodes++;
                return true;
            }
        }

        this.expandedNodes++;
        game.revertAction();
        return false;
    }
}
