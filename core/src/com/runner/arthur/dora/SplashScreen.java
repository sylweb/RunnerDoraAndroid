package com.runner.arthur.dora;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class SplashScreen {
    private float gameStartTime = 0.0f;
    private boolean hasBeenStarted = false;
    private float phaseTime = 0.0f;
    private Texture screen;
    private float totalTime = 0.0f;

    public SplashScreen(float screenTime, Texture screen) {
        this.totalTime = screenTime;
        this.screen = screen;
    }

    public void render(float currentGameTime, SpriteBatch batch) {
        float internalTime = currentGameTime - this.gameStartTime;
        if (internalTime > this.totalTime) {
            this.hasBeenStarted = false;
        }
        if (this.hasBeenStarted) {
            batch.begin();
            Color c = batch.getColor();
            float transparency = 1.0f;
            if (internalTime <= this.phaseTime) {
                transparency = internalTime / this.phaseTime;
            }
            if (internalTime >= this.phaseTime * 2.0f) {
                transparency = 1.0f - ((internalTime - (this.phaseTime * 2.0f)) / this.phaseTime);
            }
            batch.setColor(c.r, c.g, c.b, transparency);
            batch.draw(this.screen, 0.0f, 0.0f);
            batch.setColor(c);
            batch.end();
        }
    }

    public void start(float currentGameTime) {
        if (!this.hasBeenStarted) {
            this.hasBeenStarted = true;
            this.gameStartTime = currentGameTime;
            this.phaseTime = this.totalTime / 3.0f;
        }
    }

    public boolean isFinished() {
        return !this.hasBeenStarted;
    }
}
