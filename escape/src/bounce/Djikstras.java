package bounce;

import java.util.ArrayList;

public class Djikstras {
    int[][] array;
    int px, py;

    public Djikstras(int width, int height, int px, int py, ArrayList<Collidable> collidables) {
        array = new int[width][height];
        array[px][py] = -1;
        this.px = px;
        this.py = py;
        this.check(px ,py, 1);
    }

    private void check(int x, int y, int value) {
        if (this.array[x][y] == -1) {
            return;
        }
        if (this.array[x][y] == 0 || this.array[x][y] > value) {
           this.array[x][y] = value;
            check(x - 1, y, value + 1);
            check(x + 1, y, value + 1);
            check(x, y - 1, value + 1);
            check(x, y + 1, value + 1);
        }
        return;
    }
}
