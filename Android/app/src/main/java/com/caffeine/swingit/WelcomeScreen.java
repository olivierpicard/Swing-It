package com.caffeine.swingit;

import android.graphics.Color;
import android.graphics.Typeface;

import com.caffeine.swingit.Graphics.GLabel;
import com.caffeine.swingit.Graphics.GPoint;
import com.caffeine.swingit.Graphics.GTools;


/**
 * Created by olivierpicard on 06/04/2018.
 */

public class WelcomeScreen {
    private final GameScene scene;
    private final GLabel title_label;
    private final Button play_button;
    private final Button resume_button;
    private final Button map_button;
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
        this.resume_button = new Button(R.string.experience);
        this.map_button = new Button(R.string.map);

        this.title_label.setPosition(GTools.fromSceneToScreenPos(this.scene.getSize(), new GPoint(0.5f, 0.8f)));
        this.play_button.setPosition(GTools.fromSceneToScreenPos(this.scene.getSize(), new GPoint(0.5f, 0.6f)));
        this.resume_button.setPosition(new GPoint(this.play_button.getPosition().x,
                this.play_button.getPosition().y + this.play_button.getSize().height + 30));
        this.map_button.setPosition(new GPoint(this.resume_button.getPosition().x,
                this.resume_button.getPosition().y + this.resume_button.getSize().height + 30));
    }


    public void show() {
        if(!isHidden) return;
        this.isHidden = false;
        this.scene.addChild(this.title_label);
        this.scene.addChild(this.play_button);
        this.scene.addChild(this.resume_button);
        this.scene.addChild(this.map_button);
    }


    public void hide() {
        if(isHidden) return;
        this.isHidden = true;
        this.scene.removeChild(this.title_label);
        this.scene.removeChild(this.play_button);
        this.scene.removeChild(this.resume_button);
        this.scene.removeChild(this.map_button);
    }

    public void touchUp(GPoint pos) {
        if(this.play_button.isClicked(pos))
            scene.setFlagGameState(GameScene.GameState.PLAY);
        else if(this.resume_button.isClicked(pos))
            GTools.activitySwitcher.switchActivityWithResult(QRCodeActivity.class, MainActivity.QRCODE_ACTIVITY_CODE);
        else if(this.map_button.isClicked(pos))
            GTools.activitySwitcher.switchActivityWithResult(MapsActivity.class, MainActivity.MAP_ACTIVITY_CODE);
    }

}
