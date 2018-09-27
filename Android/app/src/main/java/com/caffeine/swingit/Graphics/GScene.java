package com.caffeine.swingit.Graphics;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.SortedSet;
import java.util.TreeSet;

/**
 * Scene côté vue, qui va servir à afficher
 * et gérer les items de la scene
 */
public abstract class GScene extends GNode implements Runnable
{
    public enum TouchType {DOWN, UP, MOVE}


    public class TouchEvent
    {
        boolean isEdited = false;
        private TouchType touchType = null;
        private GPoint position = GPoint.zero();
        public void edit(TouchType t, GPoint pos) {isEdited = true; touchType = t; position = pos; }
    }


    private class SwipeData
    {
        GPoint firstTouchPos = GPoint.zero();
        GPoint intermediatePos = GPoint.zero();
        GVector vectorSwipe = GVector.zero();
        float refreshDistance = 5f;

        void compute(GPoint newPoint) {
            if(intermediatePos == GPoint.zero()) return;
            vectorSwipe = new GVector(intermediatePos, newPoint);
            if(GPoint.distance(intermediatePos, newPoint) > refreshDistance)
                intermediatePos = newPoint;
        }

        void reset() {
            firstTouchPos = GPoint.zero();
            vectorSwipe = GVector.zero();
            intermediatePos = GPoint.zero();
        }

        void init(GPoint pos) {
            firstTouchPos = pos;
            intermediatePos = pos;
        }
    }


    class AccelerometerEvent
    {
        private boolean isEdited = false;
        private float[] currentValue;
        private float[] referenceValues;
        float[] deltaValues = new float[3];

        void edit(float[] newValues) {
            isEdited = true;
            currentValue = newValues;
            if(referenceValues == null) referenceValues = currentValue.clone();
            deltaValues[0] = referenceValues[0] - currentValue[0];
            deltaValues[1] = referenceValues[1] - currentValue[1];
            deltaValues[2] = referenceValues[2] - currentValue[2];
            System.out.println(currentValue[0] + " --- " + referenceValues[0] + " --- " + deltaValues[0]);
        }
    }


    protected int backgroundColor = Color.BLACK;
    private List<GNode> elementsToAdd;
    private List<GNode> elementsToRemove;
    private List<IGCollisionListener> collisionListeners;
    private GSize baseSize;
    private GSize scale;

    public volatile boolean enable = false;

    public TouchEvent touchEvent;
    public AccelerometerEvent accelerometerEvent;
    private SwipeData swipeData = new SwipeData();
    private GSize size;

    private long time_from_lastFrame = 0;
    public int colorStatsText = Color.WHITE;
    public boolean showFPS = true;
    public boolean showNodeCounter = true;


    abstract public void didInitialized();
    abstract public void start();
    abstract public void update(long currentTime);

    protected void touchDown(@NonNull GPoint pos) { swipeData.init(pos); }
    protected void touchUp(@NonNull GPoint pos) { swipeData.reset(); }
    protected void touchMove(@NonNull GPoint pos) { swipeData.compute(pos);}
    protected void touchSwipe(@NonNull GVector vectorIntermediate, @NonNull GPoint startPos, @NonNull GPoint currentPos) {  }
    protected void onAccelerometerEvent(@NonNull float[] axisValues) { }


    public void init(GSize baseSceneSize)
    {
        touchEvent = new TouchEvent();
        accelerometerEvent = new AccelerometerEvent();
        elementsToAdd = new ArrayList<>();
        elementsToRemove = new ArrayList<>();
        collisionListeners = new ArrayList<>();

        this.baseSize = baseSceneSize;
        final float xScale = GTools.screenMetrics.widthPixels / this.baseSize.width;
        final float yScale = GTools.screenMetrics.heightPixels / this.baseSize.height;
        this.scale = new GSize(xScale, yScale);
        this.size = new GSize(GTools.screenMetrics.widthPixels / this.scale.width,
                GTools.screenMetrics.heightPixels/ this.scale.height);

        GSceneViewController.surfaceHolder.setFixedSize(
                (int)this.size.width,
                (int)this.size.height
        );
    }


    public void run()
    {
        didInitialized();
        while(this.enable) {
            refreshSceneNodes();
            processTouch();
            processAccelerometer();
            update(System.currentTimeMillis());
            Canvas canvas = GSceneViewController.surfaceHolder.lockCanvas();
            if (canvas != null) {
                synchronized (GSceneViewController.surfaceHolder) {
                    canvas.drawColor(backgroundColor, PorterDuff.Mode.CLEAR);
                    render(canvas);
                }
                GSceneViewController.surfaceHolder.unlockCanvasAndPost(canvas);
            }
            try { Thread.sleep(16); } catch (Exception e) {}
        }
    }


    private void render(Canvas canvas)
    {
        Map<Integer, List<IGDrawable>> renderElements = new LinkedHashMap<>();
        processRenderOrder(renderElements);
        SortedSet<Integer> orderedRenderElement = new TreeSet<>(renderElements.keySet());

        for(Integer id : orderedRenderElement) {
            for(IGDrawable node : renderElements.get(id)) {
                if(deleteIfPossible(node)) continue;
                if(node instanceof IGCollisionable) computeCollision((IGCollisionable)node);
                node.render(canvas);
            }
        }

        renderStats(canvas);
    }


