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
        super(x * 16,y * 16);
        this.minX = x * 16;
        this.maxX = x * 16 + 16;
        this.minY = y * 16;
        this.maxY = y * 16 + 16;

        this.tileX = x;
        this.tileY = y;

        addImageWithBoundingBox(
                ResourceManager
                        .getImage(EscapeGame.PLAYER_IMG_RSC)
                        .getScaledCopy(16,16),
                new Vector(8f, 8f));

        setDebug(true);
    }

    public int getTileX() { return this.tileX; }

    public int getTileY() { return this.tileY; }
}
