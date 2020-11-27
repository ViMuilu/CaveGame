
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
    
    public Node(int x, int y , int height, int width, int index){
        this.x = x;
        this.y = y;
        this.height = height;
        this.width = width;
        nodes = new ArrayList<>();
        this.index = index;
        visited = false;
        
    }
    // recursively crates all rooms/nodes
    public Node divide(Node current){
        // Change random before final dl
        Random rd = new Random();
        
        if(current.x <= 10){
            current.x += 15;
        }
        if(current.y <= 10){
            current.y+=15;
        }
        if(current.x >= 110){
            current.x -= 15;
        }
        if(current.y >= 110){
            current.y -= 15;
        }
        
        int divider =rd.nextInt((2 - 1) + 1) + 1;
        int size = 10;
        // add node to list if size is smaller than 5
        if(current.height - size < 5 || current.width - size < 5){
           
           nodes.add(new Node(current.x,current.y,current.height,current.width,index));
           
           return current;
        }
        // remember to make room generation more random
        if(divider == 2) {
            // split horizontal
            index++;
            current.left = divide(new Node(current.x, current.y, current.width, size,index));
            index++;
            current.right = divide(new Node(current.x, current.y + size, current.width, current.height - size,index));
            
        } else {
            index++;
            current.left = divide(new Node(current.x, current.y,size , current.width,index));
            index++;
            current.right = divide(new Node(current.x+ size, current.y ,current.height - size , current.width,index));

        }

        return current;
    }
    
    public ArrayList getNodes(){
        return nodes;
    }
    
}
