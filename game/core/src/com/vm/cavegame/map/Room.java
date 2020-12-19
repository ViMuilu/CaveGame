package vm.cavegame.map;

/**
 * 
 * @author ville
 */
public class Room {

    private int x;
    private int y;
    private int w;
    private int h;

    /**
     *
     * @param x
     * @param y
     */
    public Room(int x, int y) {
        this.x = x;
        this.y = y;
    }

    /**
     *
     * @return
     */
    public int getX() {
        return x;
    }

    /**
     *
     * @return
     */
    public int getY() {
        return y;
    }

    /**
     *
     * @param w
     * @param h
     */
    public void setSize(int w,int h){
        this.w =w;
        this.h = h;
    }

    /**
     *
     * @return
     */
    public int getWidth(){
        return w;
    }

    /**
     *
     * @return
     */
    public int getHeight(){
        return h;
    }

}
