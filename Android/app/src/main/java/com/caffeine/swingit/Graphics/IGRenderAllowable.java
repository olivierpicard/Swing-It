package com.caffeine.swingit.Graphics;

import android.graphics.Canvas;

public interface IGRenderAllowable
{
    int getZPosition();
    void setZPosition(int zPosition);

    int getColor();
    void setColor(int color);

    float getZRotation();
    void setZRotation(float zRotation);

    GRelativeRender getRelativeRender();

    void render(Canvas canvas);
}
