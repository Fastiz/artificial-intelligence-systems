package back.cost_functions;

import back.interfaces.CostFunction;
import back.interfaces.Game;

public class TrivialCostFunction implements CostFunction {

    @Override
    public float evaluate(Game current, Game child) {
        return 0.1f;
    }
}
