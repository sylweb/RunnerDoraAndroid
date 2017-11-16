package com.runner.arthur.dora;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.net.HttpStatus;
import java.lang.reflect.Array;
import java.util.ArrayList;

public class Level {
    public ArrayList<Ennemy> ennemies = new ArrayList();
    protected Tile[][] map;
    protected int mapXElement = 0;
    protected int mapYElement = 0;
    protected int nbOfTilesInSet = 0;
    protected int tileHeight = 0;
    protected int tileWidth = 0;

    public Level(int levelNb) {
        RessourceContainer.loadLevelTiles(levelNb);
        switch (levelNb) {
            case 1:
                this.tileWidth = 90;
                this.tileHeight = 90;
                this.mapXElement = 100;
                this.mapYElement = 100;
                this.nbOfTilesInSet = 3;
                try {
                    readMap("level/dora-lvl1_land.csv");
                    readEnnemies("level/dora-lvl1_ennemies.csv");
                    return;
                } catch (Exception e) {
                    System.exit(-1);
                    return;
                }
            case 2:
                this.tileWidth = 90;
                this.tileHeight = 90;
                this.mapXElement = 3000;
                this.mapYElement = 10;
                this.nbOfTilesInSet = 3;
                try {
                    readMap("level/dora-lvl2_land.csv");
                    return;
                } catch (Exception e2) {
                    System.exit(-1);
                    return;
                }
            case 3:
                this.tileWidth = 90;
                this.tileHeight = 90;
                this.mapXElement = HttpStatus.SC_OK;
                this.mapYElement = 10;
                this.nbOfTilesInSet = 5;
                try {
                    readMap("level/dora-lvl3_land.csv");
                    readEnnemies("level/dora-lvl3_ennemies.csv");
                    readItems("level/dora-lvl3_items.csv");
                    return;
                } catch (Exception e3) {
                    System.exit(-1);
                    return;
                }
            default:
                System.exit(1);
                return;
        }
    }

    private void readMap(String mapData) throws Exception {
        int i;
        String cvsSplitBy = ",";
        int[][] data = (int[][]) Array.newInstance(Integer.TYPE, new int[]{this.mapYElement, this.mapXElement});
        String[] lines = Gdx.files.internal(mapData).readString().split("\r\n");
        for (i = 0; i < lines.length; i++) {
            int j;
            String[] values = lines[i].split(cvsSplitBy);
            for (j = 0; j < values.length; j++) {
                data[i][j] = Integer.valueOf(values[j]).intValue();
            }
        }
        this.map = (Tile[][]) Array.newInstance(Tile.class, new int[]{this.mapYElement, this.mapXElement});
        for (i = 0; i < this.mapYElement; i++) {
            for (int j = 0; j < this.mapXElement; j++) {
                if (data[i][j] == -1) {
                    this.map[i][j] = null;
                } else {
                    this.map[i][j] = new Tile((float) (this.tileWidth * j), (float) ((this.mapYElement - (i + 1)) * this.tileHeight), (float) this.tileWidth, (float) this.tileHeight, data[i][j]);
                }
            }
        }
    }

    private void readEnnemies(String ennemyData) throws Exception {
        int i;
        String cvsSplitBy = ",";
        int[][] data = (int[][]) Array.newInstance(Integer.TYPE, new int[]{this.mapYElement, this.mapXElement});
        String[] lines = Gdx.files.internal(ennemyData).readString().split("\r\n");
        for (i = 0; i < lines.length; i++) {
            int j;
            String[] values = lines[i].split(cvsSplitBy);
            for (j = 0; j < values.length; j++) {
                data[i][j] = Integer.valueOf(values[j]).intValue();
            }
        }
        for (i = 0; i < this.mapYElement; i++) {
            for (int j = 0; j < this.mapXElement; j++) {
                if (data[i][j] != -1) {
                    float x = (float) (this.tileWidth * j);
                    float y = (float) ((this.mapYElement - (i + 1)) * this.tileHeight);
                    switch (data[i][j]) {
                        case 0:
                            x -= Bee.SPRITE_WIDTH - ((float) this.tileWidth);
                            y += (2.0f * Bee.SPRITE_HEIGHT) - ((float) this.tileHeight);
                            RessourceContainer.loadBee();
                            this.ennemies.add(new Bee(x, y));
                            break;
                        case 1:
                            x -= Pirate.SPRITE_WIDTH - ((float) this.tileWidth);
                            RessourceContainer.loadPirate();
                            this.ennemies.add(new Pirate(x, y));
                            break;
                        case 2:
                            RessourceContainer.loadIceCreamTruck();
                            this.ennemies.add(new IceCreamTruck(x, y));
                            break;
                        default:
                            break;
                    }
                }
            }
        }
    }

