package bounce;

import java.util.ArrayList;

public class Dijkstras {
    private int[][] dijkstras;
    private int px, py;
    private final int width;
    private final int height;
    private final Collidable[][] collidables;

    public Dijkstras(int width, int height, int px, int py, Collidable[][] collidables) {
        this.width = width;
        this.height = height;
        this.collidables = collidables;
        init(px, py);
    }

    private void check(int x, int y, int value) {
        // tile is outside of map
        if (x < 0 || y < 0 || x > width || y > height) {
            return;
        }

        // tile contains terrain
        if (collidables[x][y] != null) {
            dijkstras[x][y] = -1;
            return;
        }

        if (dijkstras[x][y] == 0 || dijkstras[x][y] > value) {
            dijkstras[x][y] = value;
            check(x - 1, y, value + 1);
            check(x + 1, y, value + 1);
            check(x, y - 1, value + 1);
            check(x, y + 1, value + 1);
        }
    }

    public void update(int px, int py) {
        init(px, py);
    }

    public int getValue(int x, int y) {
        return dijkstras[x][y];
    }

    public void setPx(int px) {
        this.px = px;
    }

    public void setPy(int py) {
        this.py = py;
    }

    public void init(int px, int py) {
        dijkstras = new int[width][height];
        dijkstras[px][py] = -1;
        this.px = px;
        this.py = py;
        check(px - 1, py, 1);
        check(px + 1, py, 1);
        check(px, py - 1, 1);
        check(px, py + 1, 1);
        dijkstras[px][py] = 0;
    }
}
