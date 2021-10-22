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
        if (x < 0 || y < 0 || x >= width || y >= height) {
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

    public String getToPlayer(Enemy enemy) {
        int x = (int) (enemy.getCoarseGrainedMaxX()) / 32;
        int y = (int) (enemy.getCoarseGrainedMaxY()) / 32;
        int xIndex = 0, yIndex = 0;

        int[][] dirs = new int[3][3];
        int min = 2147483647;

        dirs[0][0] = getValue(x - 1, y - 1);
        dirs[0][1] = getValue(x - 1, y);
        dirs[0][2] = getValue(x - 1, y + 1);
        dirs[1][0] = getValue(x , y - 1);
        dirs[1][1] = getValue(x, y);
        dirs[1][2] = getValue(x, y + 1);
        dirs[2][0] = getValue(x + 1, y - 1);
        dirs[2][1] = getValue(x + 1, y);
        dirs[2][2] = getValue(x + 1, y + 1);

        if (dirs[0][1] < min) {
            min = dirs[0][1];
            xIndex = 0;
            yIndex = 1;
        }
        if (dirs[2][1] < min) {
            min = dirs[2][1];
            xIndex = 2;
            yIndex = 1;
        }
        if (dirs[1][0] < min) {
            min = dirs[1][0];
            xIndex = 1;
            yIndex = 0;
        }
        if (dirs[1][2] < min) {
            min = dirs[1][2];
            xIndex = 1;
            yIndex = 2;
        }

        System.out.println("X: " + String.valueOf(xIndex) + " Y: " + String.valueOf(yIndex));

        if (min > 15) {
            return "stop";
        }

        if (xIndex == 1 && yIndex == 0) {
            if (dirs[0][0] == 2147483647) {
                if (enemy.collides(collidables[x-1][y-1]) != null) {
                    return "right";
                }
            }
            if (dirs[2][0] == 2147483647) {
                if (enemy.collides(collidables[x+1][y-1]) != null) {
                    return "left";
                }
            }
            return "up";
        }
        if (xIndex == 0 && yIndex == 1) {
            if (dirs[0][0] == 2147483647) {
                if (enemy.collides(collidables[x-1][y-1]) != null) {
                    return "down";
                }
            }
            if (dirs[0][2] == 2147483647) {
                if (enemy.collides(collidables[x-1][y+1]) != null) {
                    return "up";
                }
            }
            return "left";
        }
        if (xIndex == 1 && yIndex == 2) {
            if (dirs[0][2] == 2147483647) {
                if (enemy.collides(collidables[x-1][y+1]) != null) {
                    return "right";
                }
            }
            if (dirs[2][2] == 2147483647) {
                if (enemy.collides(collidables[x+1][y+1]) != null) {
                    return "left";
                }
            }
            return "down";
        }
        if (xIndex == 2 && yIndex == 1) {
            if (dirs[2][0] == 2147483647) {
                if (enemy.collides(collidables[x+1][y-1]) != null) {
                    return "down";
                }
            }
            if (dirs[2][2] == 2147483647) {
                if (enemy.collides(collidables[x+1][y+1]) != null) {
                    return "up";
                }
            }
            return "right";
        }
//        for (int i = 0; i < 3; i++) {
//            for (int j = 0; j < 3; j++) {
//                if (dirs[i][j] == min) {
//                    xIndex = i;
//                    yIndex = j;
//                    break;
//                }
//            }
//        }

        return "";
//        if (dirs[0] == min) {
//            if (dirs[2] == 2147483647) {
//                return "up";
//            } else if (dirs[3] == 2147483647) {
//                return "down";
//            }
//            return "left";
//        } else if (dirs[1] == min) {
//            if (dirs[2] == 2147483647) {
//                return "up";
//            } else if (dirs[3] == 2147483647) {
//                return "down";
//            }
//            return "right";
//        } else if (dirs[2] == min) {
//            if (dirs[0] == 2147483647) {
//                return "right";
//            } else if (dirs[1] == 2147483647) {
//                return "left";
//            }
//            return "down";
//        } else if (dirs[3] == min) {
//            if (dirs[0] == 2147483647) {
//                return "right";
//            } else if (dirs[1] == 2147483647) {
//                return "left";
//            }
//            return "up";
//        } else {
//            return "";
//        }
    }
}
