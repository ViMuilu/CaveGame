package com.vm.cavegame.map;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.MapLayers;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.tiles.StaticTiledMapTile;
import com.badlogic.gdx.physics.box2d.World;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Stack;

public class Map {

    private TiledMap map;
    private Texture tiles;
    private Texture wallRight;
    private Texture wallLeft;
    int[][] coordinatesFor = new int[120][120];
    public TiledMap renderMap(World world) {
        Node root = new Node(10, 10, 200, 200, 0);
        root.divide(root);
        iterate(root);
        tiles = new Texture(Gdx.files.internal("floor_1.png"));
        wallRight = new Texture(Gdx.files.internal("wallRight.png"));
        wallLeft = new Texture(Gdx.files.internal("wallLeft.png"));
        TextureRegion[][] splitTiles = TextureRegion.split(tiles, 32, 32);
        TextureRegion[][] splitWallR = TextureRegion.split(wallRight, 10, 152);
        TextureRegion[][] splitWallL = TextureRegion.split(wallLeft, 10, 152);
        map = new TiledMap();
        MapLayers layers = map.getLayers();
        
        TiledMapTileLayer layerTiles = new TiledMapTileLayer(120, 120, 32, 32);
        TiledMapTileLayer layerWalls = new TiledMapTileLayer(20, 20, 32, 38);
        
        TiledMapTileLayer.Cell cell = new TiledMapTileLayer.Cell();
        cell.setTile(new StaticTiledMapTile(splitTiles[0][0]));
        
        
        System.out.println("Room placement using Bfs");
        int[][] ma = coordinatesFor;
        for (int i = 0; i < 120; i++) {
            for (int j = 0; j < 120; j++) {
                if(ma[j][i] == 1){
                    layerTiles.setCell(i, j, cell);
                }
            }
        }
        for (int[] is : ma) {
            for (int i : is) {
                System.out.print(i);
            }
            System.out.println("");
        }
        
        
        
        
       
        TiledMapTileLayer.Cell cell2 = new TiledMapTileLayer.Cell();
        cell2.setTile(new StaticTiledMapTile(splitWallR[0][0]));
        layerWalls.setCell(3, 0, cell2);
        layerWalls.setCell(3, 2, cell2);
        TiledMapTileLayer.Cell cell3 = new TiledMapTileLayer.Cell();
        cell3.setTile(new StaticTiledMapTile(splitWallL[0][0]));
        layerWalls.setCell(1, 0, cell3);
        layerWalls.setCell(1, 2, cell3);
        layers.add(layerTiles);
        layers.add(layerWalls);
        
        
        
        return map;
    }

    public int[][] generateMap() {
        int[][] coordinatesForTiles = new int[120][120];

        Node root = new Node(0, 0, 100, 100, 0);
        root.divide(root);

        for (Node node : root.nodes) {
            System.out.println("Room: " + node.index + "x " + node.x + " y " + node.y);
        }
        System.out.println("iterate");
        iterate(root);
        // change list before final dl
        ArrayList<Node> nodes = root.getNodes();
        
        for (Node node : nodes) {
            
            for (int i = node.x - 3; i < node.x + 3; i++) {
                for (int j = node.y - 3; j < node.y + 3; j++) {
                    coordinatesForTiles[i][j] = 1;

                }
            }

        }
        return coordinatesForTiles;
    }
    
    public void iterate(Node node) {
        if(node == null){
            return;
        }
        if (!node.visited) {
            node.visited = true;
            System.out.println(node.index +"<- index " + node.x + ":x y:" + node.y);
            
            for (int j = node.y - 5; j < 5 + node.y; j++) {
                for (int i = node.x - 5 ; i < 5  + node.x ; i++) {
                    coordinatesFor[i][j] = 1;
                }
            }
            
            iterate(node.left);
            iterate(node.right);

        }
        

    }
}
