package back.algorithms;

import back.AlgorithmSolution;
import back.algorithms.util.SearchAlgorithm;
import back.interfaces.Game;
import back.interfaces.Algorithm;

public class DFS implements Algorithm {

    public AlgorithmSolution run(Game game) {
        SearchAlgorithm searchAlgorithm = new SearchAlgorithm(this.getName());
        return searchAlgorithm.run(game, false);
    }

    @Override
    public String getName() {
        return "DFS";
    }
}