package com.caffeine.swingit.Graphics;

import java.util.List;

public interface IGCollisionListener extends IGCollisionable
{
    void collisionEnter(IGCollisionable collisionable);
    void collisionExit(IGCollisionable collisionable);
    List<IGCollisionable> getCollisionItems();
    void setCollisionItems(List<IGCollisionable> itemInCollision);
}
