package bounce;

import jig.ResourceManager;
import jig.Vector;
import org.lwjgl.Sys;

public class Enemy extends Character {
    private Vector velocity;
    private float speed = 0.05f;
    private int tileX;
    private int tileY;

    public Enemy (int x, int y) {
        super(x,y);
        this.addImageWithBoundingBox(ResourceManager
                .getImage(EscapeGame.PLAYER_IMG_RSC)
                .getScaledCopy(16,16)
        ,new Vector(-8f, -8f));
        velocity = new Vector(0f, 0f);
        tileX = 16 * x;
        tileY = 16 * y;
        setDebug(true);
    }

    public void update(Dijkstras dijkstras, int delta) {
        String dir = dijkstras.getToPlayer(tileX, tileY);

        if (dir == "left") {
            moveLeft();
        } else if (dir == "right") {
            moveRight();
        } else if (dir == "up") {
            moveUp();
        } else if (dir == "down") {
            moveDown();
        } else {
            moveStill();
        }

        translate(velocity.scale(delta));
        tileX = (int) getX() / 16;
        tileY = (int) getY() / 16;
    }

    public void moveStill() {
        velocity = new Vector(0f,0f);
    }

    public void moveLeft() {
        velocity = new Vector(-speed, 0f);
    }

    public void moveRight() {
        velocity = new Vector(speed, 0f);
    }

    public void moveUp() {
        velocity = new Vector(0f, -speed);
    }

    public void moveDown() {
        velocity = new Vector(0f, speed);
    }
}
