package com.runner.arthur.dora;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public abstract class Enemy {
    protected float VELOCITY_X = 0.0f;
    protected float VELOCITY_Y = 0.0f;
    protected Animation animLeft = null;
    protected Animation animRight = null;
    protected Texture deadSpriteLeft = null;
    protected Texture deadSpriteRight = null;
    protected float deltaXMove;
    protected float deltaYMove;
    protected Rectangle hitBox;
    protected int life = 1;
    protected float origVelocityX;
    protected Rectangle position;
    protected float stateTime = 0.0f;
    protected float xOrig;
    protected float yOrig;

    public Enemy(float x, float y, float w, float h, int life, float deltaX, float deltaY, Animation animLeft, Animation animRight, Texture deadSpriteLeft, Texture deadSpriteRight, float velocityX) {
        this.animLeft = animLeft;
        this.animRight = animRight;
        this.deadSpriteLeft = deadSpriteLeft;
        this.deadSpriteRight = deadSpriteRight;
        this.life = life;
        this.deltaXMove = deltaX;
        this.deltaYMove = deltaY;
        this.position = new Rectangle(x, y, w, h);
        Vector2 vec = new Vector2();
        this.position.getCenter(vec);
        this.xOrig = vec.x;
        this.yOrig = vec.y;
        this.VELOCITY_X = -1.0f * velocityX;
        this.origVelocityX = this.VELOCITY_X;
    }

    public void update() {
        Vector2 center = new Vector2();
        this.position.getCenter(center);
        if (this.deltaXMove > 0.0f && this.life > 0 && (center.x > this.xOrig + this.deltaXMove || center.x < this.xOrig - this.deltaXMove)) {
            this.VELOCITY_X *= -1.0f;
        }
        if (this.deltaYMove > 0.0f && this.life > 0 && (center.y > this.yOrig + this.deltaYMove || center.y < this.yOrig - this.deltaYMove)) {
            this.VELOCITY_Y *= -1.0f;
        }
        this.position.setCenter(new Vector2(center.x + this.VELOCITY_X, center.y + this.VELOCITY_Y));
        center = new Vector2();
        this.hitBox.getCenter(center);
        this.hitBox.setCenter(new Vector2(center.x + this.VELOCITY_X, center.y + this.VELOCITY_Y));
        if (this.life <= 0 && this.VELOCITY_Y > 10.0f * GameConstants.MOVE_DOWN) {
            this.VELOCITY_Y += 0.5f * GameConstants.MOVE_DOWN;
        }
    }

    public void render(SpriteBatch batch, ShapeRenderer shape) {
        this.stateTime += Gdx.graphics.getDeltaTime();
        if (new Rectangle(RunnerDora.camera.position.x - (RunnerDora.camera.viewportWidth / 2.0f), RunnerDora.camera.position.y - (RunnerDora.camera.viewportHeight / 2.0f), RunnerDora.camera.viewportWidth, RunnerDora.camera.viewportHeight).overlaps(this.position)) {
            batch.begin();
            if (this.life <= 0) {
                if (this.VELOCITY_X > 0.0f) {
                    batch.draw(this.deadSpriteRight, this.position.getX(), this.position.getY());
                } else {
                    batch.draw(this.deadSpriteLeft, this.position.getX(), this.position.getY());
                }
            } else if (this.VELOCITY_X > 0.0f) {
                batch.draw((TextureRegion)this.animRight.getKeyFrame(this.stateTime, true), this.position.getX(), this.position.getY());
            } else if (this.VELOCITY_X < 0.0f) {
                batch.draw((TextureRegion)this.animLeft.getKeyFrame(this.stateTime, true), this.position.getX(), this.position.getY());
            }
            batch.end();
            if (this.hitBox != null && GameSettings.DEBUG_MODE) {
                shape.begin(ShapeType.Line);
                shape.rect(this.hitBox.getX(), this.hitBox.getY(), this.hitBox.getWidth(), this.hitBox.getHeight());
                shape.end();
            }
        }
    }

    public Rectangle getHitBox() {
        return new Rectangle(this.hitBox.getX(), this.hitBox.getY(), this.hitBox.getWidth(), this.hitBox.getHeight());
    }

    public void hit() {
        this.life--;
        if (this.life <= 0) {
            this.VELOCITY_X = 0.0f;
            this.VELOCITY_Y = 4.0f * GameConstants.MOVE_DOWN;
            this.position.getCenter(new Vector2());
        }
    }

    public Rectangle getSpriteRect() {
        return this.position;
    }

    public void reset() {
        this.life = 1;
        this.position.setCenter(new Vector2(this.xOrig, this.yOrig));
        this.VELOCITY_Y = 0.0f;
        this.VELOCITY_X = this.origVelocityX;
    }

    public boolean isAlive() {
        return this.life > 0;
    }
}
