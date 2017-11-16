package com.runner.arthur.dora;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Animation.PlayMode;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;

public class Player {
    protected boolean display;
    protected boolean hasBeenHit;
    protected Rectangle hitBox;
    protected int invulnerabilityFrameCount;
    protected boolean isFalling;
    protected boolean isJumping;
    protected Animation jumpAnimLeft;
    protected Animation jumpAnimRight;
    protected Rectangle jumpBox;
    protected Texture jumpSpriteSheet;
    protected float lastXVelocity;
    protected int life;
    protected int multiplier;
    protected Rectangle position;
    protected int score;
    protected Animation standAnimLeft;
    protected Animation standAnimRight;
    protected Texture standSpriteSheet;
    protected float stateTime;
    protected Animation walkingAnimLeft;
    protected Animation walkingAnimRight;
    protected Texture walkingSpriteSheet;
    protected float xVelocity;
    protected float yVelocity;

    public Player(Rectangle pos, String walkAnimSprite, String standAnimSprite, int walkAnimFrameNb, int standAnimFrameNb) {
        int i;
        this.position = null;
        this.walkingSpriteSheet = null;
        this.jumpSpriteSheet = null;
        this.standSpriteSheet = null;
        this.isJumping = false;
        this.isFalling = false;
        this.jumpBox = new Rectangle(0.0f, 0.0f, 0.0f, 0.0f);
        this.hitBox = new Rectangle(0.0f, 0.0f, 0.0f, 0.0f);
        this.lastXVelocity = GameConstants.MOVE_RIGHT;
        this.stateTime = 0.0f;
        this.score = 0;
        this.multiplier = 1;
        this.display = true;
        this.hasBeenHit = false;
        this.invulnerabilityFrameCount = 0;
        this.life = 2;
        this.position = pos;
        this.walkingSpriteSheet = new Texture(Gdx.files.internal(walkAnimSprite));
        TextureRegion[][] tmp1 = TextureRegion.split(this.walkingSpriteSheet, this.walkingSpriteSheet.getWidth() / walkAnimFrameNb, this.walkingSpriteSheet.getHeight());
        TextureRegion[][] tmp2 = TextureRegion.split(this.walkingSpriteSheet, this.walkingSpriteSheet.getWidth() / walkAnimFrameNb, this.walkingSpriteSheet.getHeight());
        TextureRegion[] temp1 = new TextureRegion[(walkAnimFrameNb * 1)];
        TextureRegion[] temp2 = new TextureRegion[(walkAnimFrameNb * 1)];
        int index = 0;
        for (i = 0; i < 1; i++) {
            int j;
            for (j = 0; j < walkAnimFrameNb; j++) {
                tmp1[i][j].flip(false, false);
                temp1[index] = tmp1[i][j];
                tmp2[i][j].flip(true, false);
                temp2[index] = tmp2[i][j];
                index++;
            }
        }
        this.walkingAnimRight = new Animation(0.1f, temp1);
        this.walkingAnimLeft = new Animation(0.1f, temp2);
        this.walkingAnimRight.setPlayMode(PlayMode.LOOP);
        this.walkingAnimLeft.setPlayMode(PlayMode.LOOP);
        this.standSpriteSheet = new Texture(Gdx.files.internal(standAnimSprite));
        tmp1 = TextureRegion.split(this.standSpriteSheet, this.standSpriteSheet.getWidth() / standAnimFrameNb, this.standSpriteSheet.getHeight());
        tmp2 = TextureRegion.split(this.standSpriteSheet, this.standSpriteSheet.getWidth() / standAnimFrameNb, this.standSpriteSheet.getHeight());
        temp1 = new TextureRegion[(standAnimFrameNb * 1)];
        temp2 = new TextureRegion[(standAnimFrameNb * 1)];
        index = 0;
        for (i = 0; i < 1; i++) {
            for (int j = 0; j < standAnimFrameNb; j++) {
                tmp1[i][j].flip(false, false);
                temp1[index] = tmp1[i][j];
                tmp2[i][j].flip(true, false);
                temp2[index] = tmp2[i][j];
                index++;
            }
        }
        this.standAnimRight = new Animation(0.5f, temp1);
        this.standAnimLeft = new Animation(0.5f, temp2);
        this.standAnimRight.setPlayMode(PlayMode.LOOP);
        this.standAnimLeft.setPlayMode(PlayMode.LOOP);
    }

