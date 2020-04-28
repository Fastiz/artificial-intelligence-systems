import matplotlib.pyplot as plt
import numpy as np


def main():
    plot_fitness_vs_generation()
    plot_distinct_vs_generation()
    plt.show()


def plot_fitness_vs_generation():
    plt.figure()
    lines = open("./fitness").read().splitlines()

    values = [float(line) for line in lines]

    plt.plot(np.arange(0, len(values)), values)


def plot_distinct_vs_generation():
    plt.figure()
    lines = open("./distinct").read().splitlines()

    values = [float(line) for line in lines]

    plt.plot(np.arange(0, len(values)), values)


main()