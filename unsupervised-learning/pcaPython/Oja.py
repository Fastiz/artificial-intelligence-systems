import numpy as np
import random
import math

class Oja:
    data = []
    learn_factor = 0.05
    weights = []

    def __init__(self, data, learn_factor):
        self.data = data
        self.learn_factor = learn_factor
        scale = random.choice([-1, 1])
        initial_weight = scale / math.sqrt(len(data[0]))
        self.weights = [initial_weight for i in data[0]]

    def step(self):
        for x in self.data:
            self.weights += self.getDelta(x)

    def getDelta(self, vector):
        y = np.dot(vector, self.weights)
        xy = vector * y
        y2w = np.multiply(y*y, self.weights)
        return (xy - y2w) * self.learn_factor

    def getPrincipalComponent(self, x):
        return np.dot(x, self.weights)