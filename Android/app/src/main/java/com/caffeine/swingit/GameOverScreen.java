package com.caffeine.swingit;

import android.graphics.Typeface;

import com.caffeine.swingit.Graphics.GLabel;
import com.caffeine.swingit.Graphics.GPoint;
import com.caffeine.swingit.Graphics.GTools;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Ecran de game over
 * Created by olivierpicard on 06/04/2018.
 */

public class GameOverScreen {
    private final int BUTTON_INTER_SPACE;
    private final GameScene scene;
    private Button retry_button;
    private Button menu_button;
    private boolean isHidden;


    public GameOverScreen(GameScene scene) {
        this.scene = scene;
        this.isHidden = true;
        BUTTON_INTER_SPACE = (int)this.scene.getSize().height / 15;

        this.retry_button = new Button(R.string.recommencer);
        this.retry_button.setPosition(GTools.fromSceneToScreenPos(this.scene.getSize(), new GPoint(0.5f, 0.6f)));

        this.menu_button = new Button(R.string.accueil);
        this.menu_button.setPosition(new GPoint(this.retry_button.getPosition().x,
                this.retry_button.getPosition().y + this.retry_button.getSize().height + BUTTON_INTER_SPACE));
    }


    public void hide() {
        if(isHidden) return;
        this.isHidden = true;
        scene.removeChild(this.retry_button);
        scene.removeChild(this.menu_button);
    }


    public void show() {
        if(!isHidden) return;
        this.isHidden = false;
        this.scene.addChild(this.retry_button);
        this.scene.addChild(this.menu_button);
    }


    public void touchUp(GPoint pos){
        if(retry_button.isClicked(pos))
            scene.setFlagGameState(GameScene.GameState.PLAY);
        else if(menu_button.isClicked(pos))
            scene.setFlagGameState(GameScene.GameState.WELCOME);
    }

}
