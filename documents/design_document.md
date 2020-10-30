# Design document
Tietojenkäsittelytieteen kandidaatti

Documentation language: English

## Algorithms and Data structures
Map generation using [Binary space partitioning](http://www.roguebasin.com/index.php?title=Basic_BSP_Dungeon_generation).

Object postitioning using [Depth-first search algorithm](https://en.wikipedia.org/wiki/Depth-first_search)

### Why BSP?

With bsp it is easy to prevent rooms from overlapping or being too clumped when randomly generating maps. Also it is possible to generate a binary search tree of the rooms.

### Why Depth-first search?

With depth-first search we can determine all the different routes and thus prevent crucial objects such as keys and health items from spawning behind their intended targets. Time complexity O(n + m).

