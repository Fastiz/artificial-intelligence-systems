import os


def read_files_iterations():
    return read_all_files("../results/iterations/")


def read_files_pc():
    return read_all_files("../results/principalComponents/")


def read_all_files(path):
    principal_components = []
    learning_factors = []
    _, _, files = next(os.walk(path))
    for iteration in range(1, len(files)):
        pc, lf = read_file(path + "principalComponents" + str(iteration))
        principal_components.append(pc)
        learning_factors.append(lf)

    return principal_components, learning_factors


def read_weights():
    lines = open("../results/weights").read().splitlines()
    weights = []
    for line in lines:
        weights.append(float(line))

    return weights


def read_file(path):
    lines = open(path).read().splitlines()
    principal_components = []
    i = 0
    learning_factor = 0
    for line in lines:
        if i == 0:
            learning_factor = float(line)
        else:
            principal_components.append(float(line))
        i += 1
    return principal_components, learning_factor

