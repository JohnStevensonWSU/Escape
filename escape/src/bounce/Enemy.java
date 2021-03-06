package bounce;

import jig.Collision;
import jig.Entity;
import jig.Vector;

public class Enemy extends Character {
    private int tileX;
    private int tileY;
    private int damage;

    public Enemy (int x, int y, float speed) {
        super(x,y,speed);

        setMoveDown(EscapeGame.ENEMYBODYWALKDOWN_IMG_RSC);
        setMoveLeft(EscapeGame.ENEMYBODYWALKLEFT_IMG_RSC);
        setMoveRight(EscapeGame.ENEMYBODYWALKRIGHT_IMG_RSC);
        setMoveUp(EscapeGame.ENEMYBODYWALKUP_IMG_RSC);

        setStillDown(EscapeGame.ENEMYBODYWALKDOWNSTILL_IMG_RSC);
        setStillUp(EscapeGame.ENEMYBODYWALKUPSTILL_IMG_RSC);
        setStillRight(EscapeGame.ENEMYBODYWALKRIGHTSTILL_IMG_RSC);
        setStillLeft(EscapeGame.ENEMYBODYWALKLEFTSTILL_IMG_RSC);

        tileX = x / 32;
        tileY = y / 32;
        damage = 1;
    }

    public void update(Dijkstras dijkstras, int delta) {
        if (isBouncing) {
            bounceTimer -= delta;
            if (bounceTimer < 0) {
                isBouncing = false;
            }
            super.update(delta);
            return;
        }

        String dir = dijkstras.getToPlayer(this);

        if (dir == "stop") {
            moveStill();
        }

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

        super.update(delta);
        tileX = (int) getX() / 32;
        tileY = (int) getY() / 32;
    }

    public int getDamage() {
        if (isBouncing) {
            return 0;
        }
        return damage;
    }

}
