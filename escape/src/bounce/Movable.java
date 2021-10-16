package bounce;

import jig.Entity;
import jig.Vector;

public class Movable extends Entity {
    protected Vector velocity;

    public Movable (int x, int y, Vector velocity) {
        super(x,y);
        this.velocity = velocity;
    }

    public Movable (int x, int y, float vx, float vy) {
        this(x,y,new Vector(vx,vy));
    }

    public Movable (int x, int y) {
        this(x,y,0f,0f);
    }

    public void update(int delta) {
        translate(velocity.scale(delta));
    }
}
