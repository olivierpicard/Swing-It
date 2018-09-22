package com.caffeine.swingit.Graphics;

import android.graphics.Canvas;
import android.graphics.Color;

public abstract class GShape extends GNode implements IGRenderAllowable
{
    private int color;
    private float zRotation;
    private GRelativeRender relativeRender;
    public GRelativeRender.RotationPivot rotationPivot;
    protected float thickness;
    protected GPoint position;


    public GShape(GPoint position) {
        this.position = position;
        relativeRender = new GRelativeRender();
        rotationPivot = GRelativeRender.RotationPivot.ROOT;
        zRotation = 0f;
        thickness = 0f;
    }


    public int getColor() {
        return color;
    }
    public void setColor(int color) {
        this.color = color;
    }

    public int getAlpha() {return (this.color >> 24) & 0xff;}
    public void setAlpha(int value) {
        this.color = GTools.setColorOpacity(this.color, value);
    }

    public float getZRotation() { return zRotation; }
    public void setZRotation(float zRotation) { this.zRotation = zRotation; }

    public GPoint getPosition() { return position; }
    public void setPosition(GPoint position) { this.position = position; }

    public GRelativeRender getRelativeRender() {
        return relativeRender;
    }

    public abstract void render(Canvas canvas);
}
