# Sokoban Solver
## Introduction
Solver for [sokoban game](https://en.wikipedia.org/wiki/Sokoban) using different search algorithms.
## Algorithms
The following list represents the algorithms that were implemented to solve the game tree. Some are informed and others not.
- A* (A-star)
- DFS (Depth-first search)
- BFS (Breath-first search)
- Global greedy
- IDA* (Iterative deepening A-star)
- IDDFS (Iterative deepening depth-first search)
## Heuristics
It were implemented a series of heuristic to test the informed algorithms. All of the following heuristics are [admissible](https://en.wikipedia.org/wiki/Admissible_heuristic?oldformat=true). 
### Bruteforce assignation
Assigns the best fit for each box and goal according to manhattan distance. This algorithm has complexity O(n!) so it is not recommended for puzzles that have a lot of boxes.
### Greedy assignation
Assigns a good-enough fit for goals and boxes. Could happen that multiple boxes are assigned to the same goal.
### Simple assignation
Sums of the distance of the closest goal to each box. Totally ignores if the goal is already assigned for another box.
### Walkable distance
The length of the path the player would have to travel to each goal.
### Simple assignation considering walls
The same as simple assignation but instead of finding the manhattan distance, uses the closest path without going over walls.
## Executing the program
To execute the program download the [artificial-intelligence-systems.jar](https://github.com/Fastiz/artificial-intelligence-systems/releases) from releases and place it inside the directory that includes the maps, configuration.txt and output.txt.
Then open a shell in the same directory and run the following script:
```
java -jar artificial-intelligence-systems.jar
```
Then the program should run and save the output inside output.txt.
## Configuration file
The configuration file reads different inputs depending on the algorithm used. The format for each line is the following:
### Map number
The first line should be the number of the map wanted to solve inside *maps* folder. Number should be between 1 and N, where N is the number of the map with the biggest index.
### Algorithm
The second line represents the number id of the algorithm wanted to run. The algorithms are coded with the following numbers:
1. A*
2. BFS
3. DFS
4. Global greedy
5. IDA*
6. IDDFS
### Heuristic
If the algorithms chosen is informed then the third line is reserved for the id of the heuristic to use. The heuristic is coded with the following numbers:
1. Bruteforce assignation
2. Greedy assignation
3. Trivial
4. Walkable distance
5. Simple assignation
### Additional parameter
The last line in reserved for additional parameter that some algorithms require. If your algorithm is IDDFS enter max depth or leave the line with -1 if no limit.
If your algorithm is not mentioned before then remove the line.
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
