package io.github.binaryguru101.AP.Levels;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import io.github.binaryguru101.AP.AngryBirdsGame;
import io.github.binaryguru101.AP.Screens.PlayScreen;

public class LevelsScreen extends ScreenAdapter {
    private final Stage stage;
    private final AngryBirdsGame game; // Reference to the main game class
    private Texture exitTexture; // Texture for exit button
    private Texture settingsTexture; // Texture for settings button
    private Texture[] levelTextures; // Array to hold level textures
    private Texture backgroundTexture; // Background texture
    private SpriteBatch batch;

    public LevelsScreen(AngryBirdsGame game) {
        this.game = game; // Store reference to the main game
        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);

        // Load exit and settings textures
        exitTexture = new Texture(Gdx.files.internal("Quit.png")); // Update with your texture filename
        settingsTexture = new Texture(Gdx.files.internal("Settings.png")); // Update with your texture filename

        batch = new SpriteBatch();

        // Load level textures
        levelTextures = new Texture[3];
        levelTextures[0] = new Texture(Gdx.files.internal("level1.png")); // Update with your level 1 texture filename
        levelTextures[1] = new Texture(Gdx.files.internal("Level2.png")); // Update with your level 2 texture filename
        levelTextures[2] = new Texture(Gdx.files.internal("Level3.png")); // Update with your level 3 texture filename

        backgroundTexture = new Texture(Gdx.files.internal("Background.jpg")); // Update with your background texture filename


        createLevelButtons();


        createActionButtons();
    }

    private void createLevelButtons() {
        // Create a table to layout level buttons
        Table table = new Table();
        table.setFillParent(true);
        table.center(); // Center the table in the stage

        // Create level buttons (3 levels)
        for (int i = 0; i < levelTextures.length; i++) {

            ImageButton levelButton = new ImageButton(new TextureRegionDrawable(levelTextures[i]));
            levelButton.setSize(200, 100); // Set the size of the buttons


            final int finalI = i + 1; // final variable to use in inner class
            levelButton.addListener(new ClickListener() {
                @Override
                public void clicked(com.badlogic.gdx.scenes.scene2d.InputEvent event, float x, float y) {
                    // Handle level selection
                    System.out.println("Level " + finalI + " clicked");
                    game.setScreen(new PlayScreen(game)); // Pass level number to PlayScreen
                }
            });


            table.add(levelButton).fillX().uniformX().pad(20);
            table.row();
        }

        // Add the table to the stage
        stage.addActor(table);
    }

    private void createActionButtons() {
        // Create Exit Button with texture
        ImageButton exitButton = new ImageButton(new TextureRegionDrawable(exitTexture));
        exitButton.setSize(100, 50);
        exitButton.setPosition(50, 50);
        exitButton.addListener(new ClickListener() {
            @Override
            public void clicked(com.badlogic.gdx.scenes.scene2d.InputEvent event, float x, float y) {
                Gdx.app.exit(); // Exit the game
            }
        });
        stage.addActor(exitButton);


        ImageButton settingsButton = new ImageButton(new TextureRegionDrawable(settingsTexture));
        settingsButton.setSize(100, 50); // Set size for the settings button
        settingsButton.setPosition(Gdx.graphics.getWidth() - 150, 50); // Position at the bottom right
        settingsButton.addListener(new ClickListener() {
            @Override
            public void clicked(com.badlogic.gdx.scenes.scene2d.InputEvent event, float x, float y) {
                System.out.println("Settings clicked");
                // game.setScreen(new SettingsScreen(game)); // switch to settings screen
            }
        });
        stage.addActor(settingsButton);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin();
        batch.draw(backgroundTexture, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        batch.end();

        // Draw the stage
        stage.act();
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
    }

    @Override
    public void dispose() {
        stage.dispose();
        exitTexture.dispose(); // Dispose of exit texture
        settingsTexture.dispose(); // Dispose of settings texture
        for (Texture texture : levelTextures) {
            texture.dispose(); // Dispose of level textures
        }
    }
}
