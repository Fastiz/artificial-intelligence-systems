import matplotlib.pyplot as plt
import numpy as np


def main():
    lines = open("./output").read().splitlines()

    values = [float(line) for line in lines]

    plt.plot(np.arange(0, len(values)), values)
    plt.show()


main()