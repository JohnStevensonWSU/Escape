package bounce;

import jig.ConvexPolygon;
import jig.ResourceManager;
import jig.Vector;

public class Collidable extends Foreground {
    private final float minX;
    private final float maxX;
    private final float minY;


    private final float maxY;

    private final int tileX;
    private final int tileY;

    public Collidable (int x, int y) {
        super(x * 32,y * 32);
        this.minX = x * 32;
        this.maxX = x * 32 + 32;
        this.minY = y * 32;
        this.maxY = y * 32 + 32;

        this.tileX = x;
        this.tileY = y;

        addImageWithBoundingBox(
                ResourceManager
                        .getImage(EscapeGame.PLAYER_IMG_RSC)
                        .getScaledCopy(32,32),
                new Vector(16f, 16f));

    }

    public int getTileX() { return this.tileX; }

    public int getTileY() { return this.tileY; }

    public float getMinX() {
        return minX;
    }

    public float getMaxX() {
        return maxX;
    }

    public float getMinY() {
        return minY;
    }

    public float getMaxY() {
        return maxY;
    }

    public void add(int i, int j) {
        addImageWithBoundingBox(ResourceManager
                .getImage(EscapeGame.PLAYER_IMG_RSC)
                .getScaledCopy(16, 16),
                new Vector(8 + 16 * i, 8 + 16 * j));
    }
}
