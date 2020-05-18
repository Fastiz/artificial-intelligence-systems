from perceptron.perceptron_network import PerceptronNetwork
import numpy as np
import pandas as pd


def run():
    execute_prime_numbers([0, 7, 9], [0, 1, 2, 3, 4, 5, 6, 7, 8, 9])


def get_numbers_data(numbers_to_train):
    data = read_numbers()
    output = get_numbers_output()
    data_to_train = []
    output_to_train = []
    for i in numbers_to_train:
        data_to_train.append(data[i])
        output_to_train.append(output[i])

    return data_to_train, output_to_train

def execute_prime_numbers(numbers_to_train, numbers_to_classify):
    data, output = get_numbers_data(numbers_to_train)
    b = 1
    pnw = PerceptronNetwork(lambda p: np.tanh(b*p), lambda p: b * (1-((np.tanh(p))**2)), data, output,
                        [3, 3], 0.2, 1)

    for i in range(100000):
        pnw.step()

    data_to_classify, output_to_classify = get_numbers_data(numbers_to_classify)

    for data_in, data_out in zip(data_to_classify, output_to_classify):
        print(" ".join([str(data_in), str(data_out), "-->", str(pnw.classify(data_in))]))

def read_numbers():
    numbers = open("../data/numeros.dat", "r")
    lines = numbers.readlines()
    i = 0
    data = []
    current_index = 0
    for line in lines:
        if i % 7 == 0:
            data.append([])
            if i != 0:
                current_index += 1

        for character in line:
            if str.isdigit(character):
                data[current_index].append(int(character))
        i += 1

    return data


def get_numbers_output():
    return [-1, -1, -1, 1, -1, 1, -1, 1, -1, -1]

