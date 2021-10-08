package bounce;

import jig.ResourceManager;
import jig.Vector;

public class Player extends Character {
    private Vector velocity;

    public Player(int x, int y) {
        super(x,y);
        addImageWithBoundingBox(ResourceManager.getImage(EscapeGame.PLAYER_IMG_RSC)
                .getScaledCopy(16,16));
        velocity = new Vector(0f, 0f);
        setDebug(true);
    }

    public void setVelocity(final Vector v) { this.velocity = v; }

    public void update(int delta) {
        translate(velocity.scale(delta));
    }
}
