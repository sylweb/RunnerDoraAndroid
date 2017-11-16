package com.runner.arthur.dora;

import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Rectangle;

public class ShapeTools {
    public static Polygon rectangleToPolygon(float x, float y, float width, float height) {
        return new Polygon(new float[]{x, y, x + width, y, x + width, y + height, x, y + height});
    }

    public static Polygon rectangleToPolygon(Rectangle rec) {
        return new Polygon(new float[]{rec.getX(), rec.getY(), rec.getX() + rec.getWidth(), rec.getY(), rec.getX() + rec.getWidth(), rec.getY() + rec.getHeight(), rec.getX(), rec.getY() + rec.getHeight()});
    }
}
