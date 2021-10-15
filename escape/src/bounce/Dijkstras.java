package bounce;

import java.util.Arrays;
import java.util.Collections;
import java.util.Dictionary;

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

    private void dijkstras(int x, int y, int value) {
        // tile is outside of map
        if (x < 0 || y < 0 || x > width || y > height) {
            return;
        }

        // tile contains terrain
        if (collidables[x][y] != null) {
            dijkstras[x][y] = 2147483647;
            return;
        }

        if (dijkstras[x][y] == 0 || dijkstras[x][y] > value) {
            dijkstras[x][y] = value;
            dijkstras(x - 1, y, value + 1);
            dijkstras(x + 1, y, value + 1);
            dijkstras(x, y - 1, value + 1);
            dijkstras(x, y + 1, value + 1);
        }
    }

    public void update(int px, int py) {
        init(px, py);
    }

    public int getValue(int x, int y) {
        try {
            return dijkstras[x][y];
        } catch (Exception e) {
            return 2147483647;
        }
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
        dijkstras(px - 1, py, 1);
        dijkstras(px + 1, py, 1);
        dijkstras(px, py - 1, 1);
        dijkstras(px, py + 1, 1);
        dijkstras[px][py] = 0;
    }

    public String getToPlayer(int x, int y) {
        int[] dirs = new int[4];
        int min = 2147483647;

        dirs[0] = getValue(x - 1, y);
        dirs[1] = getValue(x + 1, y);
        dirs[2] = getValue(x, y + 1);
        dirs[3] = getValue(x, y - 1);

        for (int i : dirs) {
            if (i < min) {
                min = i;
            }
        }

        if (dirs[0] == min) {
            System.out.println("left");
            return "left";
        } else if (dirs[1] == min) {
            System.out.println("right");
            return "right";
        } else if (dirs[2] == min) {
            System.out.println("down");
            return "down";
        } else if (dirs[3] == min) {
            System.out.println("up");
            return "up";
        } else {
            return "";
        }
    }
}
