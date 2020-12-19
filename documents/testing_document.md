# Tesintg document

## Unit testing
[Code coverage](https://github.com/ViMuilu/CaveGame/blob/main/documents/codecov.png)


When running jUnit tests remember to comment out lines 35-42, 44-54 and 58-81 in map class this has to be done because I wasn’t able to make these texture readers work in the test class. 

First run the test in game:core.Then Jacoco cand be generated inside netbeans by right clicking game:core folder and then gradle run and gradle command jacocotestreport.
The report is in core/build/jacocoHTML


There are only two tests because the algorithm generates random results thus making testing difficult.

One of the tests simply goes through a list of rooms and checks if they are in the mapcoordinates array.
The second test checks if all rooms have a corridor this is achieved by iterating throug all the coordinates next to the room and stopping if one of the coordinates is 1 ie there is a corridor there.

## Manual testing
Method used

long startTime = System.currentTimeMillis();
Node root = new Node(0, 0, x, y);
generateMap(root);
long stopTime = System.currentTimeMillis();
And also changing the max node size in map class and min node size in node class

x, y | Maximum node size | Minimum node size | avarage time in seconds |
----|-------------------|-------------------|-------------------------|
9000 | 1000 | 600 | 0.513 |
9000 | 100 | 60 | 0.462 |
900 | 100 | 60 | 0.024 |
900 | 100 | 6 | 0.229|
900 | 10 | 6 | 0.213 |
90 | 10 | 6 | 0.007 |

Times between first and second sets and third and fifth sets is most likely due to the maximum and minimum node values in the third being optimal for 900 x y.