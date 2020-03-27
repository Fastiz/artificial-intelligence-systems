package back.algorithms;

import back.AlgorithmSolution;
import back.interfaces.Game;
import back.interfaces.Heuristic;

import java.util.Queue;

public class QueueSearch {
    private int expandedNodes;
    private Game gameSolved;
    private Queue<Game> queue;
    private Heuristic heuristic = null;

    public AlgorithmSolution run(Queue<Game> queue) {
        expandedNodes = 0;
        gameSolved = null;
        this.queue = queue;

        long startTime = System.nanoTime();
        boolean result = queueSearch();
        long endTime = System.nanoTime();

        AlgorithmSolution solution;
        if(result)
            solution = new AlgorithmSolution(this.expandedNodes, queue.size(), this.gameSolved, endTime - startTime);
        else
            solution = new AlgorithmSolution(false, this.expandedNodes, endTime - startTime);

        this.heuristic = null;
        return solution;
    }

    public AlgorithmSolution run(Queue<Game> queue, Heuristic heuristic) {
        this.heuristic = heuristic;
        return run(queue);
    }

    public boolean queueSearch() {
        while (!queue.isEmpty()) {
            Game game = queue.poll();

            if(game.gameFinished()){
                this.gameSolved = game;
                return true;
            }

            for (Game gameChild : game.calculateChildrenWithStack()) {
                if(this.heuristic != null)
                    gameChild.setEstimatedCost(heuristic.evaluate(gameChild));

                queue.add(gameChild);
            }

            this.expandedNodes++;
        }

        return false;
    }
}
