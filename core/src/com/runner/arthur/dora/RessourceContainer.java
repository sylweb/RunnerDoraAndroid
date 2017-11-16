package com.runner.arthur.dora;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Animation.PlayMode;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

@SuppressWarnings("unchecked")
public class RessourceContainer {
    public static Animation beeAnimLeft;
    public static Animation beeAnimRight;
    public static Texture beeDeadSpriteLeft;
    public static Texture beeDeadSpriteRight;
    public static Texture beeSprites;
    public static Animation goldCoinAnim;
    public static Texture goldCoinSprites;
    public static Animation greenFlagAnim;
    public static Texture greenFlagSprites;
    public static Animation icTruckAnimLeft;
    public static Animation icTruckAnimRight;
    public static Texture icTruckDeadSprite;
    public static Texture icTruckSprites;
    public static Texture levelTileSet;
    public static Animation levelTiles;
    public static Texture libGdxLogo;
    public static Animation pirateAnimLeft;
    public static Animation pirateAnimRight;
    public static Texture pirateDeadSpriteLeft;
    public static Texture pirateDeadSpriteRight;
    public static Texture pirateSprites;
    public static Animation playerHeartAnim;
    public static Texture playerHeartSprites;
    public static Animation redIceCreamAnim;
    public static Texture redIceCreamSprites;
    public static Animation yellowFlagAnim;
    public static Texture yellowFlagSprites;

    public static void loadLevelTiles(int levelNb) {
        int bloc_level = 0;
        if (levelNb == 1) {
            bloc_level = 3;
        } else if (levelNb == 2) {
            bloc_level = 3;
        } else if (levelNb == 3) {
            bloc_level = 5;
        }
        levelTileSet = new Texture(Gdx.files.internal("gfx/land_lvl" + levelNb + "_90_90.png"));
        TextureRegion[][] tmp = TextureRegion.split(levelTileSet, 90, 90);
        TextureRegion[] temp2 = new TextureRegion[bloc_level];
        int index = 0;
        int i = 0;
        while (i < 1) {
            int j = 0;
            int index2 = index;
            while (j < bloc_level) {
                index = index2 + 1;
                temp2[index2] = tmp[i][j];
                j++;
                index2 = index;
            }
            i++;
            index = index2;
        }
        levelTiles = new Animation(0.025f, temp2);
    }

    public static void loadPirate() {
        if (pirateSprites == null) {
            pirateSprites = new Texture(Gdx.files.internal(Pirate.ANIM_IMG));
            pirateDeadSpriteRight = new Texture(Gdx.files.internal(Pirate.DEATH_IMG + "_right.png"));
            pirateDeadSpriteLeft = new Texture(Gdx.files.internal(Pirate.DEATH_IMG + "_left.png"));
            TextureRegion[][] tmp = TextureRegion.split(pirateSprites, (int) Pirate.SPRITE_WIDTH, (int) Pirate.SPRITE_HEIGHT);
            TextureRegion[][] tmp1 = TextureRegion.split(pirateSprites, (int) Pirate.SPRITE_WIDTH, (int) Pirate.SPRITE_HEIGHT);
            TextureRegion[] temp2 = new TextureRegion[Pirate.ANIM_NB_OF_IMG];
            TextureRegion[] temp3 = new TextureRegion[Pirate.ANIM_NB_OF_IMG];
            int index = 0;
            for (int i = 0; i < 1; i++) {
                for (int j = 0; j < Pirate.ANIM_NB_OF_IMG; j++) {
                    tmp[i][j].flip(false, false);
                    temp2[index] = tmp[i][j];
                    tmp1[i][j].flip(true, false);
                    temp3[index] = tmp1[i][j];
                    index++;
                }
            }
            pirateAnimRight = new Animation(0.1f, temp3);
            pirateAnimLeft = new Animation(0.1f, temp2);
            pirateAnimRight.setPlayMode(PlayMode.LOOP);
            pirateAnimLeft.setPlayMode(PlayMode.LOOP);
        }
    }

