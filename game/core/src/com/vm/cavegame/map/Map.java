
package com.vm.cavegame.map;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.MapLayers;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.tiles.StaticTiledMapTile;
import com.badlogic.gdx.physics.box2d.World;

public class Map {
    private TiledMap map;
    private Texture tiles;
    private Texture wallRight;
    private Texture wallLeft;
    public TiledMap renderMap(World world){
        
        tiles = new Texture(Gdx.files.internal("floor_1.png"));
        wallRight = new Texture(Gdx.files.internal("wallRight.png"));
        wallLeft =new Texture(Gdx.files.internal("wallLeft.png"));
        TextureRegion[][] splitTiles = TextureRegion.split(tiles, 32, 32);
        TextureRegion[][] splitWallR = TextureRegion.split(wallRight, 10, 152);
        TextureRegion[][] splitWallL = TextureRegion.split(wallLeft, 10, 152);
        map = new TiledMap();
        MapLayers layers = map.getLayers();

        
        TiledMapTileLayer layerTiles = new TiledMapTileLayer(20, 20, 32, 32);
        TiledMapTileLayer layerWalls = new TiledMapTileLayer(20, 20, 32, 38);
        int ty = (int) (Math.random() * splitTiles.length);
        int tx = (int) (Math.random() * splitTiles[ty].length);
        System.out.println("Tiles:"+splitTiles.length);
        TiledMapTileLayer.Cell cell = new TiledMapTileLayer.Cell();
        cell.setTile(new StaticTiledMapTile(splitTiles[0][0]));
        layerTiles.setCell(1, 0, cell);
        layerTiles.setCell(1, 1, cell);
        layerTiles.setCell(1, 2, cell);
        layerTiles.setCell(1, 3, cell);
        layerTiles.setCell(1, 4, cell);
        layerTiles.setCell(2, 0, cell);
        layerTiles.setCell(2, 1, cell);
        layerTiles.setCell(2, 2, cell);
        layerTiles.setCell(2, 3, cell);
        layerTiles.setCell(2, 4, cell);
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
    public String[][] generateMap(int rooms){
        // algo for bsp tree here
        return  new String[0][0];
    }
}
