package com.runner.arthur.dora;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;

public class Item {
    protected Animation animatedSprite;
    protected boolean collectable;
    protected Rectangle position;
    protected float stateTime = 0.0f;
    protected int typeId;
    protected boolean visible;

    public Item(float x, float y, int typeId) {
        this.typeId = typeId;
        this.stateTime = 0.0f;
        float w = 0.0f;
        float h = 0.0f;
        if (this.typeId == ItemType.RED_ICE_CREAM.getTypeId()) {
            w = ItemType.RED_ICE_CREAM.getWidth();
            h = ItemType.RED_ICE_CREAM.getHeight();
            this.animatedSprite = ResourceContainer.redIceCreamAnim;
            this.collectable = true;
        } else if (this.typeId == ItemType.GOLD_COIN.getTypeId()) {
            w = ItemType.GOLD_COIN.getWidth();
            h = ItemType.GOLD_COIN.getHeight();
            this.animatedSprite = ResourceContainer.goldCoinAnim;
            this.collectable = true;
        } else if (this.typeId == ItemType.YELLOW_FLAG.getTypeId()) {
            w = ItemType.YELLOW_FLAG.getWidth();
            h = ItemType.YELLOW_FLAG.getHeight();
            this.animatedSprite = ResourceContainer.yellowFlagAnim;
            this.collectable = false;
        } else if (this.typeId == ItemType.GREEN_FLAG.getTypeId()) {
            w = ItemType.GREEN_FLAG.getWidth();
            h = ItemType.GREEN_FLAG.getHeight();
            this.animatedSprite = ResourceContainer.greenFlagAnim;
            this.collectable = false;
        }
        this.position = new Rectangle(x, y, w, h);
        this.visible = true;
    }

    public void render(SpriteBatch batch) {
        this.stateTime += Gdx.graphics.getDeltaTime();
        if (new Rectangle(RunnerDora.camera.position.x - (RunnerDora.camera.viewportWidth / 2.0f), RunnerDora.camera.position.y - (RunnerDora.camera.viewportHeight / 2.0f), RunnerDora.camera.viewportWidth, RunnerDora.camera.viewportHeight).overlaps(this.position) && this.visible) {
            batch.begin();
            batch.draw((TextureRegion)this.animatedSprite.getKeyFrame(this.stateTime, true), this.position.getX(), this.position.getY());
            batch.end();
        }
    }

    public int getTypeId() {
        return this.typeId;
    }

    public Rectangle getPosition() {
        return this.position;
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
    }

    public boolean isVisible() {
        return this.visible;
    }

    public boolean isCollectable() {
        return this.collectable;
    }

    public void reset() {
        this.stateTime = 0.0f;
        this.visible = true;
    }

    public int getPoints() {
        if (getTypeId() == ItemType.GOLD_COIN.getTypeId()) {
            return 10;
        }
        if (getTypeId() == ItemType.RED_ICE_CREAM.getTypeId()) {
            return 100;
        }
        return 0;
    }
}
