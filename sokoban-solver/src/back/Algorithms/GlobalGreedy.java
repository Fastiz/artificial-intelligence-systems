package back.Algorithms;

import back.Interfaces.Heuristic;
import back.game.Game;

import java.util.Comparator;
import java.util.PriorityQueue;

public class GlobalGreedy {

    public void runAlgorithm(Game game, Heuristic heuristic) {
        Comparator<Game> gameComparator = Comparator.comparingInt(Game::getEstimatedCost);
        PriorityQueue<Game> gamesPriorityQueue = new PriorityQueue<>(gameComparator);

        game.setEstimatedCost(heuristic.evaluate(game));
        gamesPriorityQueue.add(game);

        QueueSearch queueSearch = new QueueSearch();
        queueSearch.runAlgorithm(gamesPriorityQueue, heuristic);
    }
}
