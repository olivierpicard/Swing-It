package com.caffeine.swingit.Graphics;

import android.graphics.Canvas;

public interface IGDrawable
{
    int getZPosition();
    void setZPosition(int zPosition);

    int getColor();
    void setColor(int color);

    float getZRotation();
    void setZRotation(float zRotation);

    GScene getScene();
    void setScene(GScene scene);

    GRelativeRender getRelativeRender();

    void render(Canvas canvas);
}
