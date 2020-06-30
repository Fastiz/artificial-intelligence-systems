import matplotlib.pyplot as plt
import matplotlib as mpl
import numpy as np


def read_from_file(path):
    file = open(path, "r")
    lines = file.read().splitlines()

    index = 0
    for i in range(len(lines)):
        if lines[i] == "":
            index = i
            break

    segment_a = lines[:index]
    segment_b = lines[index + 1:]

    return ([(round(float(R)) / 255, round(float(G)) / 255, round(float(B)) / 255) for R, G, B in
             [line.split(" ") for line in segment_a]],
            [(round(float(R)) / 255, round(float(G)) / 255, round(float(B)) / 255) for R, G, B in
             [line.split(" ") for line in segment_b]])


def plot():
    a, b = read_from_file("../colors")

    color_plot(a)

    color_plot(b)

    plt.show()


def color_plot(values):
    fig, ax = plt.subplots(figsize=(6, 1))
    fig.subplots_adjust(bottom=0.5)

    cmap = mpl.colors.ListedColormap(values)

    ticks = []

    fig.colorbar(
        mpl.cm.ScalarMappable(cmap=cmap),
        cax=ax,
        spacing='proportional',
        orientation='horizontal',
        ticks=ticks
    )


plot()
