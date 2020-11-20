
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
    int index;
    
    public Node(int x, int y , int height, int width){
        this.x = x;
        this.y = y;
        this.height = height;
        this.width = width;
        nodes = new ArrayList<>();
        index = 0;
        
        
    }
    
    public void divide(Node n){
        // Change random before final dl
        Random rd = new Random();
        if(n.x <= 5){
            n.x += 15;
        }
        if(n.y <= 5){
            n.y+=15;
        }
        if(n.x >= 395){
            n.x -= 15;
        }
        if(n.y >= 395){
            n.y -= 15;
        }
        int divider =rd.nextInt((2 - 1) + 1) + 1;
        int size = rd.nextInt((15 - 10) + 1) + 10;
        // add node to list if size is smaller than 5
        if(n.height - size < 5 || n.width - size < 5){
           
           nodes.add(new Node(n.x,n.y,n.height,n.width));
           index++;
           return;
        }
        
        if(divider == 2) {
            // split horizontal
            
            left = new Node(n.x, n.y, n.width, size);
            right = new Node(n.x, n.y + size, n.width, n.height - size);

        } else {
            left = new Node(n.x, n.y,size , n.width);
            right = new Node(n.x+ size, n.y ,n.height - size , n.width);

        }
        divide(left);
        divide(right);
    }
    
    public ArrayList getNodes(){
        return nodes;
    }
    
}
