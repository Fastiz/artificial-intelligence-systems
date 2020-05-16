from perceptron.perceptron import Perceptron

import pandas as pd
import numpy as np


def run():
    data = pd.read_csv('./perceptron/ex2_training.csv')
    output = data['y'].to_list()
    del data['y']
    data = data.values

    norm = max(output)
    output = np.array(output) / max(output)

    run_training(data, output, norm)


def run_training(data, output, norm):
    b = 1
    perceptron = Perceptron(lambda p: np.tanh(b*p), lambda p: b * (1-((np.tanh(p))**2)), data, output, 0.8)

    error = perceptron.get_error()
    min_error = error

    loop = True
    while (loop and error < 20) or (not loop and perceptron.get_error() > 0.485):
        perceptron.step()
        error = perceptron.get_error()
        if error < min_error:
            min_error = error
            print(error)

    print(" ".join(["Finished with error:", str(perceptron.get_error())]))
    for elem, out in zip(data, output):
        print(" ".join([str(elem), str(out * norm), "RESULT:", str(perceptron.classify(elem) * norm)]))
