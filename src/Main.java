import java.util.ArrayList;
import java.util.Random;
import java.util.stream.Collectors;

/**
 * Created by michaelplott on 11/7/16.
 */
public class Main {
    static final int SIZE = 10;

    static Room[][] createRoom() {
        Room[][] rooms = new Room[SIZE][SIZE];
        for (int row = 0; row < SIZE; row++) {
            for (int col = 0; col < SIZE; col++) {
                rooms[row][col] = new Room(row, col);
            }
        }
        return rooms;
    }

    static ArrayList<Room> possibleNeightbors(Room[][] rooms, int row, int col) {
        ArrayList<Room> neighbors = new ArrayList<>();
        if (row < SIZE - 1) {
            neighbors.add(rooms[row+1][col]);
        }
        if (row > 0) {
            neighbors.add(rooms[row-1][col]);
        }
        if (col < SIZE - 1) {
            neighbors.add(rooms[row][col+1]);
        }
        if (col > 0) {
            neighbors.add(rooms[row][col-1]);
        }
        neighbors = neighbors.stream()
                .filter(room -> !room.wasVisited)
                .collect(Collectors.toCollection(ArrayList::new));
        return neighbors;
    }

    static Room randomNeighbor(Room[][] rooms, int row, int col) {
        ArrayList<Room> neighbors = possibleNeightbors(rooms, row, col);
        if (neighbors.size() == 0) {
            return null;
        }

        Random r = new Random();
        int index = r.nextInt(neighbors.size());
        return neighbors.get(index);
    }

    static void tearDownWall(Room oldRoom, Room newRoom) {
        if (oldRoom.row < newRoom.row) {
            oldRoom.hasBottom = false;
        }
        else if (oldRoom.row > newRoom.row) {
            newRoom.hasBottom = false;
        }
        else if (oldRoom.col < newRoom.col) {
            oldRoom.hasRight = false;
        }
        else if (oldRoom.col > newRoom.col) {
            newRoom.hasRight = false;
        }
    }

    static boolean createMaze(Room[][] rooms, Room room) {
        room.wasVisited = true;
        Room nextRoom = randomNeighbor(rooms, room.row, room.col);
        if (nextRoom == null) {
            return false;
        }

        tearDownWall(room, nextRoom);

        while(createMaze(rooms, nextRoom));

        return true;
    }

    public static void main(String[] args) {
        Room[][] rooms = createRoom();
        createMaze(rooms, rooms[0][0]);
        for (Room[] row : rooms) {
            System.out.print(" _");
        }
        System.out.println();
        for (Room[] row : rooms) {
            System.out.print("|");
            for (Room room : row) {
                if (room.hasBottom) {
                    System.out.print("_");
                }
                else {
                    System.out.print(" ");
                }
                if (room.hasRight) {
                    System.out.print("|");
                }
                else {
                    System.out.print(" ");
                }
            }
            System.out.println();
        }
    }
}
