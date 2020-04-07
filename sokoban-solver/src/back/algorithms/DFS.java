package back.algorithms;

import back.AlgorithmSolution;
import back.algorithms.util.SearchAlgorithm;
import back.algorithms.util.SearchCollection;
import back.interfaces.Game;
import back.interfaces.Algorithm;
import javafx.util.Pair;

import java.util.*;


public class DFS implements Algorithm {

    private Stack<Game> stack;
    private HashSet<Game> hashSet;
    private Game gameSolved;
    private int expandedNodes;

    public AlgorithmSolution run(Game game) {
        expandedNodes = 0;
        gameSolved = null;
        stack = new Stack<>();
        hashSet = new HashSet<>();

        stack.push(game);

        System.out.println(searchDFS());
        return null;
    }

    private boolean searchDFS() {
        while (!stack.isEmpty()) {
            Game game = stack.peek();

            if (!game.isVisited()) {
                if(!hashSet.contains(game)) {
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