package com.caffeine.swingit.Graphics;

/**
 * Created by olivierpicard on 11/04/2018.
 */

import android.support.annotation.NonNull;

/**
 * Calcule les valeurs(position, zRotation...) relatives par rapport au noeud parent
 * et les stock en valeur absolue pour pouvoir être dessiné lors du rendu en fonction
 * en prenant en compte les informations du parents.
 */
public class GRelativeRender {
    public enum RotationPivot {
        SELF,
        ROOT
    }
    public float zRotation;
    public GPoint position;
    public int zPosition;


    public GRelativeRender() {
        this.zRotation = 0;
        this.position = GPoint.zero();
        this.zPosition = 0;
    }


    public void processChildRelativity(@NonNull GNode current) {
        if(!(current instanceof IGBoundDrawable)) return;

        final IGBoundDrawable _current = (IGBoundDrawable)current;
        this.zRotation = _current.getZRotation();
        this.zPosition = _current.getZPosition();
        this.position = _current.getPosition();

        if (current.parent == null || !(current.parent instanceof IGBoundDrawable)) return;

        processChildRelativity(current.parent);
        final IGBoundDrawable parent = (IGBoundDrawable)current.parent;
        this.zRotation = parent.getRelativeRender().zRotation + _current.getZRotation();
        this.position = GPoint.add(_current.getPosition(), parent.getRelativeRender().position);
        this.zPosition = parent.getRelativeRender().zPosition + _current.getZPosition();
    }

}
