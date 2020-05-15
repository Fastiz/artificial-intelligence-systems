from perceptron.perceptron import Perceptron
import training.andLogic
import training.exclusiveOr
import matplotlib.pyplot as plt


def run():
    print("And")
    run_training(training.andLogic.getData())
    print("Exclusive or")
    run_training(training.exclusiveOr.getData())


def run_training(data):

    data, output = data
    plot_data(data, output)
    perceptron = Perceptron(lambda p: 1 if p >= 0 else -1, data, output, 0.001)

    weights = []
    while perceptron.get_current_step() <= 10000:
        perceptron.step()
        weights.append(perceptron.get_weights())

    plot_weights(weights)

    for elem, out in zip(data, output):
        print(" ".join([str(elem), str(out), "RESULT:", str(perceptron.classify(elem))]))

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


def plot_weights(weights):
    plt.figure()
    plt1, = plt.plot([weight[0] for weight in weights], label="w1")
    plt2, = plt.plot([weight[1] for weight in weights], label="w2")

    plt.legend(handles=[plt1, plt2])

    plt.show()
