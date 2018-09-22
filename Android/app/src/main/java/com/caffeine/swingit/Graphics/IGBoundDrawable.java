package com.caffeine.swingit.Graphics;

/**
 * Dessine l'objet à l'écran, en prenant en compte
 * les modifications fait lors de l'update
 */

public interface IGBoundDrawable extends IGDrawable {
    GSize getSize();
    void setSize(GSize size);

    GPoint getPosition();
    void setPosition(GPoint position);
}
