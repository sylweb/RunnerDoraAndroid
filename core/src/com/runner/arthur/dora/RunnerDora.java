package com.runner.arthur.dora;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.controllers.Controller;
import com.badlogic.gdx.controllers.ControllerListener;
import com.badlogic.gdx.controllers.PovDirection;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import java.util.ArrayList;

public class RunnerDora extends ApplicationAdapter implements ControllerListener {

	public static OrthographicCamera camera;
	public static ArrayList<Item> items = new ArrayList<Item>();
	public static GameStatus status = GameStatus.LOADING;
	private SpriteBatch batch;
	private BitmapFont bigFont;
	private Level currentLevel;
	private float gameTime = 0.0f;
	float game_over_text_height = 0.0f;
	float game_over_text_width = 0.0f;
	private Rectangle leftArrowRec;
	private Texture leftArrowSprite;
	private SplashScreen libGdxScreen;
	private Music mainTheme;
	private BitmapFont mediumFont;
	private Background myBack;
	private Player myPlayer;
	private Rectangle rightArrowRec;
	private Texture rightArrowSprite;
	private ShapeRenderer shape;
	//private TalkToTheLauncher talkInterface;
	private boolean touchHasBeenReset = true;
	private Viewport viewport;
	float win_text_height = 0.0f;
	float win_text_width = 0.0f;
	float x_size_factor = 1.0f;
	float y_size_factor = 1.0f;

	public RunnerDora(TalkToTheLauncher talk) {
		/*this.talkInterface = talk;*/
	}

	public void create() {
		this.batch = new SpriteBatch();
		this.shape = new ShapeRenderer();
		camera = new OrthographicCamera();
		this.viewport = new FitViewport(1280.0f, 720.0f, camera);
		this.viewport.apply();
		camera.position.set(this.viewport.getWorldWidth() / 2.0f, this.viewport.getWorldHeight() / 2.0f, 0.0f);
		loadGame();
	}

	public void loadGame() {
		ResourceContainer.loadLibGdxLogo();
		this.myBack = new Background();
		this.currentLevel = new Level(3);
		createPlayer();
		ResourceContainer.loadPlayerLifeHeart();
		this.mediumFont = new BitmapFont(Gdx.files.internal("mediumFont.fnt"), Gdx.files.internal("mediumFont.png"), false);
		this.mediumFont.getData().setScale(1.5f);
		this.bigFont = new BitmapFont(Gdx.files.internal("bigFont.fnt"), Gdx.files.internal("bigFont.png"), false);
		this.bigFont.getData().setScale(2.0f);
		GlyphLayout layout = new GlyphLayout();
		layout.setText(this.bigFont, "GAME OVER");
		this.game_over_text_width = layout.width;
		this.game_over_text_height = layout.height;
		layout.setText(this.bigFont, "YOU WIN");
		this.win_text_width = layout.width;
		this.win_text_height = layout.height;
		this.mainTheme = Gdx.audio.newMusic(Gdx.files.internal("music/dora_main_theme.ogg"));
		this.mainTheme.setLooping(true);
		this.rightArrowSprite = new Texture(Gdx.files.internal("gfx/arrow_right_red.png"));
		this.rightArrowRec = new Rectangle();
		this.leftArrowSprite = new Texture(Gdx.files.internal("gfx/arrow_left_blue.png"));
		this.leftArrowRec = new Rectangle();
		this.libGdxScreen = new SplashScreen(6.0f, ResourceContainer.libGdxLogo);
		this.libGdxScreen.start(this.gameTime);
	}