    public static void loadBee() {
        if (beeSprites == null) {
            beeSprites = new Texture(Gdx.files.internal(Bee.ANIM_IMG));
            beeDeadSpriteRight = new Texture(Gdx.files.internal(Bee.DEATH_IMG + "_right.png"));
            beeDeadSpriteLeft = new Texture(Gdx.files.internal(Bee.DEATH_IMG + "_left.png"));
            TextureRegion[][] tmp = TextureRegion.split(beeSprites, (int) Bee.SPRITE_WIDTH, (int) Bee.SPRITE_HEIGHT);
            TextureRegion[][] tmp1 = TextureRegion.split(beeSprites, (int) Bee.SPRITE_WIDTH, (int) Bee.SPRITE_HEIGHT);
            TextureRegion[] temp2 = new TextureRegion[Bee.ANIM_NB_OF_IMG];
            TextureRegion[] temp3 = new TextureRegion[Bee.ANIM_NB_OF_IMG];
            int index = 0;
            for (int i = 0; i < 1; i++) {
                for (int j = 0; j < Bee.ANIM_NB_OF_IMG; j++) {
                    tmp[i][j].flip(false, false);
                    temp2[index] = tmp[i][j];
                    tmp1[i][j].flip(true, false);
                    temp3[index] = tmp1[i][j];
                    index++;
                }
            }
            beeAnimRight = new Animation(0.1f, temp3);
            beeAnimLeft = new Animation(0.1f, temp2);
            beeAnimRight.setPlayMode(PlayMode.LOOP);
            beeAnimLeft.setPlayMode(PlayMode.LOOP);
        }
    }

    public static void loadIceCreamTruck() {
        if (icTruckSprites == null) {
            icTruckSprites = new Texture(Gdx.files.internal(IceCreamTruck.ANIM_IMG));
            icTruckDeadSprite = new Texture(Gdx.files.internal(IceCreamTruck.DEATH_IMG));
            TextureRegion[][] tmp = TextureRegion.split(icTruckSprites, (int) IceCreamTruck.SPRITE_WIDTH, (int) IceCreamTruck.SPRITE_HEIGHT);
            TextureRegion[][] tmp1 = TextureRegion.split(icTruckSprites, (int) IceCreamTruck.SPRITE_WIDTH, (int) IceCreamTruck.SPRITE_HEIGHT);
            TextureRegion[] temp2 = new TextureRegion[IceCreamTruck.ANIM_NB_OF_IMG];
            TextureRegion[] temp3 = new TextureRegion[IceCreamTruck.ANIM_NB_OF_IMG];
            int index = 0;
            for (int i = 0; i < 1; i++) {
                for (int j = 0; j < IceCreamTruck.ANIM_NB_OF_IMG; j++) {
                    tmp[i][j].flip(false, false);
                    temp2[index] = tmp[i][j];
                    tmp1[i][j].flip(true, false);
                    temp3[index] = tmp1[i][j];
                    index++;
                }
            }
            icTruckAnimRight = new Animation(0.1f, temp2);
            icTruckAnimLeft = new Animation(0.1f, temp3);
            icTruckAnimRight.setPlayMode(PlayMode.LOOP);
            icTruckAnimLeft.setPlayMode(PlayMode.LOOP);
        }
    }

    public static void loadIceCreamItem() {
        if (redIceCreamSprites == null) {
            redIceCreamSprites = new Texture(Gdx.files.internal("gfx/ice_cream_red.png"));
            TextureRegion[][] tmp = TextureRegion.split(redIceCreamSprites, (int) ItemType.RED_ICE_CREAM.getWidth(), (int) ItemType.RED_ICE_CREAM.getHeight());
            TextureRegion[] temp2 = new TextureRegion[ItemType.RED_ICE_CREAM.getNbOfAnimFrame()];
            int index = 0;
            for (int i = 0; i < 1; i++) {
                for (int j = 0; j < ItemType.RED_ICE_CREAM.getNbOfAnimFrame(); j++) {
                    tmp[i][j].flip(false, false);
                    temp2[index] = tmp[i][j];
                    index++;
                }
            }
            redIceCreamAnim = new Animation(0.1f, temp2);
            redIceCreamAnim.setPlayMode(PlayMode.LOOP);
        }
    }

