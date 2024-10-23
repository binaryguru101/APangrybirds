package io.github.binaryguru101.AP.Pigs;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.physics.box2d.*;

public class Pigs extends Sprite {

    protected World world;
    protected Body pigbody;
    protected Texture pigTexture;


    protected static final float PIG_RADIUS = 40f;
    protected static final float DENSITY = 0.0f;
    protected static final float RESTITUTION = 0.5f;
    protected static final float FRICTION = 0.3f;


    public Pigs(World world, float x, float y, String texturePath) {
        this.world = world;

        definePig(x, y);

        pigTexture = new Texture(Gdx.files.internal(texturePath));
        setBounds(0, 0, PIG_RADIUS * 2, PIG_RADIUS * 2);
        setRegion(pigTexture);
        // degbugging
        System.out.println("Pig Texture Loaded: " + (pigTexture != null));


    }

    protected void definePig(float x, float y) {
        BodyDef pigDef = new BodyDef();
        pigDef.position.set(x, y);
        pigDef.type = BodyDef.BodyType.StaticBody; // Can be StaticBody if you don't want it to move

        pigbody = world.createBody(pigDef);

        CircleShape shape = new CircleShape();
        shape.setRadius(PIG_RADIUS);

        FixtureDef fdef = new FixtureDef();
        fdef.shape = shape;
        fdef.density = DENSITY;
        fdef.friction = FRICTION;
        fdef.restitution = RESTITUTION;

        pigbody.createFixture(fdef);
        shape.dispose();
    }


    public void update(float dt) {
        setPosition(pigbody.getPosition().x - getWidth() / 2, pigbody.getPosition().y - getHeight() / 2);
        System.out.println("Pig Position: " + pigbody.getPosition());  // Debug log


    }


    public Body getBody() {
        return pigbody;
    }
}
