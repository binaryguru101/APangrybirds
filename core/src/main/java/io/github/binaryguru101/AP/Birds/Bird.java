package io.github.binaryguru101.AP.Birds;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;

public abstract class Bird extends Sprite {
    protected World world;
    protected Body birdbody;
    protected Texture birdtexture;

    protected static final float BIRD_RADIUS = 10f;
    protected Vector2 initialPosition;

    protected boolean isLaunched;


    public Bird(World world, Float x, Float y, String texturePath) {
        this.world = world;
        initialPosition = new Vector2(x, y);  // Start at the position passed

        // Define bird body


        // Texture
        birdtexture = new Texture(Gdx.files.internal(texturePath));
        setBounds(0, 0, BIRD_RADIUS * 7, BIRD_RADIUS * 7);
        setRegion(birdtexture);

        System.out.println("Bird Texture Loaded: " + (birdtexture != null));

        defineBird();

    }

    protected void defineBird() {
        BodyDef birdDef = new BodyDef();
        birdDef.position.set(initialPosition.x, initialPosition.y);
        birdDef.type = BodyDef.BodyType.DynamicBody;

        birdbody = world.createBody(birdDef);

        CircleShape shape = new CircleShape();
        shape.setRadius(BIRD_RADIUS);

        FixtureDef fdef = new FixtureDef();
        fdef.shape = shape;
        birdbody.createFixture(fdef);

        shape.dispose();
    }

    public void update(float dt) {
        setPosition(birdbody.getPosition().x - getWidth() / 2, birdbody.getPosition().y - getHeight() / 2);
        System.out.println("Bird Position: " + birdbody.getPosition());  // Debug log
    }

    public void dispose() {
        birdtexture.dispose();
    }

    public void launch(Vector2 startPosition, Vector2 currentPosition) {
        if (!isLaunched) {
            Vector2 pullVector = new Vector2(startPosition.x - currentPosition.x, startPosition.y - currentPosition.y);


            float pullDistance = pullVector.len();
            pullVector.nor();

            Vector2 launchForce = pullVector.scl(pullDistance * 10); // Adjust the multiplier for desired launch strength

            birdbody.applyLinearImpulse(launchForce, birdbody.getWorldCenter(), true);
            isLaunched = true;
        }
    }



    public void reset() {
        birdbody.setTransform(initialPosition, 0); // Reset position
        birdbody.setLinearVelocity(0, 0);           // Reset velocity
        birdbody.setAngularVelocity(0);              // Reset angular velocity
        isLaunched = false;                          // Reset launched state
        setPosition(initialPosition.x - getWidth() / 2, initialPosition.y - getHeight() / 2); // Reset sprite position
    }

    public void onHit() {
        // Logic to make the bird disappear or become inactive
        birdbody.setLinearVelocity(birdbody.getLinearVelocity().x, -10f); // Adjust speed as necessary
    }

    public Body getBirdbody() {
        return birdbody;
    }

    // Define abstract method
    public abstract void useSpecialAbility();

}
