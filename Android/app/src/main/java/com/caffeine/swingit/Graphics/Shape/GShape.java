package com.caffeine.swingit.Graphics.Shape;

import android.graphics.Canvas;

import com.caffeine.swingit.Graphics.GNode;
import com.caffeine.swingit.Graphics.GPoint;
import com.caffeine.swingit.Graphics.GRelativeRender;
import com.caffeine.swingit.Graphics.GTools;
import com.caffeine.swingit.Graphics.IGDrawable;

public abstract class GShape extends GNode implements IGDrawable
{
    private int color;
    private float zRotation;
    private GRelativeRender relativeRender;
    public GRelativeRender.RotationPivot rotationPivot;
    protected float thickness;


    public GShape() {
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

    public GRelativeRender getRelativeRender() {
        return relativeRender;
    }

    public abstract void render(Canvas canvas);
}
