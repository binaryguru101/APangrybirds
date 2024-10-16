package io.github.binaryguru101.AP.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import io.github.binaryguru101.AP.AngryBirdsGame;
import io.github.binaryguru101.AP.Levels.LevelsScreen;

public class NewScreen extends ScreenAdapter {
    private final Stage stage;
    private final Skin skin;
    private AngryBirdsGame game;

    public NewScreen(AngryBirdsGame game) {
        this.game=game;
        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);

        skin = new Skin(Gdx.files.internal("uiskin.json"));

        // Create buttons
        TextButton newGameButton = new TextButton("New Game", skin);
        TextButton levelsButton = new TextButton("Levels", skin);

        // Set up button actions
        newGameButton.addListener(new ClickListener() {
            @Override
            public void clicked(com.badlogic.gdx.scenes.scene2d.InputEvent event, float x, float y) {
                // Handle new game logic here
                System.out.println("New Game clicked");
                game.setScreen(new LevelsScreen(game));
                // Set the game screen or perform actions to start a new game
            }
        });

        levelsButton.addListener(new ClickListener() {
            @Override
            public void clicked(com.badlogic.gdx.scenes.scene2d.InputEvent event, float x, float y) {
                // Handle levels logic here
                System.out.println("Levels clicked");
                game.setScreen(new LevelsScreen(game));
            }
        });

        // Create a table to organize the buttons
        Table table = new Table();
        table.setFillParent(true);
        table.add(newGameButton).fillX().uniformX();
        table.row().pad(10, 0, 10, 0);
        table.add(levelsButton).fillX().uniformX();

        // Add the table to the stage
        stage.addActor(table);
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        // Draw the stage
        stage.act();
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
    }

    @Override
    public void pause() {
        // Handle pause events
    }

    @Override
    public void resume() {
        // Handle resume events
    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        stage.dispose();
        skin.dispose();
    }
}
