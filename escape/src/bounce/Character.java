package bounce;

import jig.Collision;
import jig.ConvexPolygon;
import jig.ResourceManager;
import jig.Vector;
import org.newdawn.slick.Animation;

public class Character extends Movable {
    private float speed = 0.1f;
    private Vector prevPosition;
    protected boolean isBouncing = false;
    protected int bounceTimer;

    private String moveLeft = EscapeGame.PLAYERBODYWALKLEFT_IMG_RSC,
            moveRight = EscapeGame.PLAYERBODYWALKRIGHT_IMG_RSC,
            moveUp = EscapeGame.PLAYERBODYWALKUP_IMG_RSC,
            moveDown = EscapeGame.PLAYERBODYWALKDOWN_IMG_RSC;
    private String stillLeft = EscapeGame.PLAYERBODYWALKLEFTSTILL_IMG_RSC,
            stillRight = EscapeGame.PLAYERBODYWALKRIGHTSTILL_IMG_RSC,
            stillUp = EscapeGame.PLAYERBODYWALKUPSTILL_IMG_RSC,
            stillDown = EscapeGame.PLAYERBODYWALKDOWNSTILL_IMG_RSC;

    private String currImage, currAnimation, currDir;
    private Animation animation;

    public Character (int x, int y, float speed) {
        super(x,y,0f,0f);
        this.addShape(new ConvexPolygon(32f,32f),
                new Vector(-16f, -16f));
        setVelocity();
        setSpeed(speed);
        prevPosition = getPosition();
    }

    public void update(int delta) {
        prevPosition = getPosition();
        super.update(delta);
    }

    // set velocity to 0
    public void setVelocity() {
        setVelocity(new Vector(0f,0f));
    }

    // set velocity to (vx,vy)
    public void setVelocity(float vx, float vy) {
        setVelocity(new Vector(vx,vy));
    }

    //set velocity to vector
    public void setVelocity(Vector velocity) {
        this.velocity = velocity;
    }

    public Vector getVelocity() {
        return velocity;
    }

    public void setSpeed(float speed) {
        this.speed = speed;
    }

    public void moveStill() {
        if (currImage != null) {
            return;
        }
        currImage = currDir != null ? currDir : stillDown;
        addImage(ResourceManager.getImage(currImage), new Vector(-16f,-16f));
        if (currAnimation != null) {
            removeAnimation(animation);
            currAnimation = null;
            animation = null;
        }
        setVelocity(0f, 0f);
    }

    public void moveLeft() {
        move(moveLeft, stillLeft);
        setVelocity(-speed, 0f);
    }

    public void moveRight() {
        move(moveRight, stillRight);
        setVelocity(speed, 0f);
    }

    public void moveUp() {
        move(moveUp, stillUp);
        setVelocity(0f, -speed);
    }

    public void moveDown() {
        move(moveDown, stillDown);
        setVelocity(0f, speed);
    }

    private void move(String moveDir, String still) {
        if (currImage != null) {
            removeImage(ResourceManager.getImage(currImage));
            currImage = null;
        }
        if (currAnimation != moveDir) {
            if (animation != null) {
                removeAnimation(animation);
            }
            currDir = still;
            currAnimation = moveDir;
            animation = new Animation(ResourceManager.getSpriteSheet(currAnimation,32,32), 100);
            addAnimation(animation, new Vector(-16f,-16f));
        }
    }

    public String getMoveLeft() {
        return moveLeft;
    }

    public void setMoveLeft(String moveLeft) {
        this.moveLeft = moveLeft;
    }

    public String getMoveRight() {
        return moveRight;
    }

    public void setMoveRight(String moveRight) {
        this.moveRight = moveRight;
    }

    public String getMoveUp() {
        return moveUp;
    }

    public void setMoveUp(String moveUp) {
        this.moveUp = moveUp;
    }

    public String getMoveDown() {
        return moveDown;
    }

    public void setMoveDown(String moveDown) {
        this.moveDown = moveDown;
    }

    public String getStillLeft() {
        return stillLeft;
    }

    public void setStillLeft(String stillLeft) {
        this.stillLeft = stillLeft;
    }

    public String getStillRight() {
        return stillRight;
    }

    public void setStillRight(String stillRight) {
        this.stillRight = stillRight;
    }

    public String getStillUp() {
        return stillUp;
    }

    public void setStillUp(String stillUp) {
        this.stillUp = stillUp;
    }

    public String getStillDown() {
        return stillDown;
    }

    public void setStillDown(String stillDown) {
        this.stillDown = stillDown;
    }

    protected void revert() {
        setPosition(prevPosition);
    }

    protected void handleCollision(Collision collision) {
        revert();
        moveStill();
    }

    public void bounce(Vector bounceDir, float bounceFactor) {
        isBouncing = true;

        setVelocity(bounceDir.negate().scale(bounceFactor + 1));
        bounceTimer = 333;
    }
}
