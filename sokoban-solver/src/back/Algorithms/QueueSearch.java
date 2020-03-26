package back.Algorithms;

import back.ResultPrinter;
import game.Action;
import game.Game;

import java.util.Queue;

public class QueueSearch {
    private int expandedNodes;
    private Game gameSolved;

    public void runAlgorithm(Queue<Game> queue) {
        expandedNodes = 0;
        gameSolved = null;

        long startTime = System.nanoTime();
        boolean result = recursiveQueueSearch(queue);
        long endTime = System.nanoTime();

        if(result)
            ResultPrinter.printResult(expandedNodes, queue.size(), gameSolved, endTime - startTime);
        else
            ResultPrinter.printNoSolutionFound(expandedNodes, queue.size(), endTime - startTime);
    }

    public boolean recursiveQueueSearch(Queue<Game> queue) {
        if(queue.isEmpty())
            return false;

        Game game = queue.poll();
        if(game.gameFinished()){
            this.gameSolved = game;
            return true;
        }

        for (Action action : game.getAvailableActions()) {
            queue.add(game.applyActionAndClone(action));
        }

        this.expandedNodes++;
        return recursiveQueueSearch(queue);
    }
}
