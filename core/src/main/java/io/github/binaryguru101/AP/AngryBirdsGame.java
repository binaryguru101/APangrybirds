package io.github.binaryguru101.AP;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import io.github.binaryguru101.AP.Screens.NewScreen;
import io.github.binaryguru101.AP.Screens.PlayScreen;


/** {@link com.badlogic.gdx.ApplicationListener} implementation shared by all platforms. */
public class AngryBirdsGame extends Game {
    public static float V_Width=800,V_Height=400;
    public SpriteBatch batch;
    private boolean isPaused;



    @Override
    public void create() {
        batch = new SpriteBatch();
        setScreen(new NewScreen(this));
//        setScreen(new PlayScreen(this));
        isPaused = false;

    }

    @Override
    public void render() {
        super.render();
        if (isPaused) {
            // Don't render game elements when paused
            return;
        }
    }
    public void resumeGame() {
        isPaused = false;
    }

    @Override
    public void dispose() {
        batch.dispose();
    }
    public Screen getCurrentScreen() {
        return super.getScreen();
    }
}