    public static void loadGoldCoinItem() {
        if (goldCoinSprites == null) {
            goldCoinSprites = new Texture(Gdx.files.internal("gfx/coin_gold.png"));
            TextureRegion[][] tmp = TextureRegion.split(goldCoinSprites, (int) ItemType.GOLD_COIN.getWidth(), (int) ItemType.GOLD_COIN.getHeight());
            TextureRegion[] temp2 = new TextureRegion[ItemType.GOLD_COIN.getNbOfAnimFrame()];
            int index = 0;
            for (int i = 0; i < 1; i++) {
                for (int j = 0; j < ItemType.GOLD_COIN.getNbOfAnimFrame(); j++) {
                    tmp[i][j].flip(false, false);
                    temp2[index] = tmp[i][j];
                    index++;
                }
            }
            goldCoinAnim = new Animation(0.1f, temp2);
            goldCoinAnim.setPlayMode(PlayMode.LOOP);
        }
    }

    public static void loadYellowFlagItem() {
        if (yellowFlagSprites == null) {
            yellowFlagSprites = new Texture(Gdx.files.internal("gfx/yellow_flag.png"));
            TextureRegion[][] tmp = TextureRegion.split(yellowFlagSprites, (int) ItemType.YELLOW_FLAG.getWidth(), (int) ItemType.YELLOW_FLAG.getHeight());
            TextureRegion[] temp2 = new TextureRegion[ItemType.YELLOW_FLAG.getNbOfAnimFrame()];
            int index = 0;
            for (int i = 0; i < 1; i++) {
                for (int j = 0; j < ItemType.YELLOW_FLAG.getNbOfAnimFrame(); j++) {
                    tmp[i][j].flip(false, false);
                    temp2[index] = tmp[i][j];
                    index++;
                }
            }
            yellowFlagAnim = new Animation(0.2f, temp2);
            yellowFlagAnim.setPlayMode(PlayMode.LOOP);
        }
    }

    public static void loadGreenFlagItem() {
        if (greenFlagSprites == null) {
            greenFlagSprites = new Texture(Gdx.files.internal("gfx/green_flag.png"));
            TextureRegion[][] tmp = TextureRegion.split(greenFlagSprites, (int) ItemType.GREEN_FLAG.getWidth(), (int) ItemType.GREEN_FLAG.getHeight());
            TextureRegion[] temp2 = new TextureRegion[ItemType.GREEN_FLAG.getNbOfAnimFrame()];
            int index = 0;
            for (int i = 0; i < 1; i++) {
                for (int j = 0; j < ItemType.GREEN_FLAG.getNbOfAnimFrame(); j++) {
                    tmp[i][j].flip(false, false);
                    temp2[index] = tmp[i][j];
                    index++;
                }
            }
            greenFlagAnim = new Animation(0.2f, temp2);
            greenFlagAnim.setPlayMode(PlayMode.LOOP);
        }
    }

    public static void loadPlayerLifeHeart() {
        if (playerHeartSprites == null) {
            playerHeartSprites = new Texture(Gdx.files.internal("gfx/player_heart.png"));
            TextureRegion[][] tmp = TextureRegion.split(playerHeartSprites, 64, 64);
            TextureRegion[] temp2 = new TextureRegion[3];
            int index = 0;
            int i = 0;
            while (i < 1) {
                int j = 0;
                int index2 = index;
                while (j < 3) {
                    index = index2 + 1;
                    temp2[index2] = tmp[i][j];
                    j++;
                    index2 = index;
                }
                i++;
                index = index2;
            }
            playerHeartAnim = new Animation(0.025f, temp2);
            playerHeartAnim.setPlayMode(PlayMode.NORMAL);
        }
    }

    public static void loadLibGdxLogo() {
        if (libGdxLogo == null) {
            libGdxLogo = new Texture(Gdx.files.internal("gfx/libgdx.png"));
        }
    }
}
