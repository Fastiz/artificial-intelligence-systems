import matplotlib.pyplot as plt


def read_from_file(path):
    file = open(path, "r")
    lines = file.read().splitlines()
    return [(float(x), float(y), letter) for x, y, letter in [line.split(" ") for line in lines]]


def plot():
    values = read_from_file("../data")

    x = [x for x, y, letter in values]
    y = [y for x, y, letter in values]
    letters = [letter for x, y, letter in values]

    fig, ax = plt.subplots()
    ax.scatter(x, y)

    for i in range(len(values)):
        ax.annotate("  {0}".format(letters[i]), (x[i], y[i]))

    plt.show()


plot()
