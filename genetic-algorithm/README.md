# Genetic algorithm
## Introduction
Algorithm to find the best configuration for each character of a specific rol game.
## Executing the program
To execute the program download the [genetic-algorithm.jar](https://github.com/Fastiz/artificial-intelligence-systems/releases) from releases and place it inside the directory that includes the configuration file.
Then open a shell in the same directory and run the following script:
```
java -jar genetic-algorithm.jar
```
## Configuration file
The configuration file reads different inputs depending on the methods used.
### Fitness function 
Set the fitness function for the kind of character you want to optimize. The options are:
1. Archer
2. Defender
3. Spy
4. Warrior

To set it just type fitnessFunction=X, where X is the option number.
### Crossover
Select the type of crossover you want to use. The options are
1. One dot
2. Two dots
3. Ring (Anular in spanish)
4. Uniform

To set it type crossover=X, where X is the option number.
### Selection
Choose the selection method. The options are:
1. Fill all
2. Fill parent

### Parent selection function
Choose the selection function for the parents. The options are:
1. Bolztmann
  Parameters:
     a) selectionParameter = T0
     b) selectionParameter2 = TC
     c) selectionParameter3 = k
2. Deterministic tournament
  Parameters:
     a) selectionParameter = M
3. Elite
4. Ranking
5. Roulette
6. Stochastic tournament
7. Universal

To set the method type selectionFunction=X, where X is the option number.
### Second parent selection function
Same as before, but for the function use:
secondSelectionFunction = X

And for the parameters:
secondSelectionParameter = X
secondSelectionParameter2 = X
secondSelectionParameter3 = X

### Replacement selection function
Choose the selection function to replace individuals.
Same as before, but for the function use:
replacementFunction = X

And for the parameters:
replacementParameter = X
replacementParameter2 = X
replacementParameter3 = X

### Second replacement selection function
Same as before, but for the function use:
secondReplacementFunction = X

And for the parameters:
secondReplacementParameter = X
secondReplacementParameter2 = X
secondReplacementParameter3 = X

### First parent selection function percentage
Set the percentage for the first parent selection function.

Type a=X, where X is the percentage.

### First replacement selection function percentage
Set the percentage for the first replacement selection function.

Type b=X, where X is the percentage.

### Mutation
Choose the type of mutation. The options are:
1. Single gen
2. Limite multigen
3. Uniform multigen
4. Complete

To set the mutation type mutation=X, where X is the option number.
You also must set the mutation probabiliy by typing mutationProbability=Y, where Y is the probability.
### Cutting method
Choose the cirterion to decide if the algorithm should finish. The options are:
1. Time
  Parameters:
    a) cutParameter = Maximum time in seconds.
2. Generations amount
  Parameters:
    a) cutParameter = Maximum generation number.
3. Acceptable solution
  Parameters:
    a) cutParameter = Acceptable fitness value
4. Content. (Best fitness does not change after a specific amount of generations)
  Parameters:
    a) cutParameter = Amount of generations
    b) cutParameter2 = Error (The minimum difference two fitness values must have to be considered different, for example, if you     set it to 0 but the fitness change a minimum values during the amount of generations setted, the program will not end, because     if the error is 0 the value must be exactly the same).
5. Estructure. (A percentage of individuals does not change after an amount of generationss)
  Parameters:
    a) cutParameter = Amount of generations
    b) cutParameter2 = Percentage (This is the minimum percentage of coincidences that the generations must have between them in       order to finish the execution).
  

To set the mutation type mutation=X, where X is the option number.
You also must set the mutation probabiliy by typing mutationProbability=Y, where Y is the probability.

### Important
Unused parameter are ignored, there is no need to delete them, just keep them in the file to save the variable names in case you need to use them later.

### Examples
Copy and paste into the configuration.txt file to run map 1 with 'A*' as algorithm and 'simple assignation cosidering walls' heuristic.
```
# Los parametros sin usar con valores seteados no se tendran en cuenta, se pueden dejar
# sin cambios.
#
# Fitness function // 1. Archer | 2. Defender | 3. Spy | 4. Warrior
fitnessFunction=4
# Cruces // 1. Un punto | 2. Dos puntos | 3. Anular | 4. Uniforme
crossover=1

# Seleccion // 1. Fill all | 2. Fill parent
selection=1

# Porcentaje de funcion de reemplazo de individuos numero uno.
b = 0.5

# Porcentaje de funcion de seleccion de padres numero uno.
a = 0.5

# Funcion de seleccion de padres
# 1. Boltzmann / Parametros: selectionParamter = T0 | selectionParamter2 = TC | selectionParamter3 = k
# 2. Torneo deterministico / Parametros: selectionParamter = M
# 3. Elite
# 4. Ranking
# 5. Ruleta
# 6. Torneo probabilistico
# 7. Universal
selectionFunction=3
#unused
selectionParameter=1
selectionParameter2=1
selectionParameter3=1

# Segunda funcion de seleccion de padres
# Igual que el anterior, con distintos nombres en las variables.
secondSelectionFunction=4
#unused
secondSelectionParameter=1
secondSelectionParameter2=1
secondSelectionParameter3=1

# Funcion de reemplazo de individuos
# Igual que el anterior, con distintos nombres en las variables.
replacementFunction=5
#unused
replacementParameter=1
replacementParameter2=1
replacementParameter3=1

# Segunda funcion de reemplazo de individuos
# Igual que el anterior, con distintos nombres en las variables.
secondReplacementFunction=6
#unused
secondReplacementParameter=1
secondReplacementParameter2=1
secondReplacementParameter3=1

# Mutacion // 1. Gen simple | 2. Multigen limitada | 3. Multigen uniforme | 4. Completa
mutation=3
mutationProbability=0.6

# Cirterio de corte //
# 1. Tiempo / Parametros: cutParameter = tiempo de corte.
# 2. Cantidad de generaciones / Parametros: cutParameter = cantidad de generaciones
# 3. Solucion aceptable / Paramtros: cutParameter = fitness aceptable
# 4. Contenido / Paramtros: cutParameter = cantidad de generaciones | cutParamter2 = error
# 5. Estructural / Paramtros: cutParameter = cantidad de generaciones | cutParamter2 = porcentaje de cambio
cutMethod=1
cutParameter=10
#unused
cutParameter2=0.005
```
