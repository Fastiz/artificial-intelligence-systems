import numpy as np
from matplotlib import pyplot as plt
from sklearn.decomposition import PCA
from sklearn.preprocessing import StandardScaler


def matrix_graph(path, format_annotate=False):
    lines = open(path).read().splitlines()

    dims = [int(dim) for dim in lines[0].split(" ")]
    matrix = np.zeros(dims)

    fig, ax = plt.subplots()

    matrix_lines = lines[1:]
    for i in range(dims[0]):
        for j in range(dims[1]):
            line = matrix_lines[i*dims[1] + j]
            matrix[i, j] = float(line)
            if format_annotate:
                ax.annotate("{0:0.2f}".format(float(line)), (j-0.1, i))
            else:
                ax.annotate(float(line), (j-0.07, i))

    squeezed_matrix = np.squeeze(matrix)
    im = plt.imshow(X=squeezed_matrix)
    fig.colorbar(im)
    plt.xticks(np.arange(0, dims[0], 1))
    plt.yticks(np.arange(0, dims[1], 1))
    plt.show()


def get_array_matrix_from_file(path):
    lines = open(path).read().splitlines()

    dims = [int(dim) for dim in lines[0].split(" ")]
    matrix = [[[] for j in range(dims[0])] for i in range(dims[1])]

    matrix_lines = lines[1:]
    for i in range(dims[0]):
        for j in range(dims[1]):
            line = matrix_lines[i*dims[1] + j]
            matrix[i][j] = line.replace("\"", "").replace("[", "").replace("]", "").split(", ")

    return matrix


def cell_members_graph():
    matrix = get_array_matrix_from_file("../results/kohonen/cellMembers")

    for i in range(len(matrix)):
        for j in range(len(matrix[1])):
            print("({0}, {1}): {2}".format(str(i), str(j), str(matrix[i][j])))


def graph_weights():
    matrix = get_array_matrix_from_file("../results/kohonen/cellWeights")

    indexes = []
    values = []
    for i in range(len(matrix)):
        for j in range(len(matrix[1])):
            values.append(matrix[i][j])
            indexes.append((i, j))

    standard_data = StandardScaler().fit_transform(values)
    pca = PCA(n_components=2)
    principal_components_array = pca.fit_transform(standard_data)

    x = [x for x, y in principal_components_array]
    y = [y for x, y in principal_components_array]

    fig, ax = plt.subplots()
    ax.scatter(x, y)

    for t in range(len(indexes)):
        i, j = indexes[t]
        ax.annotate(" ({0}, {1})".format(i, j), (x[t], y[t]))

    plt.show()


matrix_graph("../results/kohonen/cellPopulation")
matrix_graph("../results/kohonen/cellDistance", True)
cell_members_graph()
graph_weights()
