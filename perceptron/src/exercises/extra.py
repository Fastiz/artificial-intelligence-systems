from __future__ import division

import random

from perceptron.perceptron_network import PerceptronNetwork
import numpy as np
import ex1
import training.exclusiveOr
import pandas as pd
from mnist import MNIST


def run():
    mndata = MNIST('../data/')

    data, output = mndata.load_training()

    output_real = []
    for i in range(len(output)):
        output_real.append((output[i] / 10) * 2 - 1)

    b = 1
    pnw = PerceptronNetwork(lambda p: np.tanh(b*p), lambda p: b * (1-((np.tanh(p))**2)), data, output_real,
                            [8, 8, 8], 0.2, 1)

    for i in range(10000):
        pnw.step()

    for data_in, data_out in zip(data, output_real):
        print(" ".join([str(data_in), str(data_out), "-->", str(pnw.classify(data_in))]))