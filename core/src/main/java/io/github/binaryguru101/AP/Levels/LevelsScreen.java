package io.github.binaryguru101.AP.Levels;

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
import io.github.binaryguru101.AP.Screens.PlayScreen;

public class LevelsScreen extends ScreenAdapter {
    private final Stage stage;
    private final Skin skin;
    private final AngryBirdsGame game; // Reference to the main game class

    public LevelsScreen(AngryBirdsGame game) {
        this.game = game; // Store reference to the main game
        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);

        skin = new Skin(Gdx.files.internal("uiskin.json")); // Ensure you have a skin file

        // Create a table to layout level buttons
        Table table = new Table();
        table.setFillParent(true);

        // Create level buttons
        for (int i = 1; i <= 10; i++) {
            TextButton levelButton = new TextButton("Level " + i, skin);
            int finalI = i;
            levelButton.addListener(new ClickListener() {
                @Override
                public void clicked(com.badlogic.gdx.scenes.scene2d.InputEvent event, float x, float y) {
                    // Handle level selection (replace with your game screen logic)
                    System.out.println("Level " + finalI + " clicked");
                    game.setScreen(new PlayScreen(game)); // Change to your GameScreen class
                }
            });
            table.add(levelButton).fillX().uniformX().pad(10);
            table.row();
        }

        // Add the table to the stage
        stage.addActor(table);
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
    public void dispose() {
        stage.dispose();
        skin.dispose();
    }
}
