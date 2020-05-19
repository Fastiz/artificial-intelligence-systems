import random
import pandas as pd
import numpy as np
import matplotlib.pyplot as plt

from exercises.utils import run_multiple_times_and_collect_errors
from perceptron.perceptron_network import PerceptronNetwork


def run():
    data = pd.read_csv('./perceptron/ex2_training.csv')
    output = data['y'].to_list()
    del data['y']
    data = data.values

    norm = max(output)
    data = np.array(data)
    output = np.array(output) / norm

    data = data.tolist()
    output = output.tolist()

    run_error_graph(data, output)

    errors = run_multiple_times_and_collect_errors(10, run_training, 0.8, data, output)

    print(" ".join(["Errors:", str(errors)]))
    print(" ".join(["Mean:", str(np.mean(np.array(errors), axis=None))]))
    print(" ".join(["Standard deviation:", str(np.std(np.array(errors), axis=None))]))


def run_error_graph(training_in, training_out):
    errors = []

    b = 1
    perceptron = PerceptronNetwork(lambda p: np.tanh(b*p), lambda p: b * (1-((np.tanh(p))**2)), training_in,
                                   training_out, [8, 8], 0.00001, 1)

    for i in range(100000):
        if i % 1000 == 0:
            errors.append(perceptron.calculate_error(training_in, training_out))
        perceptron.step()

    for elem, out in zip(training_in, training_out):
        print(" ".join([str(elem), str(out), "RESULT:", str(perceptron.classify(elem))]))

    plt.figure()
    plt.plot(errors)
    plt.show()

    print(errors[-1])


def run_training(training, test):
    training_in, training_out = training

    b = 1
    perceptron = PerceptronNetwork(lambda p: np.tanh(b*p), lambda p: b * (1-((np.tanh(p))**2)), training_in,
                                   training_out, [], 0.2, 1)

    for i in range(100000):
        perceptron.step()

    test_in, test_out = test
    return perceptron.calculate_error(test_in, np.array([test_out]))
