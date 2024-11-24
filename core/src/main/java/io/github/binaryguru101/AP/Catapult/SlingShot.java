package io.github.binaryguru101.AP.Catapult;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

public class SlingShot {
    private Texture texture;
    private Vector2 Start_position;

    public SlingShot() {
            texture = new Texture("Catapult.png");
            Start_position = new Vector2(20f*0.15f, 20f*0.15f);
    }
    public void render(SpriteBatch batch) {
        batch.begin();
        batch.draw(texture, Start_position.x, Start_position.y,1.2f, 2f);
        batch.end();
    }

    public void Dispose(){
        texture.dispose();
    }

    public Texture getTexture() {
        return texture;
    }

    public void setTexture(Texture texture) {
        this.texture = texture;
    }

    public Vector2 getStart_position() {
        return Start_position;
    }

    public void setStart_position(Vector2 start_position) {
        Start_position = start_position;
    }
}
