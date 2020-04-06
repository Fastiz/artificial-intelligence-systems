package back.algorithms;

import back.AlgorithmSolution;
import back.algorithms.util.SearchAlgorithm;
import back.algorithms.util.SearchCollection;
import back.interfaces.Algorithm;
import back.interfaces.Game;

import java.util.LinkedList;
import java.util.Queue;

public class BFS implements Algorithm {

    public AlgorithmSolution run(Game game) {
        SearchAlgorithm searchAlgorithm = new SearchAlgorithm(this.getName());
        return searchAlgorithm.run(game, true);
    }

    @Override
    public String getName(){
        return "BFS";
    }

}
