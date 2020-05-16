from perceptron.perceptron import Perceptron
from perceptron.no_linear_perceptron import NoLinearPerceptron
import training.andLogic
import training.exclusiveOr
import matplotlib.pyplot as plt
import pandas as pd
import xlrd
import numpy as np


def run():
    data = pd.read_excel('../data/TP3-ej2-Conjunto_entrenamiento.xlsx')
    output = data['y'].to_list()
    del data['y']
    data = data.values
    run_training(data, output)

def run_training(data, output):
    b = 1
    perceptron = NoLinearPerceptron(lambda p: np.tanh(b*p), lambda p: b * (1-((np.tanh(p))**2)), data, output, 0.001)

    while perceptron.get_current_step() <= 10000:
        perceptron.step()

    for elem, out in zip(data, output):
        print(" ".join([str(elem), str(out), "RESULT:", str(perceptron.classify(elem))]))
