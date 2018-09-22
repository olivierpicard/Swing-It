package com.caffeine.swingit.Graphics;

import android.graphics.Canvas;

/**
 * Dessine l'objet à l'écran, en prenant en compte
 * les modifications fait lors de l'update
 */

public interface IGDrawable extends IGRenderAllowable{
    GPoint getPosition();
    void setPosition(GPoint position);

    GSize getSize();
    void setSize(GSize size);

    GScene getScene();
    void setScene(GScene scene);
}
