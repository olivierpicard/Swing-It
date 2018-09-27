package com.caffeine.swingit;

import android.graphics.Color;
import android.graphics.Typeface;

import com.caffeine.swingit.GameScene;
import com.caffeine.swingit.Graphics.GLabel;
import com.caffeine.swingit.Graphics.GPoint;
import com.caffeine.swingit.Graphics.GTools;
import com.caffeine.swingit.R;


/**
 * Created by olivierpicard on 06/04/2018.
 */

public class WelcomeScreen {
    private final GameScene scene;
    private final GLabel title_label;
    private final Button play_button;
    private boolean isHidden;

    public WelcomeScreen(GameScene scene) {
        this.scene = scene;
        this.isHidden = true;
        this.title_label = new GLabel("Swing it");
        this.title_label.setAlpha(128);
        this.title_label.setFontType(Typeface.create("Helvetica", Typeface.NORMAL));
        this.title_label.setFontSize(55);
        this.title_label.setColor(GTools.setColorOpacity(Color.WHITE, 150));

        this.play_button = new Button(R.string.jouer);

        this.title_label.setPosition(GTools.fromSceneToScreenPos(this.scene.getSize(), new GPoint(0.5f, 0.8f)));

        this.play_button.setPosition(GTools.fromSceneToScreenPos(this.scene.getSize(), new GPoint(0.5f, 0.5f)));
    }


    public void show() {
        if(!isHidden) return;
        this.isHidden = false;
        this.scene.addChild(this.title_label);
        this.scene.addChild(this.play_button);
    }

    public void hide() {
        if(isHidden) return;
        this.isHidden = true;
        this.scene.removeChild(this.title_label);
        this.scene.removeChild(this.play_button);
    }

    public void touchUp(GPoint pos) {
        if(this.play_button.isClicked(pos))
            scene.setGameState(GameScene.GameState.PLAY);
    }

}
