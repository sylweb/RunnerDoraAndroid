package com.runner.arthur.dora;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;

public class Background {
    protected float SCROLL_VELOCITY;
    protected float deltaSky;
    protected float oldCamX;
    protected Rectangle positionHorizon;
    protected Texture spriteHorizon;

    public Background() {
        this.deltaSky = 0.0f;
        this.oldCamX = 0.0f;
        this.spriteHorizon = new Texture(Gdx.files.internal("gfx/background.png"));
        this.positionHorizon = new Rectangle(RunnerDora.camera.position.x - (RunnerDora.camera.viewportWidth / 2.0f), -30.0f, (float) this.spriteHorizon.getWidth(), (float) this.spriteHorizon.getHeight());
        this.SCROLL_VELOCITY = 0.1f;
        this.oldCamX = RunnerDora.camera.position.x;
    }

    public void render(SpriteBatch batch, ShapeRenderer shape) {
        batch.begin();
        batch.draw(this.spriteHorizon, this.positionHorizon.getX(), this.positionHorizon.getY());
        batch.end();
    }

    public void update(float playerScrollVelocity) {
        this.positionHorizon.x = RunnerDora.camera.position.x - (RunnerDora.camera.viewportWidth / 2.0f);
    }
}
