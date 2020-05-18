from perceptron.perceptron_network import PerceptronNetwork
import numpy as np
import ex1
import training.exclusiveOr
import pandas as pd


def run():
    print("Exclusive or ------------------------")
    run_exclusive_or(training.exclusiveOr.getData())
    print("Prime numbers ------------------------")
    run_prime_numbers([0, 1, 2, 3, 4, 5, 6, 7, 8, 9], [0, 1, 2, 3, 4, 5, 6, 7, 8, 9])


def run_exclusive_or(data):
    data, output = data
    perceptron = PerceptronNetwork(lambda p: 1 if p >= 0 else -1, lambda p: 1, data, output, [8, 8], 0.01, 1, bias=1)

    for i in range(100000):
        perceptron.step()

    for elem, out in zip(data, output):
        print(" ".join([str(elem), str(out), "RESULT:", str(perceptron.classify(elem))]))


def run_prime_numbers(numbers_to_train, numbers_to_classify):
    data, output = get_numbers_data(numbers_to_train)
    b = 1
    pnw = PerceptronNetwork(lambda p: np.tanh(b*p), lambda p: b * (1-((np.tanh(p))**2)), data, output,
                        [8, 8], 0.2, 1)

    for i in range(100000):
        pnw.step()

    data_to_classify, output_to_classify = get_numbers_data(numbers_to_classify)

    for data_in, data_out in zip(data_to_classify, output_to_classify):
        print(" ".join([str(data_in), str(data_out), "-->", str(pnw.classify(data_in))]))


def get_numbers_data(numbers_to_train):
    all_data = read_numbers()
    all_output = get_numbers_output()
    data = []
    output = []
    for i in numbers_to_train:
        data.append(all_data[i])
        output.append(all_output[i])

    return data, output


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

