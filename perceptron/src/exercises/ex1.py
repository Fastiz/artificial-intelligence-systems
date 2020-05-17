import training.andLogic
import training.exclusiveOr
import matplotlib.pyplot as plt

from perceptron.perceptron_network import PerceptronNetwork


def run():
    print("And")
    run_training(training.andLogic.getData())
    print("Exclusive or")
    run_training(training.exclusiveOr.getData())


def run_training(data):

    data, output = data
    perceptron = PerceptronNetwork(lambda p: 1 if p >= 0 else -1, lambda p: 1, data, output, [], 0.01, 1, bias=1)

    for i in range(100000):
        perceptron.step()

    for elem, out in zip(data, output):
        print(" ".join([str(elem), str(out), "RESULT:", str(perceptron.classify(elem))]))

    plot_data(data, output)


def plot_data(data, output):
    plt.figure()
    colors = {
        -1: 'r',
        1: 'b'
    }

    points = []
    cols = []

    for i in range(len(data)):
        x, y = data[i]
        out = output[i]
        points.append((x, y))
        cols.append(colors[out])

    plt.scatter([point[0] for point in points], [point[1] for point in points], c=cols)
    plt.show()
