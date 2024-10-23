package io.github.binaryguru101.AP.Blocks;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;

public class Block extends Sprite {
    private Body body;

    public Block(World world, float x, float y, String texturePath) {
        super(new Texture(texturePath));

        setRegion(0, 0, getTexture().getWidth(), getTexture().getHeight());
        // Create the body definition
        BodyDef bodyDef = new BodyDef();
        bodyDef.position.set(x / 100, y / 100);
        bodyDef.type = BodyDef.BodyType.DynamicBody; // Dynamic body to allow interaction

        body = world.createBody(bodyDef);


        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = createShape();
        fixtureDef.density = 1.0f;
        fixtureDef.friction = 0.5f;
        fixtureDef.restitution = 0.2f;

        // Attach the fixture to the body
        body.createFixture(fixtureDef);
        body.setUserData(this);
    }

    public Body getBody() {
        return body;
    }

    public void update(float delta) {
        setPosition(body.getPosition().x * 100, body.getPosition().y * 100);
        setRotation((float) Math.toDegrees(body.getAngle()));
    }

    private PolygonShape createShape() {
        PolygonShape shape = new PolygonShape();
        shape.setAsBox(0.5f, 0.5f);
        return shape;
    }

    public void dispose() {
        body.getWorld().destroyBody(body); // Clean up body when done
        getTexture().dispose(); // Dispose of the texture
        body.getWorld().destroyBody(body); // Clean
    }
}
