package src;

import java.util.List;

public class LetterData {
    private List<Double> input;
    private List<Double> output;

    public LetterData(List<Double> input, List<Double> output) {
        this.input = input;
        this.output = output;
    }

    public List<Double> getInput() {
        return input;
    }

    public void setInput(List<Double> input) {
        this.input = input;
    }

    public List<Double> getOutput() {
        return output;
    }

    public void setOutput(List<Double> output) {
        this.output = output;
    }
}
