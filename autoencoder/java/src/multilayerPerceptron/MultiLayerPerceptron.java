package src.multilayerPerceptron;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

public class MultiLayerPerceptron {
    public static class Builder {
        private Function<Double, Double> activationFunction, activationFunctionDerivative;
        private List<Integer> innerLayersDimensions;
        private double alpha;
        private int inDim, outDim;
        private Random random;
        private Function<Integer, Double> temperatureFunction;
        boolean withBias;

        public Builder() {
            this.random = new Random();
            this.withBias = true;
        }

        public Builder setActivationFunction(Function<Double, Double> activationFunction) {
            this.activationFunction = activationFunction;
            return this;
        }

        public Builder setActivationFunctionDerivative(Function<Double, Double> activationFunctionDerivative) {
            this.activationFunctionDerivative = activationFunctionDerivative;
            return this;
        }

        public Builder setInnerLayersDimensions(List<Integer> innerLayersDimensions) {
            this.innerLayersDimensions = innerLayersDimensions;
            return this;
        }

        public Builder setAlpha(double alpha) {
            this.alpha = alpha;
            return this;
        }

        public Builder setInDim(int inDim) {
            this.inDim = inDim;
            return this;
        }

        public Builder setOutDim(int outDim) {
            this.outDim = outDim;
            return this;
        }

        public Builder setSeed(long seed){
            this.random = new Random(seed);
            return this;
        }

        public Builder setTemperatureFunction(Function<Integer, Double> temperatureFunction){
            this.temperatureFunction = temperatureFunction;
            return this;
        }

        public Builder withBias(boolean bias){
            this.withBias = bias;
            return this;
        }

        public MultiLayerPerceptron create(){

            if(activationFunction == null ||
                    activationFunctionDerivative == null ||
                    innerLayersDimensions == null ||
                    (alpha == 0 && temperatureFunction == null) ||
                    inDim == 0 ||
                    outDim == 0
            )
                throw new IllegalArgumentException("One of the fields was not set");

            if(temperatureFunction == null)
                temperatureFunction = p->this.alpha;

            return new MultiLayerPerceptron(
                    temperatureFunction,
                    activationFunction,
                    activationFunctionDerivative,
                    innerLayersDimensions,
                    alpha,
                    inDim,
                    outDim,
                    random,
                    withBias
            );
        }
    }

    static class VsAndHsWrapper{
        List<List<Double>> vs, hs;
        VsAndHsWrapper(List<List<Double>> vs, List<List<Double>> hs){
            this.vs = vs;
            this.hs = hs;
        }
    }

    private final Function<Double, Double> activationFunction, activationFunctionDerivative;
    private final List<Integer> innerLayersDimensions;
    private final double alpha;
    private final double bias;
    private final Function<Integer, Double> temperatureFunction;

    private final Random random;

    private int trainingStep;

    private final List<List<List<Double>>> weights;

    public MultiLayerPerceptron(Function<Integer, Double> temperatureFunction, Function<Double, Double> activationFunction,
                                Function<Double, Double> activationFunctionDerivative, List<Integer> innerLayersDimensions,
                                double alpha, int inDim, int outDim, Random random, boolean withBias) {
        this.temperatureFunction = temperatureFunction;
        this.activationFunction = activationFunction;
        this.activationFunctionDerivative = activationFunctionDerivative;
        this.innerLayersDimensions = innerLayersDimensions;
        this.alpha = alpha;
        this.bias = withBias ? -1.0: 0.0;
        this.random = random;
        this.weights = new ArrayList<>(2 + innerLayersDimensions.size());

        this.trainingStep = 0;

        generateWeights(inDim, innerLayersDimensions, outDim);
    }