	public void render() {
		this.gameTime += Gdx.graphics.getDeltaTime();
		update();
		camera.update();
		this.batch.setProjectionMatrix(camera.combined);
		this.shape.setProjectionMatrix(camera.combined);
		Gdx.gl.glClearColor(0.0f, 0.0f, 0.0f, 1.0f);
		Gdx.gl.glClear(16640);
		if (this.libGdxScreen.isFinished()) {
			if (status == GameStatus.LOADING && this.libGdxScreen.isFinished()) {
				if (GameSettings.SOUND_ON) {
					this.mainTheme.play();
				}
				status = GameStatus.WAIT_FOR_PLAYER_TO_START;
			}
			this.myBack.render(this.batch, this.shape);
			for (int i = 0; i < items.size(); i++) {
				items.get(i).render(this.batch);
			}
			this.currentLevel.render(this.batch, this.shape);
			this.myPlayer.render(this.batch, this.shape);
			this.batch.begin();
			CharSequence score = "Score : " + this.myPlayer.getScore();
			float x_pos = (camera.position.x - (camera.viewportWidth / 2.0f)) + 10.0f;
			float y_pos = (camera.position.y + (camera.viewportHeight / 2.0f)) - 10.0f;
			this.mediumFont.setColor(Color.GOLD);
			this.mediumFont.draw(this.batch, score, x_pos, y_pos);
			x_pos = (camera.position.x + (camera.viewportWidth / 2.0f)) - 64.0f;
			y_pos = (camera.position.y + (camera.viewportHeight / 2.0f)) - 64.0f;
			int life = this.myPlayer.getLife();
			if (life < 0) {
				life = 0;
			}
			this.batch.draw((TextureRegion) ResourceContainer.playerHeartAnim.getKeyFrames()[life], x_pos, y_pos);
			if (status == GameStatus.PLAYER_DIED) {
				this.bigFont.setColor(Color.RED);
				this.bigFont.draw(this.batch, "GAME OVER", camera.position.x - (this.game_over_text_width / 2.0f), camera.position.y + (this.game_over_text_height / 2.0f));
			}
			if (status == GameStatus.PLAYER_WIN) {
				this.bigFont.setColor(Color.GREEN);
				this.bigFont.draw(this.batch, "YOU WIN", camera.position.x - (this.win_text_width / 2.0f), camera.position.y + (this.win_text_height / 2.0f));
			}
			if (GameSettings.PLATFORMER_MODE) {
				float x = (camera.position.x - (camera.viewportWidth / 2.0f)) + 5.0f;
				this.leftArrowRec.x = x;
				this.batch.draw(this.leftArrowSprite, x, 0.0f);
				x = (((float) this.leftArrowSprite.getWidth()) + x) + (((float) this.leftArrowSprite.getWidth()) / 2.0f);
				this.rightArrowRec.x = x;
				this.batch.draw(this.rightArrowSprite, x, 0.0f);
			}
			this.batch.end();
			return;
		}
		this.libGdxScreen.render(this.gameTime, this.batch);
	}

	public void update() {
		if (status != GameStatus.LOADING) {
			collisionManager();
			this.myBack.update(this.myPlayer.getXVelocity());
			this.currentLevel.update();
			this.myPlayer.update();
			testKeys();
		}
	}

	public void collisionManager() {
		int i;
		boolean playerFall = true;
		ArrayList<Tile> landTiles = this.currentLevel.getTiles();
		if (landTiles != null && landTiles.size() > 0) {
			for (i = 0; i < landTiles.size(); i++) {
				Tile tile = landTiles.get(i);
				if (Intersector.overlapConvexPolygons(tile.getHitBox(), ShapeTools.rectangleToPolygon(this.myPlayer.getJumpBox())) && !this.myPlayer.isJumping() && this.myPlayer.getLife() > 0) {
					playerFall = false;
					this.myPlayer.setYVelocity(0.0f);
					if (tile.isTriangle()) {
						this.myPlayer.setYVelocity(tile.calculateYVelocity(this.myPlayer.getXVelocity()));
						break;
					} else {
						this.myPlayer.moveY((tile.getDrawBox().getY() + tile.getDrawBox().getHeight()) - this.myPlayer.getJumpBox().getY());
					}
				}
			}
		}
		if (playerFall || this.myPlayer.getLife() <= 0) {
			this.myPlayer.fall();
		} else {
			this.myPlayer.stopFall();
		}
		ArrayList<Enemy> e = this.currentLevel.getEnemies();
		i = 0;
		while (i < e.size()) {
			if (e.get(i).getHitBox().overlaps(this.myPlayer.getJumpBox()) && !this.myPlayer.isJumping()) {
				e.get(i).hit();
				this.myPlayer.stopFall();
				this.myPlayer.smallBounce();
				this.myPlayer.raiseMultiplier();
			}
			if (e.get(i).getHitBox().overlaps(this.myPlayer.getHitBox()) && e.get(i).isAlive() && !this.myPlayer.isFalling()) {
				this.myPlayer.hit();
			}
			i++;
		}
		for (int j = 0; j < items.size(); j++) {
			Item temp = items.get(j);
			if (temp.isVisible() && temp.isCollectable() && temp.getPosition().overlaps(this.myPlayer.getHitBox())) {
				temp.setVisible(false);
				this.myPlayer.addPoints(temp.getPoints());
			}
			if (temp.getTypeId() == ItemType.GREEN_FLAG.getTypeId() && temp.getPosition().overlaps(this.myPlayer.getHitBox())) {
				status = GameStatus.PLAYER_WIN;
				this.myPlayer.setXVelocity(0.0f);
			}
		}
		if (this.myPlayer.getPosition().getY() < -2.0f * this.myPlayer.getPosition().height) {
			status = GameStatus.PLAYER_DIED;
			this.myPlayer.setXVelocity(0.0f);
		}
	}

