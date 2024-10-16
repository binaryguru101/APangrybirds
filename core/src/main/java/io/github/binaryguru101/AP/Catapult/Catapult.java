package io.github.binaryguru101.AP.Catapult;


import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

public class Catapult extends Sprite {
    private Vector2 position;
    private Texture texture;


    public Catapult(float x, float y, String texturePath) {
        position = new Vector2(x, y);
        texture = new Texture(texturePath);
        setBounds(x, y, 50, 200);
        setTexture(texture);
    }

    public void render(SpriteBatch batch) {
        float scaleFactor = 1f;

        // Draw the catapult with scaling
        batch.draw(texture, position.x, position.y, getWidth() * scaleFactor, getHeight() * scaleFactor);
    }

    public void dispose() {
        texture.dispose();
    }
}
