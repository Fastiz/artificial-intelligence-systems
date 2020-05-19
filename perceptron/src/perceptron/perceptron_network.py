import random
import numpy as np


# Generates n random values with uniform distribution between -1 and 1
def generate_random_values(n):
    return [random.uniform(-1, 1) for i in range(n)]


# Generates weights into array with the format: weights[m][i][j]
# where m is the layer, j is the layer's node and i is the next layer node
def generate_weights(layer_dims):
    all_weights = []

    layer_dim = layer_dims[0]
    for next_layer_dim in layer_dims[1:]:
        layer_weights = []
        for i in range(next_layer_dim):
            layer_weights.append(generate_random_values(layer_dim))
        all_weights.append(layer_weights)
        layer_dim = next_layer_dim

    return all_weights


class PerceptronNetwork:
    def __init__(self, activation_function, activation_function_derivative, data_in, data_out, dim_inner_layers, alpha,
                 out_dim, bias=0):
        self.bias = bias
        self.alpha = alpha
        self.layer_dims = [len(data_in[0])+1] + dim_inner_layers + [out_dim]
        self.weights = generate_weights(self.layer_dims)
        self.data_in = np.array([data + [-bias] for data in data_in])
        self.data_out = np.array(data_out)
        self.activation_function = activation_function
        self.activation_function_derivative = activation_function_derivative

    def classify(self, inp):
        vs, hs = self.propagate(inp + [-self.bias])
        return vs[-1]

    def calculate_error(self, in_values, out_values):
        error = 0
        for in_val, out_val in zip(np.array([data + [-self.bias] for data in in_values]), out_values):
            vs, hs = self.propagate(in_val)
            error += (np.linalg.norm(np.array(out_val) - vs[-1]))**2
        return (1/len(in_values))*error

    def propagate(self, in_val):
        vs = [in_val]
        hs = []
        for layer_weights in self.weights:
            v = []
            h = []
            for i_weights in layer_weights:
                dot_product = np.dot(i_weights, vs[-1])
                h.append(dot_product)
                v.append(self.activation_function(dot_product))
            vs.append(v)
            hs.append(h)
        return vs, hs

    def step(self):
        # Choose the pattern
        in_val, out_val = random.choice(list(zip(self.data_in, self.data_out)))

        # Propagate the signal
        vs, hs = self.propagate(in_val)

        # Backwards propagation (computing deltas)
        deltas = [np.array([np.dot(np.array([self.activation_function_derivative(h) for h in hs[-1]]), (out_val - vs[-1]))])]
        for m in reversed(range(len(hs[:-1]))):
            first_term = np.array([self.activation_function_derivative(h) for h in hs[m]])
            second_term = np.array([np.dot(transposed, deltas[-1]) for transposed in np.transpose(self.weights[m+1])])
            deltas.append(first_term * second_term)

        # Update weights
        for m in range(len(deltas)):
            layer_weights = self.weights[m]
            for i in range(len(layer_weights)):
                i_weights = layer_weights[i]
                for j in range(len(i_weights)):
                    i_weights[j] += self.alpha * list(reversed(deltas))[m][i] * vs[m][j]
