package io.github.binaryguru101.AP.Screens;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import io.github.binaryguru101.AP.Levels.LevelManager;
import io.github.binaryguru101.AP.Levels.LevelsMenu;

public class Main extends ApplicationAdapter {
    private SpriteBatch batch;
    private Texture background;
    private Texture playButton;
    private Viewport viewport;

    private boolean isPlaying;

    private boolean isInLevelMenu;
    private LevelsMenu levelMenu;  // New LevelMenu screen
    private LevelManager level;

    public static final float PIXELS_TO_METERS = 5f;
    public static final float GRAVITY = -8f;
    public static final float WORLD_WIDTH = 20f;
    public static final float WORLD_HEIGHT = 12f;
    public static final float BLOCK_WIDTH = 2.8f;  // Width of the block in meters
    public static final float BLOCK_HEIGHT = 0.4f;

    public void create() {
        batch = new SpriteBatch();
        background = new Texture("Background.jpg");
        playButton = new Texture("Play_button.png");

        viewport = new FitViewport(800, 600);
//        viewport = new ScreenViewport();
//        view= new Scr
        viewport.apply();

        isPlaying = false;
        isInLevelMenu = false;  // Initially not in level menu
    }

    @Override
    public void render() {
        if (isPlaying) {
            // Render the level if the game is started
            level.render();
        } else if (isInLevelMenu) {
            // Render the level menu when we're in the menu
            levelMenu.render(batch);
        } else {
            // Render landing page
            batch.begin();
            batch.draw(background, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
            batch.draw(playButton, Gdx.graphics.getWidth() / 2 - 50, Gdx.graphics.getHeight() / 2 - 105, 100, 50);
            batch.end();

            // Check if the play button is clicked
            if (Gdx.input.justTouched()) {  // Detects a single touch event
                float touchX = Gdx.input.getX();
                float touchY = Gdx.graphics.getHeight() - Gdx.input.getY();  // Invert Y-axis

                if (touchX > Gdx.graphics.getWidth() / 2 - 50 && touchX < Gdx.graphics.getWidth() / 2 + 50 &&
                    touchY > Gdx.graphics.getHeight() / 2 - 105 && touchY < Gdx.graphics.getHeight() / 2 -25) {
                    // Open the level menu
                    levelMenu = new LevelsMenu(this, viewport);  // Pass reference to Main for callback
                    isInLevelMenu = true;
                }
            }
        }
    }
    public void startLevel(LevelManager level) {
        this.level = level;
        isPlaying = true;
        isInLevelMenu = false;
    }

    public void exitToLandingPage() {
        isPlaying = false;
        isInLevelMenu = false;
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height, true);
    }

    @Override
    public void dispose() {
        batch.dispose();
        background.dispose();
        playButton.dispose();
        if (level != null) {
            level.dispose();
        }
        if (levelMenu != null) {
            levelMenu.dispose();
        }
    }
}
