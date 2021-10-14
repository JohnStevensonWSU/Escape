package bounce;

import jig.*;
import org.lwjgl.Sys;
import org.newdawn.slick.Animation;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SpriteSheet;

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

    private Animation moveLeft, moveRight, moveUp, moveDown;
    private String currAnimation;
    private Animation animation;
    private String currImage;
    private Image still = ResourceManager.getImage(EscapeGame.PLAYERBODYWALKDOWNSTILL_IMG_RSC);
    private int lastPressed;

    public Player(int x, int y) {
        super(x,y);
        this.addAnimations();
        this.currAnimation = null;
        this.currImage = "";
        this.addShape(new ConvexPolygon(16f,16f),
                new Vector(8f, 8f));
        velocity = new Vector(0f, 0f);
        setDebug(true);
        moveStill();
    }

    public void setSurroundingTiles(Collidable[] collidables) {
        for (int i = 0; i < 8; i++) {
            surroundingTiles[i] = collidables[i];
        }
    }

    private void addAnimations() {
        moveDown = new Animation(
                new SpriteSheet(ResourceManager.
                        getImage(EscapeGame.PLAYERBODYWALKDOWN_IMG_RSC),
                        64,
                        64),
                100);
        moveLeft = new Animation(
                new SpriteSheet(ResourceManager.
                        getImage(EscapeGame.PLAYERBODYWALKLEFT_IMG_RSC),
                        64,
                        64),
                100);
        moveRight = new Animation(
                new SpriteSheet(ResourceManager.
                        getImage(EscapeGame.PLAYERBODYWALKRIGHT_IMG_RSC),
                        64,
                        64),
                100);
        moveUp = new Animation(
                new SpriteSheet(ResourceManager.
                        getImage(EscapeGame.PLAYERBODYWALKUP_IMG_RSC),
                        64,
                        64),
                100);
    }

    public void setVelocity(final float vx, final float vy) { this.setVelocity(new Vector(vx, vy)); }

    public void setVelocity(final Vector v) { this.velocity = v; }

    public Vector getVelocity() { return this.velocity; }

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

    public void moveStill() {
        if (this.currImage == EscapeGame.PLAYERBODYWALKDOWNSTILL_IMG_RSC) {
            return;
        }
        this.currImage = EscapeGame.PLAYERBODYWALKDOWNSTILL_IMG_RSC;
        this.addImage(ResourceManager.getImage(this.currImage), new Vector(8f,8f));
        if (this.currAnimation != null) {
            this.removeAnimation(this.animation);
            this.currAnimation = null;
            this.animation = null;
        }
        setVelocity(0f, 0f);
    }

    public void moveLeft() {
        if (this.currImage != null) {
            this.removeImage(ResourceManager.getImage(this.currImage));
            this.currImage = null;
        }
        if (this.currAnimation != EscapeGame.PLAYERBODYWALKLEFT_IMG_RSC) {
            if (this.animation != null) {
                this.removeAnimation(this.animation);
            }
            this.currAnimation = EscapeGame.PLAYERBODYWALKLEFT_IMG_RSC;
            this.animation = new Animation(ResourceManager.getSpriteSheet(this.currAnimation,64,64), 100);
            this.addAnimation(this.animation, new Vector(8f,8f));
        }

        this.setVelocity(-0.1f, 0f);
    }

    public void moveRight() {
        if (this.currImage != null) {
            this.removeImage(ResourceManager.getImage(this.currImage));
            this.currImage = null;
        }
        if (this.currAnimation != EscapeGame.PLAYERBODYWALKRIGHT_IMG_RSC) {
            if (this.animation != null) {
                this.removeAnimation(this.animation);
            }
            this.currAnimation = EscapeGame.PLAYERBODYWALKRIGHT_IMG_RSC;
            this.animation = new Animation(ResourceManager.getSpriteSheet(this.currAnimation,64,64), 100);
            this.addAnimation(this.animation, new Vector(8f,8f));
        }
        this.setVelocity(0.1f, 0f);
    }

    public void moveUp() {
        if (this.currImage != null) {
            this.removeImage(ResourceManager.getImage(this.currImage));
            this.currImage = null;
        }
        if (this.currAnimation != EscapeGame.PLAYERBODYWALKUP_IMG_RSC) {
            if (this.animation != null) {
                this.removeAnimation(this.animation);
            }
            this.currAnimation = EscapeGame.PLAYERBODYWALKUP_IMG_RSC;
            this.animation = new Animation(ResourceManager.getSpriteSheet(this.currAnimation,64,64), 100);
            this.addAnimation(this.animation, new Vector(8f,8f));
        }
        this.setVelocity(0f, -0.1f);
    }

    public void moveDown() {
        if (this.currImage != null) {
            this.removeImage(ResourceManager.getImage(this.currImage));
            this.currImage = null;
        }
        if (this.currAnimation != EscapeGame.PLAYERBODYWALKDOWN_IMG_RSC) {
                if (this.animation != null) {
                    this.removeAnimation(this.animation);
                }
                this.currAnimation = EscapeGame.PLAYERBODYWALKDOWN_IMG_RSC;
                this.animation = new Animation(ResourceManager.getSpriteSheet(this.currAnimation,64,64), 100);
                this.addAnimation(this.animation, new Vector(8f,8f));
            }
        this.setVelocity(0f, 0.1f);
    }
}
