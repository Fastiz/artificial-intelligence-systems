import pandas as pd
import numpy as np

from perceptron.perceptron_network import PerceptronNetwork


def run():
    data = pd.read_csv('./perceptron/ex2_training.csv')
    output = data['y'].to_list()
    del data['y']
    data = data.values

    norm = max(output)
    output = np.array(output) / max(output)

    run_training(data.tolist(), output.tolist(), norm)


def run_training(data, output, norm):
    b = 1
    perceptron = PerceptronNetwork(lambda p: np.tanh(b*p), lambda p: b * (1-((np.tanh(p))**2)), data, output, [], 0.2, 1)

    for i in range(1000000):
        perceptron.step()

    for elem, out in zip(data, output):
        print(" ".join([str(elem), str(out), "-->", str(perceptron.classify(elem))]))
