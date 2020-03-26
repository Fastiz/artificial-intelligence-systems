package back.Heuristics;

import back.Interfaces.Heuristic;
import back.game.Game;

public class TrivialHeuristic implements Heuristic {

    public int evaluate(Game game) {
        return 1;
    }

}
