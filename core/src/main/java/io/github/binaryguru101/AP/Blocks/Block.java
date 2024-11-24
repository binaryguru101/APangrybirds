package io.github.binaryguru101.AP.Blocks;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.physics.box2d.*;

public class Block extends Sprite {
    private Body body;
    private Texture blockTexture;

    // Constructor takes World, position (x, y), and texture path as parameters
    public Block(World world, float x, float y, String texturePath) {
        super(new Texture(texturePath));

        blockTexture = getTexture(); // Use the texture loaded from the path
        setBounds(0, 0, blockTexture.getWidth(), blockTexture.getHeight()); // Set sprite bounds based on texture size
        setRegion(blockTexture); // Set the texture region

        // Call method to define physics body and shape
        defineBlock(world, x, y);
    }

    // Define block body and fixture (similar to how Bird is defined)
    private void defineBlock(World world, float x, float y) {
        // Body definition
        BodyDef bodyDef = new BodyDef();
        bodyDef.position.set(x, y);
        bodyDef.type = BodyDef.BodyType.DynamicBody;

        // Create the body in the world
        body = world.createBody(bodyDef);

        // Create the shape and fixture for the body
        PolygonShape shape = new PolygonShape();
        shape.setAsBox(getWidth() / 2f, getHeight() / 2f); // Use sprite's width and height for the Box2D shape

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        fixtureDef.density = 1.0f;
        fixtureDef.friction = 0.5f;
        fixtureDef.restitution = 0.2f; // Bounce

        // Attach the fixture to the body
        body.createFixture(fixtureDef);
        body.setUserData(this); // Store reference to this Block instance in the body

        shape.dispose(); // Clean up shape after use
    }

    // Update the block's position based on the physics body
    public void update(float delta) {
        setPosition(body.getPosition().x - getWidth() / 2, body.getPosition().y - getHeight() / 2);
        setRotation((float) Math.toDegrees(body.getAngle())); // Update rotation based on the physics body's angle
    }

    // Clean up resources when the block is no longer needed
    public void dispose() {
        body.getWorld().destroyBody(body); // Destroy the physics body
        blockTexture.dispose(); // Dispose of the texture
    }

    // Get the Box2D body for further manipulation if needed
    public Body getBody() {
        return body;
    }
}
