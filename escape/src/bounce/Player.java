package bounce;

import jig.Collision;
import jig.Entity;
import jig.ResourceManager;
import jig.Vector;
import org.lwjgl.Sys;

import java.util.ArrayList;
import java.util.Optional;

public class Player extends Character {
    private final int ul = 0,
            um = 1,
            ur = 2,
            ml = 3,
            mr = 4,
            dl = 5,
            dm = 6,
            dr = 7;

    private Vector velocity;
    private Collidable[] surroundingTiles = new Collidable[8];


    public Player(int x, int y) {
        super(x,y);
        addImageWithBoundingBox(ResourceManager.getSpriteSheet(EscapeGame.PLAYERBODY_IMG_RSC, 64, 64)
                .getSubImage(0, 2)
                .getScaledCopy(16,16));
        velocity = new Vector(0f, 0f);
        setDebug(true);
    }

    public void setSurroundingTiles(Collidable[] collidables) {
        for (int i = 0; i < 8; i++) {
            surroundingTiles[i] = collidables[i];
        }
    }

    public void setVelocity(final float vx, final float vy) { this.setVelocity(new Vector(vx, vy)); }

    public void setVelocity(final Vector v) { this.velocity = v; }

    public Vector getVelocity() { return this.velocity; }

    public void update(int delta) {
        translate(velocity.scale(delta));
    }

    public void checkObject(Entity object) {
        Collision collision = this.collides(object);
        if (collision != null) {
            System.out.println("Collision Detected");
            this.handleCollision(collision);
        }
    }

    private void handleCollision(Collision collision) {
        this.translate(collision.getMinPenetration());
        float x = collision.getMinPenetration().getX();
        float y = collision.getMinPenetration().getY();
        if (x != 0) {
            this.setVelocity(0f, this.velocity.getY());
        }
        if (y != 0) {
            this.setVelocity(this.velocity.getX(), 0f);
        }
    }

}
