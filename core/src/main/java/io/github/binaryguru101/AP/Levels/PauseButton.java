package io.github.binaryguru101.AP.UI;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import io.github.binaryguru101.AP.AngryBirdsGame;
import io.github.binaryguru101.AP.Screens.GameOverScreen;

public class PauseButton extends Sprite {
    private final ImageButton button;

    public PauseButton(Stage stage, AngryBirdsGame game) {
        // Load the texture for the pause button
        Texture pauseTexture = new Texture(Gdx.files.internal("Pause.png"));


        TextureRegionDrawable drawable = new TextureRegionDrawable(pauseTexture);


        button = new ImageButton(drawable);


        button.setPosition(Gdx.graphics.getWidth() - button.getWidth() - 10, Gdx.graphics.getHeight() - button.getHeight() - 10);

        button.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new GameOverScreen(game));
            }
        });

        stage.addActor(button);
    }

    public void dispose() {
    }
}
