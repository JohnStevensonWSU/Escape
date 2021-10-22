package bounce;

import jig.*;

import org.newdawn.slick.Input;

import java.util.Stack;


public class Player extends Character {
    private int health;
    private int initialHealth;
    private Stack<Integer> keyStack = new Stack<Integer>();
    private Collidable[][] collidables;

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

        health = 2;
        initialHealth = health;
    }

    public void setCollidables(Collidable[][] collidables) {
        this.collidables = collidables;
    }

    public void update(Input input, int delta) {
        int x = (int) this.getX() / 32, y = (int) this.getY() / 32;
        Integer tos;
        if (isBouncing) {
            System.out.println("Bouncing");
            bounceTimer -= delta;
            if (bounceTimer < 0) {
                isBouncing = false;
                setVelocity(0,0);
            }
            super.update(delta);
            return;
        }



        if (input.isKeyPressed(Input.KEY_DOWN)) {
            keyStack.push(Input.KEY_DOWN);
        }
        if (input.isKeyPressed(Input.KEY_UP)) {
            keyStack.push(Input.KEY_UP);
        }
        if (input.isKeyPressed(Input.KEY_LEFT)) {
            keyStack.push(Input.KEY_LEFT);
        }
        if (input.isKeyPressed(Input.KEY_RIGHT)) {
            keyStack.push(Input.KEY_RIGHT);
        }

        for (Object obj : keyStack.toArray()) {
            Integer i = (Integer) obj;
            if (!input.isKeyDown(i)) {
                keyStack.remove(keyStack.indexOf(i));
            }
        }

        try {
            tos = keyStack.peek();
            if (tos == Input.KEY_DOWN) {
                if (collidables[x][y + 1] != null) {
                    if (collides(collidables[x][y + 1]) == null) {
                        moveDown();
                    } else if (collidables[x - 1][y + 1] == null) {
                        moveLeft();
                    } else if (collidables[x + 1][y + 1] == null) {
                        moveRight();
                    } else {
                        moveStill();
                    }
                } else if (collidables[x - 1][y + 1] != null) {
                    if (collides(collidables[x - 1][y + 1]) != null) {
                       moveRight();
                    } else {
                        moveDown();
                    }
                } else if (collidables[x + 1][y + 1] != null) {
                    if (collides(collidables[x + 1][y + 1]) != null) {
                        moveLeft();
                    } else {
                        moveDown();
                    }
                }
                else {
                    moveDown();
                }
            } else if (tos == Input.KEY_UP) {
                if (collidables[x][y - 1] != null) {
                    if (collides(collidables[x][y - 1]) == null) {
                        moveUp();
                    } else if (collidables[x - 1][y - 1] == null) {
                        moveLeft();
                    } else if (collidables[x + 1][y - 1] == null) {
                        moveRight();
                    } else {
                        moveStill();
                    }
                } else if (collidables[x - 1][y - 1] != null) {
                    if (collides(collidables[x - 1][y - 1]) != null) {
                        moveRight();
                    } else {
                        moveUp();
                    }
                } else if (collidables[x + 1][y - 1] != null) {
                    if (collides(collidables[x + 1][y - 1]) != null) {
                        moveLeft();
                    } else {
                        moveUp();
                    }
                }
                else {
                    moveUp();
                }
            } else if (tos == Input.KEY_RIGHT) {
                if (collidables[x + 1][y] != null) {
                    if (collides(collidables[x + 1][y]) == null) {
                        moveRight();
                    } else if (collidables[x + 1][y - 1] == null) {
                        moveUp();
                    } else if (collidables[x + 1][y + 1] == null) {
                        moveDown();
                    } else {
                        moveStill();
                    }
                } else if (collidables[x + 1][y - 1] != null) {
                    if (collides(collidables[x + 1][y - 1]) != null) {
                        moveDown();
                    } else {
                        moveRight();
                    }
                } else if (collidables[x + 1][y + 1] != null) {
                    if (collides(collidables[x + 1][y + 1]) != null) {
                        moveUp();
                    } else {
                        moveRight();
                    }
                }
                else {
                    moveRight();
                }
            } else if (tos == Input.KEY_LEFT) {
                if (collidables[x - 1][y] != null) {
                    if (collides(collidables[x - 1][y]) == null) {
                        moveLeft();
                    } else if (collidables[x - 1][y - 1] == null) {
                        moveUp();
                    } else if (collidables[x - 1][y + 1] == null) {
                        moveDown();
                    } else {
                        moveStill();
                    }
                } else if (collidables[x - 1][y - 1] != null) {
                    if (collides(collidables[x - 1][y - 1]) != null) {
                        moveDown();
                    } else {
                        moveLeft();
                    }
                } else if (collidables[x - 1][y + 1] != null) {
                    if (collides(collidables[x - 1][y + 1]) != null) {
                        moveUp();
                    } else {
                        moveLeft();
                    }
                }
                else {
                    moveLeft();
                }
            }
        } catch (Exception e) {
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
                handleCollision(collision);
                enemy.bounce(enemy.getVelocity(), 0.05f);
                this.bounce(enemy.getVelocity(), 0.05f);
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
        health = initialHealth;
    }

    public int getHealth() {
        return health;
    }

}
