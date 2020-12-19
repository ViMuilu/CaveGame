/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2D;
import com.badlogic.gdx.physics.box2d.World;
import java.util.ArrayList;
import vm.cavegame.map.Map;
import org.junit.After;
import org.junit.AfterClass;
import static org.junit.Assert.assertEquals;
import org.junit.Before;
import org.junit.Test;
import vm.cavegame.MainGame;
import vm.cavegame.map.Room;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.badlogic.gdx.tools.texturepacker.TexturePacker;

/**
 *
 * @author ville
 */
public class MapTests {

    private int[][] mapTiles;
    private Map map;
    private ArrayList<Room> rooms;
    private World world;

    /**
     *
     */
    @Before
    public void setUp() {        
        map = new Map();

        world = new World(new Vector2(0f, 0f), true);
        map.setMapTextures(world);
        mapTiles = map.coordinatesForMapTiles;
        rooms = map.rooms;
    }

    /**
     * Iterates through a list of rooms and checks if they are present in mapTiles
     */
    @Test
    public void checkThatRoomsAreGenerated() {
        int amountOfRoomsNotDrawn = 0;
        for (Room room : rooms) {
            if (mapTiles[room.getX()][room.getY()] != 1) {
                amountOfRoomsNotDrawn++;
            }

        }
        System.out.println(amountOfRoomsNotDrawn);
        assertEquals("Not all rooms were drawn",false, amountOfRoomsNotDrawn != 0 );
    }

    /**
     * Iterates over the edges of a room and checks if there are corridors
     */
    @Test
    public void checkThatRoomsHaveCorridors() {
        boolean amountOfRoomsWithoutCorridors = false;
        for (Room room : rooms) {
            // checks if left and right sides of room has a corridor
            // -1 because rooms are genrated  i<room.getHeight();
            for (int i = room.getY(); i < room.getY() + room.getHeight(); i++) {
                amountOfRoomsWithoutCorridors = true;
                if (mapTiles[room.getX() - 1][i] == 1) {
                    break;
                }
                if (mapTiles[room.getX() + room.getWidth()-1][i] == 1) {
                    break;
                }
                amountOfRoomsWithoutCorridors = false;
            }
            // checks if Top and bottom sides of room has a corridor
            for (int i = room.getX(); i < room.getX() + room.getWidth(); i++) {
                amountOfRoomsWithoutCorridors = true;
                if (mapTiles[i][room.getY() - 1] == 1) {
                    break;
                }
                if (mapTiles[i][room.getY() + room.getHeight()-1] == 1) {
                    break;
                }
                amountOfRoomsWithoutCorridors = false;
            }

        }
        assertEquals("Not all rooms have corridors",true ,amountOfRoomsWithoutCorridors );
    }

}
