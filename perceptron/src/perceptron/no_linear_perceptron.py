import random
import numpy as np


class NoLinearPerceptron:
    def __init__(self, activation_function, activation_derived, data, output, alpha):
        self.current_step = 0
        self.w = [random.uniform(-1, 1) for i in range(len(data[0]))]
        self.error = 1
        self.error_min = len(data) * 2
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
        activation = self.activation_function(excitation)
        # dw = 0
        # for i in range(len(self.data)):
        #     excitation = np.dot(self.data[i], self.w)
        #     activation = self.activation_function(excitation)
        #     dw += (self.output[i] - activation) * self.activation_derived(excitation) * self.data[i]
        # dw = self.alpha * dw
        dw = self.alpha * (self.output[index] - activation) * elem * self.activation_derived(excitation)
        self.w = self.w + dw

    def classify(self, inp):
        return self.activation_function(np.dot(inp, self.w))

    def get_error(self):
        return self.error

    def get_weights(self):
        return self.w

    def get_current_step(self):
        return self.current_step