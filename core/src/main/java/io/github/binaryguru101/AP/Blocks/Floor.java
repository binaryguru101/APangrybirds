package io.github.binaryguru101.AP.Blocks;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;

public class Floor {
    private Texture groundTexture;
    private Body groundBody;
    private float width, height;


    ShapeRenderer shapeRenderer;

    public Floor(World world, float width, float height) {
        this.width = width;
        this.height = height;
        groundTexture = new Texture("floor.png");

        createGround(world);
        shapeRenderer = new ShapeRenderer();
    }

    public void createGround(World world) {

        BodyDef groundBodyDef = new BodyDef();
        groundBodyDef.type = BodyDef.BodyType.StaticBody;
        groundBodyDef.position.set(new Vector2(70, 100));

        groundBody = world.createBody(groundBodyDef);


        PolygonShape groundShape = new PolygonShape();
        groundShape.setAsBox(width, height);

        // Define the physical properties of the ground
        FixtureDef groundFixtureDef = new FixtureDef();
        groundFixtureDef.shape = groundShape;
        groundFixtureDef.friction = 0.5f;  // Add friction so objects don't slide too much

        // Attach the shape to the body
        groundBody.createFixture(groundFixtureDef);

        // Dispose of the shape as it's no longer needed after being used
        groundShape.dispose();
    }

    public void render(SpriteBatch batch) {
        batch.begin();
        batch.draw(groundTexture, groundBody.getPosition().x,groundBody.getPosition().y, width,height);
        batch.end();
    }

    // Dispose of the texture
    public void dispose() {
        groundTexture.dispose();
    }

    // Get the ground body for Box2D collision purposes
    public Body getBody() {
        return groundBody;
    }
}