    public void step(List<Double> inValue, List<Double> outValue){
        //Propagate the signal
        VsAndHsWrapper wrapper = propagate(inValue);
        List<List<Double>> vs = wrapper.vs, hs = wrapper.hs;

        //Backwards propagation
        List<List<Double>> deltas = new ArrayList<>();
        deltas.add(
            Utils.elementwiseOperation(
                    hs.get(hs.size() - 1).stream().mapToDouble(activationFunctionDerivative::apply).boxed().collect(Collectors.toList()),
                    Utils.elementwiseOperation(outValue, vs.get(vs.size() - 1), (a, b) -> a - b),
                    (a, b)->a*b
            )
        );

        for(int m=hs.size()-2; m>=0; m--){
            List<List<Double>> transposed = Utils.transpose(weights.get(m+1));

            List<Double> firstTerm = hs.get(m).stream()
                    .mapToDouble(activationFunctionDerivative::apply)
                    .boxed()
                    .collect(Collectors.toList());

            List<Double> secondTerm = transposed.stream()
                    .mapToDouble(doubles -> Utils.dotProduct(doubles, deltas.get(deltas.size() - 1)))
                    .boxed()
                    .collect(Collectors.toList());

            secondTerm.remove(secondTerm.size()-1);

            deltas.add(
                    Utils.elementwiseOperation(
                            firstTerm,
                            secondTerm,
                            (a, b)->a*b
                    )
            );
        }

        //Update weights
        for(int m=0; m<deltas.size(); m++){
            List<List<Double>> layerWeights = weights.get(m);
            for(int i=0; i<layerWeights.size(); i++){
                List<Double> i_weights = layerWeights.get(i);
                for(int j=0; j<i_weights.size()-1; j++){
                    double v = vs.get(m).get(j);

                    double newWeight = i_weights.get(j) +
                            temperatureFunction.apply(this.trainingStep) * deltas.get(deltas.size()-1-m).get(i) * v;
                    i_weights.set(j, newWeight);
                }

                double newWeight = i_weights.get(i_weights.size()-1) +
                        temperatureFunction.apply(this.trainingStep) * deltas.get(deltas.size()-1-m).get(i) * this.bias;
                i_weights.set(i_weights.size()-1, newWeight);
            }
        }

        trainingStep++;
    }

    public List<Double> classify(List<Double> pattern){
        List<List<Double>> vs = propagate(pattern).vs;
        return vs.get(vs.size()-1);
    }

    public List<Double> classify(List<Double> pattern, int starting, int end){
        List<List<Double>> vs = propagate(pattern, starting, end).vs;
        return vs.get(vs.size()-1);
    }

    public VsAndHsWrapper propagate(List<Double> pattern){
        return propagate(pattern, 0, 1 + innerLayersDimensions.size());
    }

    public VsAndHsWrapper propagate(List<Double> pattern, int starting, int end){
        List<List<Double>> vs = new ArrayList<>(2 + innerLayersDimensions.size());
        List<List<Double>> hs = new ArrayList<>(2 + innerLayersDimensions.size());

        vs.add(pattern);

        for(List<List<Double>> layerWeights : weights.subList(starting, end)){
            List<Double> v = new ArrayList<>();
            List<Double> h = new ArrayList<>();

            for(List<Double> weight : layerWeights){
                List<Double> withBias = new ArrayList<>(vs.get(vs.size()-1));
                withBias.add(this.bias);

                Double dot = Utils.dotProduct(weight, withBias);
                h.add(dot);
                v.add(activationFunction.apply(dot));
            }

            vs.add(v);
            hs.add(h);
        }

        return new VsAndHsWrapper(vs, hs);
    }

    public List<Integer> getInnerLayersDimensions() {
        return innerLayersDimensions;
    }

    // Private methods ---------

    private void generateWeights(int inDim, List<Integer> innerLayersDimensions, int outDim){
        List<Integer> allDimensions = new ArrayList<>(2 + innerLayersDimensions.size());
        allDimensions.add(inDim);
        allDimensions.addAll(innerLayersDimensions);
        allDimensions.add(outDim);

        int prevDim = allDimensions.get(0);
        for (int i=1; i<allDimensions.size(); i++){
            int currDim = allDimensions.get(i);

            List<List<Double>> layerWeights = new ArrayList<>(currDim);

            for(int j=0; j<currDim; j++){
                layerWeights.add(generateRandomWeights(prevDim+1));
            }

            weights.add(layerWeights);

            prevDim = currDim;
        }
    }

    private List<Double> generateRandomWeights(int dim){
        List<Double> weights = new ArrayList<>(dim);

        for(int i=0; i<dim; i++){
            double factor = Math.random() > 0.5 ? 1 : -1;
            weights.add(factor/Math.sqrt(dim));
        }

        return weights;
    }

    public List<List<List<Double>>> getWeights() {
        return weights;
    }
}
