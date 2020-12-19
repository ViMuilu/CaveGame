package vm.cavegame.map;

import java.util.ArrayList;

import java.util.concurrent.ThreadLocalRandom;

/**
 * Bsp tree algorithms
 * @author ville
 */
public class Node {

    /**
     *
     */
    public int leafX;

    /**
     *
     */
    public int leafY;

    /**
     *
     */
    public int leafHeight;

    /**
     *
     */
    public int leafWidth;

    /**
     *
     */
    public Node left;

    /**
     *
     */
    public Node right;
    private final ArrayList<Room> corridors;
    private final int minLeafSize = 6;
    private int roomWidth;
    private int roomHeight;
    private Room room;
    // used for upscaling coordinates
    private final int scale = 1;

    /**
     *
     * @param x
     * @param y
     * @param height
     * @param width
     */
    public Node(int x, int y, int height, int width) {
        this.leafX = x;
        this.leafY = y;
        this.leafHeight = height;
        this.leafWidth = width;
        left = null;
        right = null;
        corridors = new ArrayList<>();
        room = null;
    }


    /**
     *Splits node into two children
     * @return
     */
    public boolean split() {
        if (left != null || right != null) {
            return false; //allready split
        }
        boolean direction = ThreadLocalRandom.current().nextBoolean();
        int max = (direction ? leafHeight : leafWidth) - minLeafSize;
        if (max - minLeafSize <= minLeafSize) {
            return false; // leaf too small to split
        }
        int split = ThreadLocalRandom.current().nextInt(minLeafSize, max);
        if (direction) {
            //split horizontal
            left = new Node(leafX, leafY, split, leafWidth);
            right = new Node(leafX, leafY + split, leafHeight - split, leafWidth);

        } else {
            //split vertical
            left = new Node(leafX, leafY, leafHeight, split);
            right = new Node(leafX + split, leafY, leafHeight, leafWidth - split);
        }
        return true;
    }


    /**
     * Randomly places room in a node
     */
    public void createRooms() {
        if (left != null || right != null) {
            if (left != null) {
                left.createRooms();
            }
            if (right != null) {
                right.createRooms();
            }
            if (left != null && right != null) {
                createCorridors(left.visitRooms(), right.visitRooms());
            }
        } else {
            roomHeight = ThreadLocalRandom.current().nextInt(minLeafSize / 2, (leafHeight - 2) + 1);
            roomWidth = ThreadLocalRandom.current().nextInt(minLeafSize / 2, (leafWidth - 2) + 1);
            int roomPositionX = ((leafWidth - roomWidth - 1) <= 1) ? 1 : ThreadLocalRandom.current().nextInt(1, (leafWidth - roomWidth - 1) + 1);
            int roomPositionY = ((leafHeight - roomHeight - 1) <= 1) ? 1 : ThreadLocalRandom.current().nextInt(1, (leafHeight - roomHeight - 1) + 1);
            room = new Room(leafX + roomPositionX, leafY + roomPositionY);
            room.setSize(roomWidth, roomHeight);
        }

    }
    /**
     * Iterates through nodes and checks if they  have a room and then returns room
     * @return
     */
    public Room visitRooms() {
        if (room == null) {
            Room leftRoom = null;
            Room rightRoom = null;
            if (left != null) {
                leftRoom = left.visitRooms();
            }
            if (right != null) {
                rightRoom = right.visitRooms();
            }
            if (leftRoom == null && rightRoom == null) {
                return null;
            } else if (rightRoom == null) {
                return leftRoom;
            } else if (leftRoom == null) {
                return rightRoom;
            } else if (Math.random() > 0.5) {
                return rightRoom;
            } else {
                return leftRoom;
            }
        } else {
            return room;
        }

    }

    /**
     * Creates a corridor between two room objects
     * @param left
     * @param right
     */
    public void createCorridors(Room left, Room right) {
        // pick random points from each room
        int randomXInRight = ThreadLocalRandom.current().nextInt(right.getX() + 1, ((right.getWidth()) + (right.getX())));
        int randomYInRight = ThreadLocalRandom.current().nextInt(right.getY() + 1, ((right.getHeight()) + (right.getY())));
        int randomXInLeft = ThreadLocalRandom.current().nextInt(left.getX() + 1, ((left.getWidth()) + (left.getX())));
        int randomYInLeft = ThreadLocalRandom.current().nextInt(left.getY() + 1, ((left.getHeight()) + (left.getY())));
        // checks if rooms are lined up on x or y axis and creates straight corridor to room
        if (randomXInRight < randomXInLeft) {
            if (randomYInRight < randomYInLeft) {
                for (int i = randomYInRight * scale; i <= randomYInLeft * scale; i++) {
                    corridors.add(new Room(randomXInRight * scale, i));
                }
                for (int j = randomXInRight * scale; j <= randomXInLeft * scale; j++) {
                    corridors.add(new Room(j, randomYInLeft * scale));
                }
            } else if (randomYInRight > randomYInLeft) {
                for (int i = randomYInRight * scale; i >= randomYInLeft * scale; i--) {
                    corridors.add(new Room(randomXInLeft * scale, i));
                }
                for (int j = randomXInRight * scale; j <= randomXInLeft * scale; j++) {
                    corridors.add(new Room(j, randomYInRight * scale));
                }
            } else {
                for (int i = randomXInRight * scale; i <= randomXInLeft * scale; i++) {
                    corridors.add(new Room(i, randomYInLeft * scale));
                }
            }
        } else if (randomXInRight > randomXInLeft) {
            if (randomYInRight < randomYInLeft) {
                for (int i = randomYInRight * scale; i <= randomYInLeft * scale; i++) {
                    corridors.add(new Room(randomXInRight * scale, i));
                }
                for (int j = randomXInRight * scale; j >= randomXInLeft * scale; j--) {
                    corridors.add(new Room(j, randomYInLeft * scale));
                }
            } else if (randomYInRight > randomYInLeft) {
                for (int i = randomYInRight * scale; i >= randomYInLeft * scale; i--) {
                    corridors.add(new Room(randomXInLeft * scale, i));
                }
                for (int j = randomXInLeft * scale; j <= randomXInRight * scale; j++) {
                    corridors.add(new Room(j, randomYInRight * scale));
                }
            } else {
                for (int i = randomXInRight * scale; i >= randomXInLeft * scale; i--) {
                    corridors.add(new Room(i, randomYInLeft * scale));
                }
            }
        } else {
            if (randomYInRight < randomYInLeft) {
                for (int i = randomYInRight * scale; i <= randomYInLeft * scale; i++) {
                    corridors.add(new Room(randomXInRight * scale, i));
                }
            } else if (randomYInRight > randomYInLeft) {
                for (int i = randomYInLeft * scale; i <= randomYInRight * scale; i++) {
                    corridors.add(new Room(randomXInRight * scale, i));
                }
            }
        }
    }

    /**
     *
     * @return
     */
    public ArrayList<Room> getCorridors() {
        return corridors;
    }

    /**
     *
     * @return
     */
    public Room getRoom() {
        return room;
    }

}
