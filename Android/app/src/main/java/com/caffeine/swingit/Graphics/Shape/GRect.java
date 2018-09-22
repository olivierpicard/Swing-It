package com.caffeine.swingit.Graphics.Shape;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;

import com.caffeine.swingit.Graphics.GPoint;
import com.caffeine.swingit.Graphics.GSize;

public class GRect extends GShape
{
    private RectF bounds;

    public GRect(GPoint position, GSize size, int color)
    {
        setColor(color);
        thickness = 1f;

        final GSize halfSize = new GSize(size.width / 2, size.height / 2);
        bounds = new RectF(
                position.x - halfSize.width,
                position.y - halfSize.height,
                position.x + halfSize.width,
                position.y + halfSize.height);
    }


    public GRect(RectF bounds, int color)
    {
        this.bounds = bounds;
        setColor(color);
    }


    public void render(Canvas canvas)
    {
        Paint p = new Paint();
        p.setStrokeWidth(thickness);
        p.setColor(getColor());
        canvas.drawRect(bounds, p);
    }
}
