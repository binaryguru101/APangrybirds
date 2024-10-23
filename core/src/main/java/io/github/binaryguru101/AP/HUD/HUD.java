package io.github.binaryguru101.AP.HUD;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import io.github.binaryguru101.AP.AngryBirdsGame;

public class HUD {
    public Stage stage;
    private Viewport view;

    private Integer worldTimer;
    private float TimeCount;
    private Integer score;
    private Integer lives;

    Label CountdownLabel;
    Label ScoreLabel;
    Label TimeLabel;
    Label LevelLabel;
    Label WorldLabel;
    Label Angrybirds;
    Label LivesLabel;

    public HUD(SpriteBatch batch) {
        worldTimer = 300;
        TimeCount = 0;
        score = 0;
        lives = 3;
        view = new FitViewport(AngryBirdsGame.V_Width, AngryBirdsGame.V_Height, new OrthographicCamera());
        stage = new Stage(view, batch);

        Table table = new Table();
        table.top();
        table.setFillParent(true); // table is size of the stage

        CountdownLabel = new Label(String.format("%03d", worldTimer), new Label.LabelStyle(new BitmapFont(), Color.BLACK));
        ScoreLabel = new Label(String.format("%06d", score), new Label.LabelStyle(new BitmapFont(), Color.BLACK));
        TimeLabel = new Label("TIME", new Label.LabelStyle(new BitmapFont(), Color.BLACK));
        LevelLabel = new Label("1-1", new Label.LabelStyle(new BitmapFont(), Color.BLACK));
        WorldLabel = new Label("WORLD", new Label.LabelStyle(new BitmapFont(), Color.BLACK));
        Angrybirds = new Label("ANGRYBIRDS", new Label.LabelStyle(new BitmapFont(), Color.BLACK));
        LivesLabel = new Label("LIVES: " + lives, new Label.LabelStyle(new BitmapFont(), Color.BLACK)); // Initialize LivesLabel


        table.add(Angrybirds).expandX().padTop(10);
        table.add(WorldLabel).expandX().padTop(10);
        table.add(TimeLabel).expandX().padTop(10);
        table.row();
        table.add(ScoreLabel).expandX();
        table.add(LevelLabel).expandX();
        table.add(CountdownLabel).expandX();
        table.row();
        table.add(LivesLabel).expandX();

        stage.addActor(table);
    }

    public void updateScore(int score) {
        this.score += score; // Update the score
        ScoreLabel.setText(String.format("%06d", this.score)); // Update the score label
    }


    public void decrementLives() {
        if (lives > 0) {
            lives--; // Decrement lives
            LivesLabel.setText("LIVES: " + lives); // Update LivesLabel
        }
    }

    public Integer getLives() {
        return lives; // Get current lives
    }

    public void updateTimer(float delta) {
        TimeCount += delta;
        if (TimeCount >= 1) { // Decrement every second
            worldTimer--;
            CountdownLabel.setText(String.format("%03d", worldTimer));
            TimeCount = 0;
        }
    }

    public void draw(SpriteBatch batch) {
        stage.draw(); // Draw the HUD stage
    }

    public void dispose() {
        stage.dispose(); // Dispose of the stage when done
    }
}
