package com.runner.arthur.dora;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public class Bee extends Ennemy {
    public static int ANIMATION_SPEED = 75;
    public static String ANIM_IMG = "gfx/bee.png";
    public static int ANIM_NB_OF_IMG = 2;
    public static String DEATH_IMG = "gfx/bee_dead";
    public static float DELTA_X_MOVE = 250.0f;
    public static float DELTA_Y_MOVE = 0.0f;
    public static int LIFE = 1;
    public static float SPRITE_HEIGHT = 64.0f;
    public static float SPRITE_WIDTH = 64.0f;
    public static float VELOCITY_X = 3.0f;

    public Bee(float x, float y) {
        super(x, y, SPRITE_WIDTH, SPRITE_HEIGHT, LIFE, DELTA_X_MOVE, DELTA_Y_MOVE, RessourceContainer.beeAnimLeft, RessourceContainer.beeAnimRight, RessourceContainer.beeDeadSpriteLeft, RessourceContainer.beeDeadSpriteRight, VELOCITY_X);
        Vector2 vec = new Vector2();
        this.position.getCenter(vec);
        this.hitBox = new Rectangle(vec.x - (0.2f * this.position.getWidth()), vec.y - (0.2f * this.position.getHeight()), 0.4f * this.position.getWidth(), 0.4f * this.position.getHeight());
    }

    public void reset() {
        super.reset();
        Vector2 vec = new Vector2();
        this.position.getCenter(vec);
        this.hitBox = new Rectangle(vec.x - (this.position.getWidth() * 0.2f), vec.y - (this.position.getHeight() * 0.2f), 0.4f * this.position.getWidth(), 0.4f * this.position.getHeight());
    }
}
