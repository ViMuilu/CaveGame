# Design document
Tietojenkäsittelytieteen kandidaatti

Documentation language: English

## Libraries

### libGDX

[LibGDX](https://libgdx.badlogicgames.com/index.html) is game development framework for Java. I chose libGDX since it has a lot of extensions which will help me with the grapchics and game AI which aren't the main focus of this project.

#### Extensions used

[Tools](https://libgdx.badlogicgames.com/tools.html)

[Box2D](https://github.com/libgdx/libgdx/wiki/Box2d)

[Ashley](https://github.com/libgdx/ashley)

[gdxAi](https://github.com/libgdx/gdx-ai)

## Algorithms and Data structures
Map generation using [Binary space partitioning](http://www.roguebasin.com/index.php?title=Basic_BSP_Dungeon_generation).

Object postitioning using [Depth-first search algorithm](https://en.wikipedia.org/wiki/Depth-first_search)

### Why BSP?

With bsp it is easy to prevent rooms from overlapping or being too clumped when randomly generating maps. Also it is possible to generate a binary search tree of the rooms.

### Why Depth-first search?

With depth-first search we can determine all the different routes and thus prevent crucial objects such as keys and health items from spawning behind their intended targets. Time complexity O(n + m).