    private void readItems(String itemData) throws Exception {
        int i;
        int j;
        String cvsSplitBy = ",";
        int[][] data = (int[][]) Array.newInstance(Integer.TYPE, new int[]{this.mapYElement, this.mapXElement});
        String[] lines = Gdx.files.internal(itemData).readString().split("\r\n");
        for (i = 0; i < lines.length; i++) {
            String[] values = lines[i].split(cvsSplitBy);
            for (j = 0; j < values.length; j++) {
                data[i][j] = Integer.valueOf(values[j]).intValue();
            }
        }
        for (i = 0; i < this.mapYElement; i++) {
            for (j = 0; j < this.mapXElement; j++) {
                if (data[i][j] != -1) {
                    float x = (float) (this.tileWidth * j);
                    float y = (float) ((this.mapYElement - (i + 1)) * this.tileHeight);
                    switch (data[i][j]) {
                        case 0:
                            RessourceContainer.loadIceCreamItem();
                            RunnerDora.items.add(new Item(x, y, ItemType.RED_ICE_CREAM.getTypeId()));
                            break;
                        case 1:
                            RessourceContainer.loadGoldCoinItem();
                            RunnerDora.items.add(new Item(x, y + (ItemType.GOLD_COIN.getHeight() / 2.0f), ItemType.GOLD_COIN.getTypeId()));
                            break;
                        case 2:
                            RessourceContainer.loadYellowFlagItem();
                            RunnerDora.items.add(new Item(x, y, ItemType.YELLOW_FLAG.getTypeId()));
                            break;
                        case 3:
                            RessourceContainer.loadGreenFlagItem();
                            RunnerDora.items.add(new Item(x, y, ItemType.GREEN_FLAG.getTypeId()));
                            break;
                        default:
                            break;
                    }
                }
            }
        }
    }

    public void render(SpriteBatch batch, ShapeRenderer shape) {
        Polygon camPoly = ShapeTools.rectangleToPolygon(new Rectangle(RunnerDora.camera.position.x - (RunnerDora.camera.viewportWidth / 2.0f), RunnerDora.camera.position.y - (RunnerDora.camera.viewportHeight / 2.0f), RunnerDora.camera.viewportWidth, RunnerDora.camera.viewportHeight));
        int i = 0;
        while (i < this.mapYElement) {
            int j = 0;
            while (j < this.mapXElement) {
                if (this.map[i][j] != null && Intersector.overlapConvexPolygons(this.map[i][j].getHitBox(), camPoly)) {
                    batch.begin();
                    batch.draw((TextureRegion)RessourceContainer.levelTiles.getKeyFrames()[this.map[i][j].getId()], this.map[i][j].getDrawBox().getX(), this.map[i][j].getDrawBox().getY());
                    batch.end();
                    if (GameSettings.DEBUG_MODE && this.map[i][j] != null) {
                        shape.begin(ShapeType.Line);
                        shape.polygon(this.map[i][j].getHitBox().getVertices());
                        shape.end();
                    }
                }
                j++;
            }
            i++;
        }
        for (i = 0; i < this.ennemies.size(); i++) {
            ((Ennemy) this.ennemies.get(i)).render(batch, shape);
        }
    }

    public void update() {
        for (int i = 0; i < this.ennemies.size(); i++) {
            ((Ennemy) this.ennemies.get(i)).update();
        }
    }

    public ArrayList<Tile> getTiles() {
        Polygon camPoly = ShapeTools.rectangleToPolygon(new Rectangle(RunnerDora.camera.position.x - (RunnerDora.camera.viewportWidth / 2.0f), RunnerDora.camera.position.y - (RunnerDora.camera.viewportHeight / 2.0f), RunnerDora.camera.viewportWidth, RunnerDora.camera.viewportHeight));
        ArrayList<Tile> tiles = new ArrayList();
        int i = 0;
        while (i < this.mapYElement) {
            int j = 0;
            while (j < this.mapXElement) {
                if (this.map[i][j] != null && Intersector.overlapConvexPolygons(this.map[i][j].getHitBox(), camPoly)) {
                    tiles.add(this.map[i][j]);
                }
                j++;
            }
            i++;
        }
        return tiles;
    }

    public int getMapXElement() {
        return this.mapXElement;
    }

    public int getMapYElement() {
        return this.mapYElement;
    }

    public ArrayList<Ennemy> getEnnemies() {
        return this.ennemies;
    }

    public int getTileHeight() {
        return this.tileHeight;
    }

    public int getTileWidth() {
        return this.tileWidth;
    }
}
