package com.vm.cavegame.map;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.MapLayers;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.tiles.StaticTiledMapTile;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;

public class Map {

    private TiledMap map;
    private Texture tiles;
    private Texture wallRight;
    private Texture wallLeft;
    private int[][] coordinatesForMapTiles = new int[240][240];
    private Body body;

    public TiledMap renderMap(World world) {

        Node root = new Node(10, 10, 200, 240, 0);
        root.divide(root);
        iterate(root);
        tiles = new Texture(Gdx.files.internal("floor_1.png"));
        Texture til = new Texture(Gdx.files.internal("floor_2.png"));
        wallRight = new Texture(Gdx.files.internal("wallRight.png"));
        wallLeft = new Texture(Gdx.files.internal("wallLeft.png"));
        TextureRegion[][] splitTiles = TextureRegion.split(tiles, 32, 32);
        TextureRegion[][] splitTi = TextureRegion.split(til, 32, 32);
        TextureRegion[][] splitWallR = TextureRegion.split(wallRight, 10, 152);
        TextureRegion[][] splitWallL = TextureRegion.split(wallLeft, 10, 152);
        map = new TiledMap();
        MapLayers layers = map.getLayers();

        TiledMapTileLayer layerTiles = new TiledMapTileLayer(120, 120, 32, 32);
        TiledMapTileLayer layerWalls = new TiledMapTileLayer(120, 120, 32, 32);
        
        TiledMapTileLayer.Cell cell = new TiledMapTileLayer.Cell();
        cell.setTile(new StaticTiledMapTile(splitTiles[0][0]));
        System.out.println(cell.toString());
        TiledMapTileLayer.Cell cell2 = new TiledMapTileLayer.Cell();
        cell2.setTile(new StaticTiledMapTile(splitWallR[0][0]));

        TiledMapTileLayer.Cell cell3 = new TiledMapTileLayer.Cell();
        cell3.setTile(new StaticTiledMapTile(splitWallL[0][0]));
        TiledMapTileLayer.Cell cell4 = new TiledMapTileLayer.Cell();
        cell4.setTile(new StaticTiledMapTile(splitTi[0][0]));
        System.out.println("Room placement using Bfs");
        int[][] ma = coordinatesForMapTiles;
        for (int i = 0; i < 120; i++) {
            for (int j = 0; j < 120; j++) {
                if (ma[j][i] == 1) {
                    layerTiles.setCell(i, j, cell);
                }
                if (ma[j][i] == 2 || ma[j][i] == 3 || ma[j][i] == 4 || ma[j][i] == 5) {
                    layerWalls.setCell(i, j, cell4);
                    BodyDef bodyDef = new BodyDef();
                    bodyDef.type = BodyDef.BodyType.StaticBody;
                    bodyDef.position.set((i) / 3f, (j) / 3f);
                    body = world.createBody(bodyDef);
                    PolygonShape shape = new PolygonShape();
                    shape.setAsBox(32/2f/100f, 32/2f/100f);
                    FixtureDef fixtureDef = new FixtureDef();
                    fixtureDef.shape = shape;
                    fixtureDef.density = 0f;

                    body.createFixture(fixtureDef);
                    shape.dispose();

                }
            }
        }
        for (int[] is : ma) {
            for (int i : is) {
                System.out.print(i);
            }
            System.out.println("");
        }
        layers.add(layerTiles);
        layers.add(layerWalls);
        return map;
    }

    public void iterate(Node node) {
        if (node == null) {
            return;
        }
        if (!node.visited) {
            node.visited = true;
            System.out.println(node.index + "<- index " + node.x + ":x y:" + node.y);
            // creates rooms
            for (int j = node.y - 5; j < 5 + node.y; j++) {

                for (int i = node.x - 5; i < 5 + node.x; i++) {
                    coordinatesForMapTiles[i][j] = 1;
                    // sets coordinates for walls
                    if (j == node.y - 5) {
                        coordinatesForMapTiles[i][j - 1] = 2;
                    }
                    if (j == 4 + node.y) {
                        coordinatesForMapTiles[i][j + 1] = 3;
                    }
                    if (i == node.x - 5) {
                        coordinatesForMapTiles[i - 1][j] = 4;
                    }
                    if (i == node.x + 4) {
                        coordinatesForMapTiles[i + 1][j] = 5;
                    }

                }
            }

            iterate(node.left);
            iterate(node.right);

        }
        // creates corridors
        // note remember to create similiar method when node.left is added  
        if (node.right != null && node.right.visited) {
            if (node.y == node.right.y) {
                for (int j = node.x; j < node.right.x; j++) {

                    coordinatesForMapTiles[j][node.y] = 1;
                    coordinatesForMapTiles[j][node.y - 1] = 1;
                    if (coordinatesForMapTiles[j][node.y - 2] != 1 && coordinatesForMapTiles[j][node.y + 1] != 1) {
                        coordinatesForMapTiles[j][node.y - 2] = 2;
                        coordinatesForMapTiles[j][node.y + 1] = 3;
                    }
                }
            } else if (node.x == node.right.x) {
                for (int j = node.y; j < node.right.y; j++) {

                    coordinatesForMapTiles[node.x - 1][j] = 1;
                    coordinatesForMapTiles[node.x][j] = 1;

                    if (coordinatesForMapTiles[node.x - 2][j] != 1 && coordinatesForMapTiles[node.x + 1][j] != 1) {
                        coordinatesForMapTiles[node.x - 2][j] = 4;
                        coordinatesForMapTiles[node.x + 1][j] = 5;
                    }
                }
            }
        }
    }

}
