## Project structure
![Diagram](https://github.com/ViMuilu/CaveGame/blob/main/documents/caveGame.png)

## Complexity and performance

Time complexity of the binary space partiotioning algorithm used in this project should be O(nm).
This is beacause of the while(true) ... fore loops in map class(rows 93-106). And the createRooms method in node class is also O(nm) since it calls itself and the visit rooms method.
I tested the speed of the algorithm using the following method.
x,y are the size of the starting region.

Some performance results.

x, y | Maximum node size | Minimum node size | avarage time in seconds |
----|-------------------|-------------------|-------------------------|
9000 | 1000 | 600 | 0.513 |
9000 | 100 | 60 | 0.462 |
900 | 100 | 60 | 0.024 |
900 | 100 | 6 | 0.229|
900 | 10 | 6 | 0.213 |
90 | 10 | 6 | 0.007 |


## Improvements

The maps could be made more natural looking by using cellular automata to genereate more realistic walls.
Also a major improvemnt would be adding enemies, collectible items and collidable walls.
The bsp algorithm node generation could most likely be done using recursion and thus eliminating the while fore loop in map class.

## Miscellaneus stuff

I used ThreadLocalRandom in node class insted of making my own random class because java random doesn't work in a multithreded enviroment and i expected that a random class of my own would have similiar porblems so I chose to use ThreadLocalRandom.

The program sometimes generates corridors throug rooms. This only happens when the root node x and y values are large when those values are 30 and 30 this happens maybe every 10 runs or so. This is most likely a scaling issue it is diificult to find appropriate values for the root node and min-and maxsize.

## Sources

(BSP tree wikipedia)[https://en.wikipedia.org/wiki/Binary_space_partitioning]
(BSP tree roguebasin)[http://www.roguebasin.com/index.php?title=Basic_BSP_Dungeon_generation]