    private void computeCollision(IGCollisionable currentNode)
    {
        for(IGCollisionListener listener : collisionListeners) {
            if(currentNode == listener) continue;
            if(listener.getBound().intersect(currentNode.getBound()) && !listener.getCollisionItems().contains(currentNode)) {
                listener.getCollisionItems().add(currentNode);
                listener.collisionEnter(currentNode);
            }
            else if(listener.getCollisionItems().contains(currentNode)) {
                listener.getCollisionItems().remove(currentNode);
                listener.collisionExit(currentNode);
            }
        }
    }


    private boolean deleteIfPossible(IGDrawable drawableNode)
    {
        if(drawableNode instanceof IGDeletable){
            final IGDeletable deletableNode = (IGDeletable)drawableNode;
            if(deletableNode.canBeDeleted()) {
                removeChild((GNode)deletableNode);
                return true;
            }
        }
        return false;
    }


    private void renderStats(Canvas canvas)
    {
        Paint p = new Paint();
        p.setTextSize(12);
        p.setColor(colorStatsText);
        p.setAntiAlias(true);
        p.setTextAlign(Paint.Align.LEFT);
        if(this.showFPS)
            canvas.drawText("FPS : " + (int)(1 / ((float)(System.currentTimeMillis() - this.time_from_lastFrame) / 1000)),
                    0, (int) this.size.height - 20, p);
        if(this.showNodeCounter)
            canvas.drawText("Nodes : " + children.size(),
                    0, (int)this.size.height - 5, p);

        this.time_from_lastFrame = System.currentTimeMillis();
    }


    private void refreshSceneNodes() {
        for(GNode node : elementsToRemove) {
            if(node instanceof IGCollisionListener) this.collisionListeners.remove(node);
            this.children.remove(node);
            node.setScene(null);
        }
        for(GNode node : elementsToAdd) {
            this.children.add(node);
            if(node instanceof IGCollisionListener) this.collisionListeners.add((IGCollisionListener)node);
        }

        // On peut utilisé clear, mais il me semble
        // que le garbageCollector fera mieux son travail
        // que clear()
//        elementsToAdd = new ArrayList<>();
        elementsToAdd.clear();
        elementsToRemove = new ArrayList<>();
    }


    private void processTouch() {
        if(!this.touchEvent.isEdited) return;
        this.touchEvent.isEdited = false;
        final GPoint pos = this.touchEvent.position;
        switch (this.touchEvent.touchType) {
            case DOWN:
                touchDown(pos);
                break;
            case UP:
                touchUp(pos);
                break;
            case MOVE:
                touchMove(pos);
                if(swipeData.vectorSwipe != GVector.zero())
                    touchSwipe(swipeData.vectorSwipe, swipeData.firstTouchPos, pos);
                break;
        }
    }


    private void processAccelerometer()
    {
        if(!accelerometerEvent.isEdited) return;
        onAccelerometerEvent(accelerometerEvent.deltaValues);
        accelerometerEvent.isEdited = false;
    }

    /**
     * Organise tous les noeuds de la scène selon leur position en z
     * @param map : Key: position en Z  -  Value: Tous les élements à cette position
     */
    private void processRenderOrder(Map<Integer, List<IGDrawable>> map) {
        for(GNode node : this.children) {
            if(!(node instanceof IGDrawable)) continue;
            IGDrawable renderAllowable = (IGDrawable)node;
            List<IGDrawable> list = map.get(renderAllowable.getRelativeRender().zPosition);
            if(list == null) list = new ArrayList<>();
            list.add(renderAllowable);
            map.put(renderAllowable.getRelativeRender().zPosition, list);
        }
    }



    /**
     * Ajoute un noeud ansi que ses enfants
     * de la scène lors de la prochaine frame
     * @param node noeud à ajouter
     */
    @Override
    public final void addChild(GNode node) {
        if(node.getScene() != this) {
            node.setScene(this);
            if(node.parent == null) return;
        }
        this.elementsToAdd.add(node);
    }

    /**
     * Supprime un noeud ansi que ses enfants
     * de la scène lors de la prochaine frame
     * @param node noeud à supprimer
     */
    @Override
    public final void removeChild(GNode node) {
        this.elementsToRemove.add(node);
        this.elementsToRemove.addAll(node.getDeepChildren());
    }

    /**
     * Supprime une liste de noeuds ansi que tous leurs enfants
     * de la scène lors de la prochaine frame
     * @param nodes liste de noeuds à supprimer
     */
    @Override
    public final void removeChildren(List<GNode> nodes) {
        for (GNode node: nodes) {
            this.elementsToRemove.add(node);
            this.elementsToRemove.addAll(node.getDeepChildren());
        }
    }

    public GSize getSize() {
        return size;
    }

    /**
     *
     * @param d the distance to refresh the vector direction.
     *          Set an intermediate point between start and current position, and the vector is
     *          reCalculate on the intermediate value instead of the start position
     */
    final protected void setSwipeDirectionRefreshDistance(float d){ swipeData.refreshDistance = d; }

    final protected void markAsAccelerometerReferencePosition() {
        accelerometerEvent.referenceValues = accelerometerEvent.currentValue.clone();
    }
}
