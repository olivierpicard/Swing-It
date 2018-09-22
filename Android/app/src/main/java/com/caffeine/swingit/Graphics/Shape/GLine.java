package com.caffeine.swingit.Graphics.Shape;

import android.graphics.Canvas;
import android.graphics.Paint;

import com.caffeine.swingit.Graphics.GPoint;

public class GLine extends GShape
{
    private GPoint goalPoint;


    private void init(GPoint to)
    {
        goalPoint = to;
        thickness = 2f;
    }

    public GLine(GPoint from, GPoint to)
    {
        super(from);
        init(to);
    }


    public GLine(GPoint from, GPoint to, int color)
    {
        super(from);
        init(to);
        setColor(color);
    }


    public void render(Canvas canvas)
    {
        Paint p = new Paint();
        p.setStrokeWidth(thickness);
        p.setColor(getColor());
        canvas.drawLine(getPosition().x, getPosition().y, goalPoint.x, goalPoint.y, p);
    }
}
