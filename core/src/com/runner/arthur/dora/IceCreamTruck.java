package com.runner.arthur.dora;

import com.badlogic.gdx.math.Rectangle;

public class IceCreamTruck extends Enemy {
    public static int ANIMATION_SPEED = 60;
    public static String ANIM_IMG = "gfx/ice_cream_truck.png";
    public static int ANIM_NB_OF_IMG = 1;
    public static String DEATH_IMG = "gfx/ice_cream_truck.png";
    public static float DELTA_X_MOVE = 0.0f;
    public static float DELTA_Y_MOVE = 0.0f;
    public static int LIFE = 1;
    public static float SPRITE_HEIGHT = 250.0f;
    public static float SPRITE_WIDTH = 416.0f;
    public static float VELOCITY_X = 1.0f;

    public IceCreamTruck(float x, float y) {
        super(x, y, SPRITE_WIDTH, SPRITE_HEIGHT, LIFE, DELTA_X_MOVE, DELTA_Y_MOVE, ResourceContainer.icTruckAnimLeft, ResourceContainer.icTruckAnimRight, ResourceContainer.icTruckDeadSprite, ResourceContainer.icTruckDeadSprite, VELOCITY_X);
        this.hitBox = new Rectangle(0.0f, 0.0f, 0.0f, 0.0f);
    }
}
