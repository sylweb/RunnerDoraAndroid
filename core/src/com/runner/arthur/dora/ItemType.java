package com.runner.arthur.dora;

public enum ItemType {
    RED_ICE_CREAM(0, 52.0f, 52, 8),
    GOLD_COIN(1, 41.0f, 42, 8),
    GREEN_FLAG(2, 128.0f, 128, 2),
    YELLOW_FLAG(3, 128.0f, 128, 2);
    
    private float height;
    private int id;
    private int nbOfAnimFrame;
    private float width;

    private ItemType(int id, float w, int h, int af) {
        this.id = id;
        this.width = w;
        this.height = (float) h;
        this.nbOfAnimFrame = af;
    }

    public int getTypeId() {
        return this.id;
    }

    public float getWidth() {
        return this.width;
    }

    public float getHeight() {
        return this.height;
    }

    public int getNbOfAnimFrame() {
        return this.nbOfAnimFrame;
    }
}