    public Player(Rectangle pos, String walkAnimSprite, String jumpAnimSprite, String standAnimSprite, int walkAnimFrameNb, int standAnimFrameNb, int jumpAnimFrameNb) {
        this(pos, walkAnimSprite, standAnimSprite, walkAnimFrameNb, standAnimFrameNb);
        this.jumpSpriteSheet = new Texture(Gdx.files.internal(jumpAnimSprite));
        TextureRegion[][] tmp1 = TextureRegion.split(this.jumpSpriteSheet, this.jumpSpriteSheet.getWidth() / jumpAnimFrameNb, this.jumpSpriteSheet.getHeight());
        TextureRegion[][] tmp2 = TextureRegion.split(this.jumpSpriteSheet, this.jumpSpriteSheet.getWidth() / jumpAnimFrameNb, this.jumpSpriteSheet.getHeight());
        TextureRegion[] temp1 = new TextureRegion[(jumpAnimFrameNb * 1)];
        TextureRegion[] temp2 = new TextureRegion[(jumpAnimFrameNb * 1)];
        int index = 0;
        for (int i = 0; i < 1; i++) {
            for (int j = 0; j < jumpAnimFrameNb; j++) {
                tmp1[i][j].flip(false, false);
                temp1[index] = tmp1[i][j];
                tmp2[i][j].flip(true, false);
                temp2[index] = tmp2[i][j];
                index++;
            }
        }
        this.jumpAnimRight = new Animation(0.1f, temp1);
        this.jumpAnimLeft = new Animation(0.1f, temp2);
        this.jumpAnimRight.setPlayMode(PlayMode.NORMAL);
    }

    public void render(SpriteBatch batch, ShapeRenderer shape) {
        this.stateTime += Gdx.graphics.getDeltaTime();
        if (this.hasBeenHit) {
            if (this.invulnerabilityFrameCount % GameConstants.PLAYER_BLINK_SPEED == 0) {
                this.display = !this.display;
            }
            this.invulnerabilityFrameCount--;
            if (this.invulnerabilityFrameCount < 0) {
                this.hasBeenHit = false;
            }
            if (!this.display) {
                return;
            }
        }
        batch.begin();
        Rectangle drawingPos = new Rectangle(this.position.getX(), this.position.getY(), this.position.getWidth(), this.position.getHeight());
        if (this.isJumping) {
            if (this.lastXVelocity > 0.0f || this.xVelocity > 0.0f) {
                batch.draw((TextureRegion)this.jumpAnimRight.getKeyFrames()[3], drawingPos.getX(), drawingPos.getY());
            } else if (this.lastXVelocity < 0.0f || this.xVelocity < 0.0f) {
                batch.draw((TextureRegion)this.jumpAnimLeft.getKeyFrames()[3], drawingPos.getX(), drawingPos.getY());
            }
        } else if (this.isFalling) {
            if (this.lastXVelocity > 0.0f || this.xVelocity > 0.0f) {
                batch.draw((TextureRegion)this.jumpAnimRight.getKeyFrames()[6], drawingPos.getX(), drawingPos.getY());
            } else if (this.lastXVelocity < 0.0f || this.xVelocity < 0.0f) {
                batch.draw((TextureRegion)this.jumpAnimLeft.getKeyFrames()[6], drawingPos.getX(), drawingPos.getY());
            }
        } else if (this.xVelocity < 0.0f) {
            batch.draw((TextureRegion)this.walkingAnimLeft.getKeyFrame(this.stateTime, true), drawingPos.getX(), drawingPos.getY() - 18.0f);
        } else if (this.xVelocity > 0.0f) {
            batch.draw((TextureRegion)this.walkingAnimRight.getKeyFrame(this.stateTime, true), drawingPos.getX(), drawingPos.getY() - 18.0f);
        } else if (this.lastXVelocity > 0.0f) {
            batch.draw((TextureRegion)this.standAnimRight.getKeyFrame(this.stateTime, true), drawingPos.getX(), drawingPos.getY());
        } else {
            batch.draw((TextureRegion)this.standAnimRight.getKeyFrame(this.stateTime, true), drawingPos.getX(), drawingPos.getY());
        }
        batch.end();
        if (GameSettings.DEBUG_MODE) {
            if (this.jumpBox != null) {
                shape.begin(ShapeType.Line);
                shape.rect(this.jumpBox.getX(), this.jumpBox.getY(), this.jumpBox.getWidth(), this.jumpBox.getHeight());
                shape.end();
            }
            if (this.hitBox != null) {
                shape.begin(ShapeType.Line);
                shape.rect(this.hitBox.getX(), this.hitBox.getY(), this.hitBox.getWidth(), this.hitBox.getHeight());
                shape.end();
            }
        }
    }

