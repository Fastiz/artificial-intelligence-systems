from perceptron.perceptron_network import PerceptronNetwork
import numpy as np
import pandas as pd


def run():
    data = pd.read_csv('./perceptron/ex2_training.csv')
    output = data['y'].to_list()
    del data['y']
    data = data.values

    norm = max(output)
    output = np.array(output) / max(output)

    output = output.tolist()
    data = data.tolist()

    b = 1
    pnw = PerceptronNetwork(lambda p: np.tanh(b*p), lambda p: b * (1-((np.tanh(p))**2)), data, output,
                            [8, 8], 0.2, 1)

    for i in range(100000):
        pnw.step()

    for data_in, data_out in zip(data, output):
        print(" ".join([str(data_in), str(data_out), "-->", str(pnw.classify(data_in))]))
