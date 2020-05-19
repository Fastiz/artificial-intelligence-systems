import random
import pandas as pd
import numpy as np

from exercises.utils import run_multiple_times_and_collect_errors
from perceptron.perceptron_network import PerceptronNetwork


def run():
    data = pd.read_csv('./perceptron/ex2_training.csv')
    output = data['y'].to_list()
    del data['y']
    data = data.values

    norm = max(output)
    output = np.array(output) / max(output)

    data = data.tolist()
    output = output.tolist()

    errors = run_multiple_times_and_collect_errors(10, run_training, 0.8, data, output)

    print(" ".join(["Errors:", str(errors)]))
    print(" ".join(["Mean:", str(np.mean(np.array(errors), axis=None))]))
    print(" ".join(["Standard deviation:", str(np.std(np.array(errors), axis=None))]))


def run_training(training, test):
    training_in, training_out = training

    b = 1
    perceptron = PerceptronNetwork(lambda p: np.tanh(b*p), lambda p: b * (1-((np.tanh(p))**2)), training_in,
                                   training_out, [], 0.2, 1)

    for i in range(100000):
        perceptron.step()

    test_in, test_out = test
    return perceptron.calculate_error(test_in, np.array([test_out]))
