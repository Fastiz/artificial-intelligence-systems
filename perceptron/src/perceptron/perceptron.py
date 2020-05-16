import random
import numpy as np


class Perceptron:
    def __init__(self, activation_function, activation_derived, data, output, alpha, bias=0):
        self.bias = bias
        self.current_step = 0
        self.w = [random.uniform(-1, 1) for i in range(len(data[0]))]
        self.activation_function = activation_function
        self.data = np.array(data)
        self.output = np.array(output)
        self.alpha = alpha
        self.activation_derived = activation_derived

    def step(self):
        self.current_step += 1
        index = random.randint(0, len(self.data)-1)
        elem = self.data[index]
        excitation = np.dot(elem, self.w)
        dw = np.array([
            self.alpha * (self.output[index] - self.activation_function(excitation - self.bias)) *
            self.activation_derived(excitation - self.bias) * inp
            for inp in elem
        ])
        self.w = self.w + dw

    def classify(self, inp):
        return self.activation_function(np.dot(inp, self.w) - self.bias)

    def get_error(self):
        return (1/2.0) * sum([(out - self.activation_function(np.dot(elem, self.w) - self.bias))**2
                              for elem, out in zip(self.data, self.output)])

    def get_weights(self):
        return self.w

    def get_current_step(self):
        return self.current_step
