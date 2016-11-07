/**
 * Created by michaelplott on 11/7/16.
 */
public class Room {
    int row;
    int col;
    boolean wasVisited = false;
    boolean hasRight = true;
    boolean hasBottom = true;

    public Room(int row, int col) {
        this.row = row;
        this.col = col;
    }
}
