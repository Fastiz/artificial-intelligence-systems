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
3. Anular
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
### Examples
Copy and paste into the configuration.txt file to run map 1 with 'A*' as algorithm and 'simple assignation cosidering walls' heuristic.
```
# Map number
#>>>
1
# Algorithm number (A*: 1, BFS: 2, DFS: 3, GLOBAL GREEDY: 4, IDA*: 5, IDDFS: 6)
#>>>
1
#If uninformed algorithm remove next line. Else pick a Heuristic (BRUTEFORCE ASSIGNATION: 1,
#	GREEDY ASSIGNATION: 2, TRIVIAL: 3, WALKABLE DISTANCE: 4, SIMPLE ASSIGNATION: 5, SIMPLE ASSIGNATION CONSIDERING WALLS: 6)
#>>>
6
#Following line is reserved for the parameter of IDDFS. If you are using another algorithm remove line.
#If your algorithm is IDDFS enter max depth or leave the line with -1 if no limit.
#>>>
```
The following one is to run DFS on map 3.
```
# Map number
#>>>
3
# Algorithm number (A*: 1, BFS: 2, DFS: 3, GLOBAL GREEDY: 4, IDA*: 5, IDDFS: 6)
#>>>
3
#If uninformed algorithm remove next line. Else pick a Heuristic (BRUTEFORCE ASSIGNATION: 1,
#	GREEDY ASSIGNATION: 2, TRIVIAL: 3, WALKABLE DISTANCE: 4, SIMPLE ASSIGNATION: 5, SIMPLE ASSIGNATION CONSIDERING WALLS: 6)
#>>>
#Following line is reserved for the parameter of IDDFS. If you are using another algorithm remove line.
#If your algorithm is IDDFS enter max depth or leave the line with -1 if no limit.
#>>>
```
## Output file
In the file output.txt is saved the result of the execution. If the algorithm found a solution then it will print information of the execution and then display the solution for the puzzle.
In the solution the characters used for displaying the map are the following:
* \# : wall
* @ : player
* $ : box
* . : goal
* blank-space : empty
## Map files
We included map files inside the maps folder. If you want to add another map then place it inside the folder and name it as 'map' followed with an unused number. Then reference the map in the configuration file.
The format of the file is the following:
In the first line there should be information about the map dimensions, and it should be written as 'height width' (separated with a blank space).
The following lines are reserved for the map content with the format specified in 'Output file' section.
***The map content should be coherent with the dimensions specified in the first line. Also, if the walls are placed before the end of the map, then fill with spaces.***
