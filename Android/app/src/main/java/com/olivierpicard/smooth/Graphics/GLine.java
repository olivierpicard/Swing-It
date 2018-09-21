package com.olivierpicard.smooth.Graphics;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

public class GLine extends GShape
{
    private GPoint goalPoint;

    public GLine(GPoint from, GPoint to)
    {
        super(from);
        goalPoint = to;
        thickness = 2f;
    }


    public void render(Canvas canvas)
    {
        Paint p = new Paint();
        p.setStrokeWidth(thickness);
        p.setColor(getColor());
        canvas.drawLine(getPosition().x, getPosition().y, goalPoint.x, goalPoint.y, p);
    }
}
