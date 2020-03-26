package back.algorithms;

import back.ResultPrinter;
import back.game.Action;
import back.game.Game;
import back.interfaces.Heuristic;

import java.util.Queue;

public class QueueSearch {
    private int expandedNodes;
    private Game gameSolved;
    private Queue<Game> queue;
    private Heuristic heuristic = null;

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

        this.heuristic = null;
    }

    public void runAlgorithm(Queue<Game> queue, Heuristic heuristic) {
        this.heuristic = heuristic;
        runAlgorithm(queue);
    }

    public boolean queueSearch() {
        while (!queue.isEmpty()) {
            Game game = queue.poll();

            if(game.gameFinished()){
                this.gameSolved = game;
                return true;
            }

            for (Action action : game.getAvailableActions()) {
                if(this.heuristic == null)
                    queue.add(game.applyActionAndClone(action));
                else
                    queue.add(game.applyActionAndClone(action, heuristic));
            }

            this.expandedNodes++;
        }

        return false;
    }
}
