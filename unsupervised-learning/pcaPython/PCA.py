import pandas as pd
from sklearn.decomposition import PCA
from sklearn.preprocessing import StandardScaler
import matplotlib.pyplot as plt
import numpy as np
from Oja import Oja

def get_pca():
    data = pd.read_csv("../europe.csv")

    standard_data = StandardScaler().fit_transform(data[data.columns.difference(["Country"])])
    pca = PCA(n_components=1)
    principal_components_array = pca.fit_transform(standard_data)
    principal_components = []
    for pc in principal_components_array:
        principal_components.append(pc[0])

    return principal_components
