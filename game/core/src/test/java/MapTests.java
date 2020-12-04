/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import vm.cavegame.map.Map;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

public class MapTests {

    int[][] mapTiles;

    public MapTests() {
    }

    @Before
    public void setUp() {
        Map map = new Map();
        map.renderMap(new World(new Vector2(0f, 0f), true));
        mapTiles = map.coordinatesForMapTiles;
    }

    @Test
    public void checkThatRoomsAreConnected() {
        int x = 0;
        int y = 0;
        for (int[] mapTile : mapTiles) {
            x++;
            for (int i : mapTile) {
                y++;
                if (i == 6) {
                    break;
                }
            }
        }

    }
}
