package back.heuristics;

import back.game.Game;
import back.interfaces.Heuristic;

public class TrivialHeuristic implements Heuristic {

    public int evaluate(Game game) {
        return 1;
    }

}
