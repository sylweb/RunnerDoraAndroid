package com.runner.arthur.dora;

import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Rectangle;

public class Tile {
    private Rectangle drawBox;
    private Polygon hitbox;
    private int id;
    private boolean isTriangle;

    public Tile() {
        this.id = -1;
    }

    public Tile(float x, float y, float w, float h, int id) {
        this.id = id;
        this.drawBox = new Rectangle(x, y, w, h);
        this.isTriangle = false;
        if (id == 0 || id == 1 || id == 2) {
            this.hitbox = ShapeTools.rectangleToPolygon(x, (y + h) - 10.0f, w, 10.0f);
        }
        if (id == 3) {
            this.hitbox = new Polygon(new float[]{x, y, x + w, y, x + w, y + w});
            this.isTriangle = true;
        }
        if (id == 4) {
            this.hitbox = new Polygon(new float[]{(x + w) - 10.0f, y, x + w, y, x, y + w});
            this.isTriangle = true;
        }
    }

    public int getId() {
        return this.id;
    }

    @SuppressWarnings("SuspiciousNameCombination")
    public float calculateYVelocity(float playerXVelocity) {
        switch (this.id) {
            case 0:
                return 0.0f;
            case 1:
                return 0.0f;
            case 2:
                return 0.0f;
            case 3:
                return playerXVelocity;
            case 4:
                return playerXVelocity * -1.0f;
            default:
                return 0.0f;
        }
    }

    public Polygon getHitBox() {
        return this.hitbox;
    }

    public Rectangle getDrawBox() {
        return this.drawBox;
    }

    public boolean isTriangle() {
        return this.isTriangle;
    }
}
