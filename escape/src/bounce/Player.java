package bounce;

import jig.*;

import org.newdawn.slick.Input;


public class Player extends Character {
    private int health;

    public Player(int x, int y, float speed) {
        super(x,y,speed);

        setMoveDown(EscapeGame.PLAYERBODYWALKDOWN_IMG_RSC);
        setMoveLeft(EscapeGame.PLAYERBODYWALKLEFT_IMG_RSC);
        setMoveRight(EscapeGame.PLAYERBODYWALKRIGHT_IMG_RSC);
        setMoveUp(EscapeGame.PLAYERBODYWALKUP_IMG_RSC);

        setStillDown(EscapeGame.PLAYERBODYWALKDOWNSTILL_IMG_RSC);
        setStillUp(EscapeGame.PLAYERBODYWALKUPSTILL_IMG_RSC);
        setStillRight(EscapeGame.PLAYERBODYWALKRIGHTSTILL_IMG_RSC);
        setStillLeft(EscapeGame.PLAYERBODYWALKLEFTSTILL_IMG_RSC);

        health = 1;
    }

    public void update(Input input, int delta) {
        boolean rightPressed = input.isKeyPressed(Input.KEY_RIGHT),
                leftPressed = input.isKeyPressed(Input.KEY_LEFT),
                upPressed = input.isKeyPressed(Input.KEY_UP),
                downPressed = input.isKeyPressed(Input.KEY_DOWN);
        boolean keyIsDown = input.isKeyDown(Input.KEY_RIGHT)||
                input.isKeyDown(Input.KEY_LEFT)||
                input.isKeyDown(Input.KEY_UP)||
                input.isKeyDown(Input.KEY_DOWN);

        if (rightPressed) {
            moveRight();
        } else if (leftPressed) {
            moveLeft();
        } else if (upPressed) {
            moveUp();
        } else if (downPressed) {
            moveDown();
        } else if (keyIsDown) {
        }else {
            moveStill();
        }

        super.update(delta);
    }

    public boolean checkObject(Entity object) {
        Collision collision = this.collides(object);
        Enemy enemy;
        if (collision != null) {
            try {
                enemy = (Enemy) object;
                this.takeDamage(enemy.getDamage());
            } catch (Exception e) {
                handleCollision(collision);
            } finally {
                return true;
            }
        }
        return false;
    }

    private void takeDamage(int damage) {
        health -= damage;
    }

    public boolean getIsDead() {
        if (health < 0) {
            return true;
        }
        return false;
    }

    public void resetHealth() {
        health = 1;
    }


}
