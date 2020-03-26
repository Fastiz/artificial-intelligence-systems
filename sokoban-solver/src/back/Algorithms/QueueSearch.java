package back.Algorithms;

import back.ResultPrinter;
import back.game.Action;
import back.game.Game;

import java.util.Queue;

public class QueueSearch {
    private int expandedNodes;
    private Game gameSolved;
    private Queue<Game> queue;

    public void runAlgorithm(Queue<Game> queue) {
        expandedNodes = 0;
        gameSolved = null;
        this.queue = queue;

        long startTime = System.nanoTime();
        boolean result = queueSearch();
        long endTime = System.nanoTime();

        if(result)
            ResultPrinter.printResult(expandedNodes, queue.size(), gameSolved, endTime - startTime);
        else
            ResultPrinter.printNoSolutionFound(expandedNodes, queue.size(), endTime - startTime);
    }

    public boolean queueSearch() {
        while (!queue.isEmpty()) {
            Game game = queue.poll();

            if(game.gameFinished()){
                this.gameSolved = game;
                return true;
            }

            for (Action action : game.getAvailableActions()) {
                queue.add(game.applyActionAndClone(action));
            }

            this.expandedNodes++;
        }

        return false;
    }
}