	public void testKeys() {
		boolean z = false;
		boolean SCREEN_TOUCHED = false;
		boolean JUMP_TOUCHED = false;
		boolean LEFT_TOUCHED = false;
		boolean RIGHT_TOUCHED = false;
		float x = -100.0f;
		float y = -100.0f;
		for (int i = 0; i < 3; i++) {
			if (Gdx.input.isTouched(i) || Gdx.input.isKeyPressed(62)) {
				SCREEN_TOUCHED = true;
				x = (float) Gdx.input.getX(i);
				y = (float) Gdx.input.getY(i);
				if (GameSettings.PLATFORMER_MODE) {
					if (x > 5.0f && x < ((float) (this.leftArrowSprite.getWidth() + 5)) * this.x_size_factor) {
						LEFT_TOUCHED = true;
					}
					if (x > ((1.5f * ((float) this.leftArrowSprite.getWidth())) + 5.0f) * this.x_size_factor && x < (((1.5f * ((float) this.leftArrowSprite.getWidth())) + 5.0f) + ((float) this.rightArrowSprite.getWidth())) * this.x_size_factor) {
						RIGHT_TOUCHED = true;
					}
					if (x > ((float) Gdx.graphics.getWidth()) / 2.0f) {
						JUMP_TOUCHED = true;
					}
				}
			}
		}
		if (!SCREEN_TOUCHED) {
			this.touchHasBeenReset = true;
		}
		if (SCREEN_TOUCHED) {
			if (!this.touchHasBeenReset) {
				SCREEN_TOUCHED = false;
			}
			this.touchHasBeenReset = false;
		}
		if (SCREEN_TOUCHED) {
			x /= this.x_size_factor;
			y /= this.y_size_factor;
			if (x > 0.0f && x < 100.0f && y > 0.0f && y < 100.0f) {
				if (!GameSettings.DEBUG_MODE) {
					z = true;
				}
				GameSettings.DEBUG_MODE = z;
				return;
			} else if (x <= 1200.0f || x >= 1280.0f || y <= 0.0f || y >= 100.0f) {
				if (status == GameStatus.RUNNING && !GameSettings.PLATFORMER_MODE) {
					this.myPlayer.jump();
				}
				if (status == GameStatus.PLAYER_DIED) {
					resetGame();
					status = GameStatus.WAIT_FOR_PLAYER_TO_START;
					return;
				} else if (status == GameStatus.WAIT_FOR_PLAYER_TO_START) {
					status = GameStatus.RUNNING;
					this.myPlayer.setXVelocity(GameConstants.SCROLL_VELOCITY_X);
					return;
				} else if (status == GameStatus.PLAYER_WIN) {
					resetGame();
					status = GameStatus.WAIT_FOR_PLAYER_TO_START;
					return;
				}
			} else {
				if (!GameSettings.PLATFORMER_MODE) {
					z = true;
				}
				GameSettings.PLATFORMER_MODE = z;
				if (GameSettings.PLATFORMER_MODE) {
					this.myPlayer.setXVelocity(0.0f);
					return;
				} else {
					this.myPlayer.setXVelocity(GameConstants.SCROLL_VELOCITY_X);
					return;
				}
			}
		}
		if (status == GameStatus.RUNNING && GameSettings.PLATFORMER_MODE) {
			float x_velocity;
			if (Gdx.input.isKeyPressed(21) || LEFT_TOUCHED) {
				x_velocity = this.myPlayer.getXVelocity() - GameConstants.X_ACCELERATION;
				if (x_velocity < -1.0f * GameConstants.MAX_X_VELOCITY) {
					x_velocity = -1.0f * GameConstants.MAX_X_VELOCITY;
				}
				this.myPlayer.setXVelocity(x_velocity);
			} else if (Gdx.input.isKeyPressed(22) || RIGHT_TOUCHED) {
				x_velocity = this.myPlayer.getXVelocity() + GameConstants.X_ACCELERATION;
				if (x_velocity > GameConstants.MAX_X_VELOCITY) {
					x_velocity = GameConstants.MAX_X_VELOCITY;
				}
				this.myPlayer.setXVelocity(x_velocity);
			} else {
				x_velocity = this.myPlayer.getXVelocity();
				if (x_velocity > 0.0f) {
					x_velocity -= GameConstants.X_DECELERATION;
					if (x_velocity < 0.0f) {
						x_velocity = 0.0f;
					}
					this.myPlayer.setXVelocity(x_velocity);
				} else if (x_velocity < 0.0f) {
					x_velocity += GameConstants.X_DECELERATION;
					if (x_velocity > 0.0f) {
						x_velocity = 0.0f;
					}
					this.myPlayer.setXVelocity(x_velocity);
				}
			}
			if (JUMP_TOUCHED) {
				this.myPlayer.jump();
			}
		}
		if (Gdx.input.isKeyPressed(Keys.ESCAPE)) {
			Gdx.app.exit();
		}
	}

