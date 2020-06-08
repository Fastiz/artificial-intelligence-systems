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
        this.weights = new Vector(x.get(0).getDimension());
        resetWeights();
    }

    public void resetWeights() {
        int factor = -1;
        this.weights.fill(factor / Math.sqrt(weights.getDimension()));
    }

    public void setLearnFactor(double learnFactor) {
        this.learnFactor = learnFactor;
    }

    public void step() {
        for (Vector vector : x) {
            Vector deltaWeight = getDelta(vector);
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
