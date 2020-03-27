package back.heuristics;

import back.interfaces.Game;
import back.interfaces.Heuristic;

public class TrivialHeuristic implements Heuristic {

    public int evaluate(Game game) {
        return 1;
    }

}
