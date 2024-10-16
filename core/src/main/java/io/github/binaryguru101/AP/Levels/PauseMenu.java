package io.github.binaryguru101.AP.Levels;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import io.github.binaryguru101.AP.AngryBirdsGame;
import io.github.binaryguru101.AP.Screens.NewScreen;

public class PauseMenu extends ScreenAdapter {
    private final AngryBirdsGame game;
    private Stage stage;
    private Skin skin;

    public PauseMenu(AngryBirdsGame game) {
        this.game = game;
        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);
        skin = new Skin(Gdx.files.internal("uiskin.json"));
        createUI();
    }

    private void createUI() {
        TextButton resumeButton = new TextButton("Resume", skin);
        resumeButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(game.getCurrentScreen()); // Resume to the previous screen
            }
        });

        TextButton restartButton = new TextButton("Restart", skin);
        restartButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new NewScreen(game)); // Restart the game
            }
        });

        // Layout buttons on the stage
        Table table = new Table();
        table.setFillParent(true);
        table.add(resumeButton).fillX().uniformX();
        table.row().pad(10, 0, 10, 0);
        table.add(restartButton).fillX().uniformX();
        table.row();
        stage.addActor(table);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 0.7f); // Slightly transparent background
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.act();
        stage.draw();
    }

    @Override
    public void dispose() {
        stage.dispose();
        skin.dispose();
    }
}
