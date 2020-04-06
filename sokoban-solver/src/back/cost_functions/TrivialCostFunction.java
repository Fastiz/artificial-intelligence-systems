package back.cost_functions;

import back.interfaces.CostFunction;
import back.interfaces.Game;

public class TrivialCostFunction implements CostFunction {

    @Override
    public int evaluate(Game current, Game child) {
        return 1;
    }
}
