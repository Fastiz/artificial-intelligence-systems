package back.algorithms;

import back.AlgorithmSolution;
import back.algorithms.util.SearchAlgorithm;
import back.interfaces.Game;
import back.interfaces.Algorithm;

import java.util.HashSet;
import java.util.List;
import java.util.Stack;

public class DFS implements Algorithm {

    public AlgorithmSolution run(Game game) {
        SearchAlgorithm searchAlgorithm = new SearchAlgorithm(this.getName());
        return searchAlgorithm.run(game, false);
    }

    private Stack<Game> stack;
    private HashSet<Game> hashSet;
    private Game gameSolved;
    private int expandedNodes;

    private boolean searchDFS() {
        while (!stack.isEmpty()) {
            Game game = stack.peek();

            if (!game.isVisited()) {
                if (!hashSet.contains(game)) {
                    if (game.gameFinished()) {
                        this.gameSolved = game;
                        return true;
                    }

                    hashSet.add(game);
                    game.setVisited(true);

                    List<Game> children = game.calculateChildren();
                    if (children.isEmpty()) {
                        hashSet.remove(stack.pop());
                    } else {
                        for (Game child : children) {
                            stack.push(child);
                        }
                        this.expandedNodes++;
                    }
                } else {
                    stack.pop();
                }
            } else {
                hashSet.remove(stack.pop());
            }
        }

        return false;
    }

    @Override
    public String getName() {
        return "DFS";
    }
}