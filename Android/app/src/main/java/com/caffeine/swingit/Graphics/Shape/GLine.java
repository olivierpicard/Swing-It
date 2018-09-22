package com.caffeine.swingit.Graphics.Shape;

import android.graphics.Canvas;
import android.graphics.Paint;

import com.caffeine.swingit.Graphics.GPoint;

public class GLine extends GShape
{
    private GPoint pointA, pointB;


    public GLine(GPoint from, GPoint to, int color)
    {
        pointA = from;
        pointB = to;
        thickness = 2f;
        setColor(color);
    }


    public void render(Canvas canvas)
    {
        Paint p = new Paint();
        p.setStrokeWidth(thickness);
        p.setColor(getColor());
        canvas.drawLine(pointA.x, pointA.y, pointB.x, pointB.y, p);
    }
}
