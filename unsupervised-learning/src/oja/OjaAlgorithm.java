package oja;

import utils.Vector;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class OjaAlgorithm {

    private List<Vector> x;
    private Vector weights;
    private double learnFactor;

    public OjaAlgorithm(double learnFactor, List<Vector> x) {
        this.learnFactor = learnFactor;
        this.x = x;
        double [] weights = new double[x.get(0).getDimension()];
        int factor = Math.random() > 0.5 ? 1 : -1;
        Arrays.fill(weights, factor / Math.sqrt(weights.length));
        this.weights = new Vector(weights);
    }

    public void step() {
        for(int i = 0; i < x.size(); i++) {
            Vector deltaWeight = getDelta(x.get(i));
            weights.sumInstance(deltaWeight);
        }
    }

    private Vector getDelta(Vector x) {
        double y = Vector.dot(x, weights);
        Vector xy = Vector.scalarMultiplication(x, y);
        Vector y2w = Vector.scalarMultiplication(weights, y*y);
        return Vector.scalarMultiplication(Vector.subtract(xy, y2w),learnFactor);
    }

    public Vector getWeights() {
        return weights;
    }

    public double getPrincipalComponent(Vector x) {
        return Vector.dot(x,weights);
    }
}
