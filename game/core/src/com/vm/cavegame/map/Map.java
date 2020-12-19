package vm.cavegame.map;

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
import java.util.ArrayList;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Generates a map
 * @author ville
 */
public class Map {

    private TiledMap map;
    private Texture tiles;
    private Texture wallRight;
    private Texture wallLeft;

    /**
     *
     */
    public int[][] coordinatesForMapTiles;
    private float playerX;
    private float playerY;
    private Body body;
    int highest = 0;

    /**
     *
     */
    public ArrayList<Room> rooms;

    /**
     * generates and sets map texture to a world
     * @param world
     * @return
     */
    public TiledMap setMapTextures(World world) {
        coordinatesForMapTiles = new int[80][80];
//         gets textures and turns them into texture region which are then turned placed on layers
//        tiles = new Texture(Gdx.files.internal("floor_1.png"));
//        Texture til = new Texture(Gdx.files.internal("floor_2.png"));
//        wallRight = new Texture(Gdx.files.internal("wallRight.png"));
//        wallLeft = new Texture(Gdx.files.internal("wallLeft.png"));
//        TextureRegion[][] splitTiles = TextureRegion.split(tiles, 32, 32);
//        TextureRegion[][] splitTi = TextureRegion.split(til, 32, 32);
//        TextureRegion[][] splitWallR = TextureRegion.split(wallRight, 10, 152);
//        TextureRegion[][] splitWallL = TextureRegion.split(wallLeft, 10, 152);
        map = new TiledMap();
//        MapLayers layers = map.getLayers();
//        TiledMapTileLayer layerTiles = new TiledMapTileLayer(120, 120, 32, 32);
//        TiledMapTileLayer layerWalls = new TiledMapTileLayer(120, 120, 32, 32);
//        TiledMapTileLayer.Cell cell = new TiledMapTileLayer.Cell();
//        cell.setTile(new StaticTiledMapTile(splitTiles[0][0]));
//        TiledMapTileLayer.Cell cell2 = new TiledMapTileLayer.Cell();
//        cell2.setTile(new StaticTiledMapTile(splitWallR[0][0]));
//        TiledMapTileLayer.Cell cell3 = new TiledMapTileLayer.Cell();
//        cell3.setTile(new StaticTiledMapTile(splitWallL[0][0]));
//        TiledMapTileLayer.Cell cell4 = new TiledMapTileLayer.Cell();
//        cell4.setTile(new StaticTiledMapTile(splitTi[0][0]));
        Node root = new Node(0, 0, 60, 60);
        generateMap(root);
        // sets map tiles
//        int[][] ma = coordinatesForMapTiles;
//        for (int i = 0; i < 60; i++) {
//            for (int j = 0; j < 60; j++) {
//                if (ma[j][i] == 1 || ma[j][i] == 6 || ma[j][i] == 7) {
//                    layerTiles.setCell(i, j, cell);
//                }
//                if (ma[j][i] == 2 || ma[j][i] == 3 || ma[j][i] == 4 || ma[j][i] == 5) {
//                    layerWalls.setCell(i, j, cell4);
//                    BodyDef bodyDef = new BodyDef();
//                    bodyDef.type = BodyDef.BodyType.StaticBody;
//                    bodyDef.position.set((i) / 3f, (j) / 3f);
//                    body = world.createBody(bodyDef);
//                    PolygonShape shape = new PolygonShape();
//                    shape.setAsBox(32 / 2f / 100f, 32 / 2f / 100f);
//                    FixtureDef fixtureDef = new FixtureDef();
//                    fixtureDef.shape = shape;
//                    fixtureDef.density = 0f;
//                    body.createFixture(fixtureDef);
//                    shape.dispose();
//                }
//            }
//        }
//        layers.add(layerTiles);
//        layers.add(layerWalls);

        return map;
    }
    private final int scale = 1;

    /**
     *Generates a bsp tree
     * @param root
     */
    public void generateMap(Node root) {
        int maxSize = 10;
        CopyOnWriteArrayList<Node> nodeList = new CopyOnWriteArrayList<>();
        nodeList.add(root);
        boolean split = true;
        // iterates through all nodes until they are correctly sized
        // bsp algo starts here
        while (split) {
            split = false;
            for (Node node : nodeList) {
                if (node.left == null && node.right == null) {
                    if (node.leafHeight > maxSize || node.leafWidth > maxSize || Math.random() > 0.25) {
                        if (node.split()) {
                            split = true;
                            nodeList.add(node.left);
                            nodeList.add(node.right);
                        }
                    }

                }
            }
        }
        // bsp algo ends here
        root.createRooms();
        rooms = new ArrayList<>();
        for (Node n : nodeList) {
            Room room = n.getRoom();
            // adds room cordinates to map tiles
            if (room != null) {
                if (room.getHeight() != 0 || room.getWidth() != 0) {
                    rooms.add(room);
                }
                for (int i = 0; i < room.getHeight(); i++) {
                    for (int j = 0; j < room.getWidth(); j++) {
                        coordinatesForMapTiles[j + room.getX() * scale][i + room.getY() * scale] = 1;
                    }
                }
            }
            if (n.getCorridors() != null && n.getCorridors().size() > 0) {
                for (Room corridor : n.getCorridors()) {
                    coordinatesForMapTiles[corridor.getX()][corridor.getY()] = 1;

                }
            }
        }
        int randomIndex = ThreadLocalRandom.current().nextInt(1, rooms.size());
        Room room = rooms.get(randomIndex);
        playerX = ThreadLocalRandom.current().nextInt(room.getX(), room.getWidth() + room.getX()) * 5;
        playerY = ThreadLocalRandom.current().nextInt(room.getY(), room.getHeight() + room.getY()) * 5;
    }

    /**
     *
     * @return
     */
    public float getPlayerX() {
        return playerX;
    }

    /**
     *
     * @return
     */
    public float getPlayerY() {
        return playerY;
    }
}
