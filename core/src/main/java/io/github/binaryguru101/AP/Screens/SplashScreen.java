package io.github.binaryguru101.AP.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import io.github.binaryguru101.AP.AngryBirdsGame;

public class SplashScreen implements Screen {
    private final AngryBirdsGame game;
    private float timeElapsed;
    private Texture wallpaper;
    private SpriteBatch batch;

    public SplashScreen(AngryBirdsGame game) {
        this.game = game;
        this.timeElapsed = 0;
    }

    @Override
    public void show() {
        batch = new SpriteBatch();  // Initialize the SpriteBatch
        wallpaper = new Texture(Gdx.files.internal("Wallpaper.jpg"));  // Load the texture
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.begin();
        batch.draw(wallpaper, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight()); // Draw the image to cover the screen
        batch.end();

        // Increment the timer
        timeElapsed += delta;


        if (timeElapsed > 1) {

            game.setScreen(new NewScreen(game));
        }
    }

    @Override
    public void resize(int width, int height) { }

    @Override
    public void pause() { }

    @Override
    public void resume() { }

    @Override
    public void hide() {

        wallpaper.dispose();
        batch.dispose();
    }

    @Override
    public void dispose() {

        wallpaper.dispose();
        batch.dispose();
    }
}
