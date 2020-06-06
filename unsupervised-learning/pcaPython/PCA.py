import pandas as pd
from sklearn.decomposition import PCA
from sklearn.preprocessing import StandardScaler
import matplotlib.pyplot as plt
import numpy as np
from Oja import Oja

data = pd.read_csv("../europe.csv")

standard_data = StandardScaler().fit_transform(data[data.columns.difference(["Country"])])
pca = PCA(n_components=1)
principal_components = pca.fit_transform(standard_data)
print(principal_components)

print('--------')
oja = Oja(standard_data, 0.0005)

for i in range(1000):
    oja.step()

for i in standard_data:
    print(oja.getPrincipalComponent(i))
