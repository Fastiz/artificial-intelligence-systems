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

        public Builder() {
            this.random = new Random();
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

        public MultiLayerPerceptron create(){
            return new MultiLayerPerceptron(
                    activationFunction,
                    activationFunctionDerivative,
                    innerLayersDimensions,
                    alpha,
                    inDim,
                    outDim,
                    random
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

    private final Random random;

    private List<List<List<Double>>> weights;

    public MultiLayerPerceptron(Function<Double, Double> activationFunction, Function<Double, Double> activationFunctionDerivative, List<Integer> innerLayersDimensions, double alpha, int inDim, int outDim, Random random) {
        this.activationFunction = activationFunction;
        this.activationFunctionDerivative = activationFunctionDerivative;
        this.innerLayersDimensions = innerLayersDimensions;
        this.alpha = alpha;
        this.bias = -1.0;
        this.random = random;
        this.weights = new ArrayList<>(2 + innerLayersDimensions.size());

        generateWeights(inDim + 1, innerLayersDimensions, outDim);
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
                for(int j=0; j<i_weights.size(); j++){
                    double newWeight = i_weights.get(j) +
                            alpha * deltas.get(deltas.size()-1-m).get(i) *
                                    vs.get(m).get(j);
                    i_weights.set(j, newWeight);
                }
            }
        }

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

        List<Double> patternWithBias = new ArrayList<>(pattern);

        if(starting == 0)
            patternWithBias.add(this.bias);

        vs.add(patternWithBias);

        for(List<List<Double>> layerWeights : weights.subList(starting, end)){
            List<Double> v = new ArrayList<>();
            List<Double> h = new ArrayList<>();

            for(List<Double> weight : layerWeights){
                Double dot = Utils.dotProduct(weight, vs.get(vs.size()-1));
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
                layerWeights.add(generateRandomWeights(prevDim/*+1*/));
            }

            weights.add(layerWeights);

            prevDim = currDim;
        }
    }

    private List<Double> generateRandomWeights(int dim){
        List<Double> weights = new ArrayList<>(dim);

        for(int i=0; i<dim; i++){
            weights.add(random.nextDouble()*2-1);
        }

        return weights;
    }

}