	private void resetGame() {
		int i;
		camera.position.set(this.viewport.getWorldWidth() / 2.0f, this.viewport.getWorldHeight() / 2.0f, 0.0f);
		createPlayer();
		ArrayList<Enemy> e = this.currentLevel.getEnemies();
		for (i = 0; i < e.size(); i++) {
			e.get(i).reset();
		}
		for (i = 0; i < items.size(); i++) {
			items.get(i).reset();
		}
	}

	private void createPlayer() {
		float x = 0.0f;
		float y = 90.0f;
		for (int i = 0; i < items.size(); i++) {
			if (items.get(i).getTypeId() == ItemType.YELLOW_FLAG.getTypeId()) {
				x = items.get(i).getPosition().getX() + items.get(i).getPosition().getWidth();
				y = items.get(i).getPosition().getY() + items.get(i).getPosition().getHeight();
			}
		}
		this.myPlayer = new Player(new Rectangle(x, y, 126.0f, 138.0f), "gfx/dora_walk.png", "gfx/dora_jump.png", "gfx/dora_stand.png", 12, 5, 8);
		this.myPlayer.setXVelocity(0.0f);
		this.myPlayer.setYVelocity(0.0f);
		this.myPlayer.stopJump();
		this.myPlayer.stopFall();
		Vector2 center = new Vector2();
		this.myPlayer.getPosition().getCenter(center);
		this.myPlayer.setJumpBox(new Rectangle(center.x - 10.0f, this.myPlayer.getPosition().getY(), 20.0f, 20.0f));
		this.myPlayer.setHitBox(new Rectangle(center.x - 20.0f, center.y - 60.0f, 80.0f, 120.0f));
	}

	public void resize(int width, int height) {
		this.viewport.update(width, height);
		GlyphLayout layout = new GlyphLayout();
		layout.setText(this.bigFont, "GAME OVER");
		this.game_over_text_width = layout.width;
		this.game_over_text_height = layout.height;
		layout.setText(this.bigFont, "YOU WIN");
		this.win_text_width = layout.width;
		this.win_text_height = layout.height;
		this.x_size_factor = ((float) Gdx.graphics.getWidth()) / this.viewport.getWorldWidth();
		this.y_size_factor = ((float) Gdx.graphics.getHeight()) / this.viewport.getWorldHeight();
	}

	@Override
	public void connected(Controller controller) {

	}

	@Override
	public void disconnected(Controller controller) {

	}

	@Override
	public boolean buttonDown(Controller controller, int buttonCode) {
		return false;
	}

	@Override
	public boolean buttonUp(Controller controller, int buttonCode) {
		return false;
	}

	@Override
	public boolean axisMoved(Controller controller, int axisCode, float value) {
		return false;
	}

	@Override
	public boolean povMoved(Controller controller, int povCode, PovDirection value) {
		return false;
	}

	@Override
	public boolean xSliderMoved(Controller controller, int sliderCode, boolean value) {
		return false;
	}

	@Override
	public boolean ySliderMoved(Controller controller, int sliderCode, boolean value) {
		return false;
	}

	@Override
	public boolean accelerometerMoved(Controller controller, int accelerometerCode, Vector3 value) {
		return false;
	}
}
