package com.caffeine.swingit.Graphics;

import android.support.annotation.NonNull;

import java.util.List;

public interface IGCollisionListener extends IGCollisionable
{
    void collisionEnter(@NonNull IGCollisionable collisionable);
    void collisionExit(@NonNull IGCollisionable collisionable);
    List<IGCollisionable> getCollisionItems();
    void setCollisionItems(List<IGCollisionable> itemInCollision);
}
