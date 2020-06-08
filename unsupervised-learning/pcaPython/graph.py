from pcaReaderFromJava import read_files_pc, read_weights, read_files_iterations
from PCA import get_pca
import numpy as np
from matplotlib import pyplot as plt
import pandas as pd
from radarChart import graphRadarChart

class Grapher:

    data = None
    principal_components_oja = None
    principal_components_oja_iterations = None
    principal_components = None
    learning_factors = None
    iterations = None

    def __init__(self):
        self.principal_components_oja, self.learning_factors = read_files_pc()
        self.principal_components = get_pca()
        self.principal_components_oja_iterations, self.iterations = read_files_iterations()
        self.data = pd.read_csv("../europe.csv")

    def get_values(self):
        return self.principal_components, self.principal_components_oja, self.learning_factors

    def graph_error(self):
        principal_components, principal_components_oja, learning_factors = self.get_values()
        errors = []
        for pc_oja in principal_components_oja:
            errors.append(np.linalg.norm(np.array(pc_oja) - np.array(principal_components)))

        plt.plot(learning_factors, errors)
        plt.yscale('log')
        plt.ylabel('Error')
        plt.xlabel('Learn factor')
        plt.show()

    def graph_oja_vs_library(self):
        principal_components, principal_components_oja, learning_factors = self.get_values()
        labels = self.data['Country'].values

        x = np.arange(len(labels))  # the label locations
        width = 0.25  # the width of the bars

        fig, ax = plt.subplots(figsize=(18, 8))
        ax.bar(x - width, principal_components, width, label='skLearn', zorder=3)
        ax.bar(x, principal_components_oja[0], width, label='Oja. LF=' + str(learning_factors[0]), zorder=3)
        it_amount = len(principal_components_oja)
        ax.bar(x + width, principal_components_oja[it_amount-1], width, label='Oja. LF=' + str(learning_factors[it_amount-1]), zorder=3)
        ax.grid(zorder=0)
        # Add some text for labels, title and custom x-axis tick labels, etc.
        ax.set_ylabel('Principal component')
        ax.set_xticks(x)
        ax.set_xticklabels(labels)
        ax.legend()

        fig.tight_layout()
        plt.xticks(rotation=90)
        plt.show()


    def graph_radar(self):
        weights = read_weights()
        positive_weights = []
        for weight in weights:
            if weight > 0:
                positive_weights.append(weight)
            else:
                positive_weights.append(-weight)
        cat = self.data.columns.values[1::]
        graphRadarChart(cat, positive_weights)

    def graph_iterations_error(self):
        errors = []
        for pc_oja in self.principal_components_oja_iterations:
            errors.append(np.linalg.norm(np.array(pc_oja) - np.array(self.principal_components)))

        print(errors)
        plt.plot(self.iterations, errors)
        plt.yscale('log')
        plt.ylabel('Error')
        plt.xlabel('Iterations')
        plt.show()

    def graph_all(self):
        self.graph_radar()
        self.graph_error()
        self.graph_oja_vs_library()
        self.graph_iterations_error()