    public void update() {
        Vector2 center = new Vector2();
        this.position.getCenter(center);
        this.position.setX(this.position.getX() + this.xVelocity);
        this.jumpBox.setX(this.jumpBox.getX() + this.xVelocity);
        this.hitBox.setX(this.hitBox.getX() + this.xVelocity);
        this.hitBox.setWidth(0.2f * this.position.getWidth());
        if (center.x > RunnerDora.camera.position.x) {
            RunnerDora.camera.position.x = center.x;
        } else if (center.x < RunnerDora.camera.position.x && RunnerDora.camera.position.x > RunnerDora.camera.viewportWidth / 2.0f) {
            RunnerDora.camera.position.x = center.x;
        }
        this.position.setY(this.position.getY() + this.yVelocity);
        this.jumpBox.setY(this.jumpBox.getY() + this.yVelocity);
        this.hitBox.setY(this.hitBox.getY() + this.yVelocity);
        Vector3 vector3;
        if (center.y > RunnerDora.camera.position.y + ((10.0f * RunnerDora.camera.viewportHeight) / 100.0f)) {
            vector3 = RunnerDora.camera.position;
            vector3.y += this.yVelocity;
        } else if (center.y < RunnerDora.camera.position.y && RunnerDora.camera.position.y > RunnerDora.camera.viewportHeight / 2.0f) {
            vector3 = RunnerDora.camera.position;
            vector3.y -= this.yVelocity;
        }
        if (RunnerDora.camera.position.y < RunnerDora.camera.viewportHeight / 2.0f) {
            RunnerDora.camera.position.y = RunnerDora.camera.viewportHeight / 2.0f;
        }
        if (this.isJumping) {
            if (this.yVelocity > 0.0f) {
                this.yVelocity -= 0.5f;
            } else {
                stopJump();
            }
        }
        if (this.isFalling) {
            this.yVelocity -= 0.5f;
            if (this.yVelocity < -10.0f) {
                this.yVelocity = -10.0f;
            }
        }
    }

    public void jump() {
        if (!this.isJumping && !this.isFalling) {
            this.yVelocity = 15.0f;
            this.isJumping = true;
        }
    }

    public boolean isJumping() {
        return this.isJumping;
    }

    public boolean isFalling() {
        return this.isFalling;
    }

    public Rectangle getPosition() {
        return this.position;
    }

    public void setJumpBox(Rectangle jumpHitBox) {
        this.jumpBox = jumpHitBox;
    }

    public Rectangle getJumpBox() {
        return this.jumpBox;
    }

    public void stopJump() {
        this.isJumping = false;
        this.isFalling = true;
    }

    public void fall() {
        if (!this.isJumping) {
            this.isFalling = true;
        }
    }

    public void stopFall() {
        this.isFalling = false;
    }

    public void setXVelocity(float velocity) {
        if (this.xVelocity != GameConstants.MOVE_NOT) {
            this.lastXVelocity = this.xVelocity;
        }
        this.xVelocity = velocity;
    }

    public float getXVelocity() {
        return this.xVelocity;
    }

    public void setYVelocity(float velocity) {
        this.yVelocity = velocity;
    }

    public Rectangle getHitBox() {
        return this.hitBox;
    }

    public void setHitBox(Rectangle hitBox) {
        this.hitBox = hitBox;
    }

    public void smallBounce() {
        this.yVelocity = 7.0f;
        this.isJumping = true;
    }

    public void addPoints(int points) {
        this.score += this.multiplier * points;
    }

    public void raiseMultiplier() {
        this.multiplier *= 2;
    }

    public void hit() {
        if (!this.hasBeenHit) {
            this.life--;
            this.multiplier = 1;
            this.hasBeenHit = true;
            this.invulnerabilityFrameCount = GameConstants.PLAYER_INVULNERABILITY_SECONDS * 60;
        }
    }

    public int getScore() {
        return this.score;
    }

    public int getLife() {
        return this.life;
    }

    public void moveY(float deltaY) {
        this.position.setY(this.position.getY() + deltaY);
        this.jumpBox.setY(this.jumpBox.getY() + deltaY);
        this.hitBox.setY(this.hitBox.getY() + deltaY);
    }
}
