package com.caffeine.swingit.Graphics;

/**
 * Created by olivierpicard on 02/04/2018.
 */

public class GVector {
    public float dx, dy;

    public GVector(float dx, float dy) {
        this.dx = dx;
        this.dy = dy;
    }

    public GVector(double dx, double dy) {
        this.dx = (float)dx;
        this.dy = (float)dy;
    }

    public GVector(GVector vector) {
        this.dx = vector.dx;
        this.dy = vector.dy;
    }

    public GVector(GPoint p1, GPoint p2) {
        this.dx = p2.x - p1.x;
        this.dy = p2.y - p1.y;
    }

    public GVector add(GVector v) {
        return new GVector(this.dx + v.dx, this.dy + v.dy);
    }

    public GVector multiply(float factor) {
        return new GVector(this.dx * factor, this.dy * factor);
    }


    @Override
    public String toString() {
        return "(dx: " + this.dx + ", dy: " + this.dy + ")";
    }
    
    public static GVector zero() { return new GVector(0, 0); }
    public static GVector up() { return new GVector(0, -1); }
    public static GVector down() { return new GVector(0, 1); }
    public static GVector left() { return new GVector(-1, 0); }
    public static GVector right() { return new GVector(1, 0); }

    public static GVector normalize(GVector vector) {
        float newX = (vector.dx > 0) ? 1 : -1;
        float newY = (vector.dy > 0) ? 1 : -1;
        if(vector.dx == 0) newX = 0;
        if(vector.dy == 0) newY = 0;

        return new GVector(newX, newY);
    }
}
