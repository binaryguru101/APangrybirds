package io.github.binaryguru101.AP.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import io.github.binaryguru101.AP.AngryBirdsGame;
import io.github.binaryguru101.AP.Levels.LevelsScreen;

public class NewScreen implements Screen {
    private final AngryBirdsGame game;
    private Texture background;
    private SpriteBatch batch;
    private Stage stage;

    public NewScreen(AngryBirdsGame game) {
        this.game = game;
    }

    @Override
    public void show() {
        batch = new SpriteBatch();
        stage = new Stage(new ScreenViewport());

        // Load background texture
        background = new Texture(Gdx.files.internal("background.jpg"));

        // Load button textures
        Texture playButtonTexture = new Texture(Gdx.files.internal("play_button.png"));
        Texture exitButtonTexture = new Texture(Gdx.files.internal("Quit.png"));
        Texture settingsButtonTexture = new Texture(Gdx.files.internal("Settings.png"));

        // Create buttons
        ImageButton playButton = new ImageButton(new TextureRegionDrawable(playButtonTexture));
        ImageButton exitButton = new ImageButton(new TextureRegionDrawable(exitButtonTexture));
        ImageButton settingsButton = new ImageButton(new TextureRegionDrawable(settingsButtonTexture));


        float buttonScale = 0.5f; // Adjust the scale factor as needed
        playButton.setSize(playButton.getWidth() * buttonScale, playButton.getHeight() * buttonScale);
        exitButton.setSize(exitButton.getWidth() * buttonScale, exitButton.getHeight() * buttonScale);
        settingsButton.setSize(settingsButton.getWidth() * buttonScale, settingsButton.getHeight() * buttonScale);


        playButton.setPosition((Gdx.graphics.getWidth() - playButton.getWidth()) / 2, Gdx.graphics.getHeight() / 2);
        exitButton.setPosition(50, 100);  // Adjusted position for bottom-left
        settingsButton.setPosition(Gdx.graphics.getWidth() - settingsButton.getWidth() - 50, 100);  // Adjusted position for bottom-right


        playButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                // Transition to the game screen when play button is clicked
                game.setScreen(new LevelsScreen(game)); // Replace with your actual game screen
            }
        });

        exitButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                Gdx.app.exit(); // Exit the application when exit button is clicked
            }
        });

        settingsButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeListener.ChangeEvent event, Actor actor) {

//                game.setScreen(new SettingsScreen(game)); // Replace with  actual settings screen
            }
        });

        // Add buttons to the stage
        stage.addActor(playButton);
        stage.addActor(exitButton);
        stage.addActor(settingsButton);


        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void render(float delta) {

        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.begin();
        batch.draw(background, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());  // Cover the screen with background
        batch.end();

        // Draw the buttons (stage)
        stage.act(Gdx.graphics.getDeltaTime());
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
    }

    @Override
    public void pause() { }

    @Override
    public void resume() { }

    @Override
    public void hide() { }

    @Override
    public void dispose() {
        background.dispose();
        batch.dispose();
        stage.dispose();
    }
}
