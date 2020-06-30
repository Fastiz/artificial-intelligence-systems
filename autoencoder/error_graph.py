import matplotlib.pyplot as plt

def read_from_file(path):
    file = open(path, "r")
    lines = file.read().splitlines()
    return [float(line) for line in lines]

def graph_error():
    data = read_from_file("./errorIt")
    plt.plot(range(1, len(data) + 1), data)
    plt.ylabel('Error')
    plt.xlabel('Iterations')
    plt.show()

graph_error()
