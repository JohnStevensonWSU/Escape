package bounce;

import jig.ResourceManager;
import jig.Vector;

public class Enemy extends Character {
    private int timer;

    public Enemy (int x, int y) {
        super(x,y);
        this.addImageWithBoundingBox(ResourceManager
                .getImage(EscapeGame.PLAYER_IMG_RSC)
                .getScaledCopy(16,16)
        ,new Vector(8f, 8f));
    }
}
