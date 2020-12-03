package com.vm.cavegame.map;

import java.util.ArrayList;
import java.util.Random;

public class Node {

    public int x;
    public int y;
    public int height;
    public int width;
    public Node left;
    public Node right;
    // change before final dl
    ArrayList<Node> nodes;
    boolean visited;
    int index;
    int[][] path;

    public Node(int x, int y, int height, int width, int index) {
        this.x = x;
        this.y = y;
        this.height = height;
        this.width = width;
        nodes = new ArrayList<>();
        this.index = index;
        visited = false;

    }

    // recursively creates all rooms/nodes
    public Node divide(Node current) {
        // Change random before final dl
        Random rd = new Random();
        
        if (current.x >= 110) {
            current.x -= 20;
        }
        if (current.y >= 110) {
            current.y -= 20;
        }
        //divider is used to decide which way the next room is rendered
        int divider = rd.nextInt((2 - 1) + 1) + 1;
        int size = 10;
        int dist = 20;
        // return when node height and width is smaller then 5 this becomes more useful when room sizes are randomized
        if (current.height - size < 5 || current.width - size < 5) {
            return current;
        }

        // remember to make room generation more random
        if (divider == 2) {
            // split horizontal

            index++;
         
            current.right = divide(new Node(current.x, current.y + dist, current.width, current.height - size, index));

        } else {
            //split vertical

            index++;
          
            current.right = divide(new Node(current.x + dist, current.y, current.height - size, current.width, index));

        }

        return current;
    }

    public ArrayList getNodes() {
        return nodes;
    }

 
}
