package io.github.binaryguru101.AP.Birds;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import io.github.binaryguru101.AP.Collisions.ProjectileEquation;

public abstract class Bird extends Sprite {
    protected World world;
    protected Body birdbody;
    protected Texture birdtexture;

    protected static final float BIRD_RADIUS = 10f;
    protected Vector2 initialPosition;

    protected boolean isLaunched;
    protected boolean isSpecialAbilityActive = false;

    protected ProjectileEquation projectileEquation;


    public Bird(World world, Float x, Float y, String texturePath) {
        this.world = world;
        initialPosition = new Vector2(x, y);  // Start at the position passed

        // Define bird body

        projectileEquation = new ProjectileEquation();
        projectileEquation.gravity = -20f; // Adjust this to match Box2D's gravity scale


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
        fdef.density = 1.0f;
        birdbody.createFixture(fdef);

        shape.dispose();
    }

    public void launch(Vector2 startPosition, Vector2 currentPosition) {
        if (!isLaunched) {
            // Calculate launch vector (reversed direction for proper slingshot feel)
            Vector2 launchVector = new Vector2(
                20f-50f,  // Reversed to shoot away from slingshot
                startPosition.y-currentPosition.y
            );
            System.out.println(startPosition.x + " " + startPosition.y);
            System.out.println(launchVector.x + " " + launchVector.y);
            float maxipull= 20f;
            // Calculate the distance pulled back
//            float pullDistance = Math.min(Math.max(maxipull,launchVector.len()),maxipull);
            float pullDistance = launchVector.len();
            System.out.println("Pull Distance: " + pullDistance);



            // Much stronger power values for Box2D scale
            float minPower = 15f;
            float maxPower = 25f;

            // Calculate launch power based on pull distance
            float power = Math.min(maxPower, Math.max(minPower, pullDistance * 0.15f));

            System.out.println("Power"+power);

            // Normalize and scale
            System.out.println("before"+launchVector.len());
            launchVector.nor();
            System.out.println("after"+launchVector.len());

            launchVector.scl(power);
            System.out.println("afterbefore"+launchVector.len());


            // Apply the impulse
            birdbody.setLinearVelocity(0, 0); // Reset any existing velocity
            birdbody.setAngularVelocity(0);   // Reset any rotation

            // Apply a much stronger impulse
            System.out.println("birdbody.getLinearVelocity(): " + launchVector.x);
            System.out.println("birdbody.getAngularVelocity(): " + launchVector.y);
            Vector2 impulse = new Vector2(launchVector.x, launchVector.y);
//            Vector2 impulse = new Vector2(20f, 1f);

            impulse.scl(birdbody.getMass()*3); // Scale by mass to ensure consistent force

            birdbody.applyLinearImpulse(
                impulse.x,
                impulse.y,
                birdbody.getWorldCenter().x,
                birdbody.getWorldCenter().y,
                true
            );

            // Debug output
            System.out.println("Launch velocity: " + impulse);
            System.out.println("Bird mass: " + birdbody.getMass());
            System.out.println("Bird position: " + birdbody.getPosition());

            isLaunched = true;
        }
    }
    public void update(float dt) {
        setPosition(birdbody.getPosition().x - getWidth() / 5, birdbody.getPosition().y - getHeight() / 5);
//        System.out.println("Bird Position: " + birdbody.getPosition());  // Debug log
    }




    public void dispose() {
        birdtexture.dispose();
    }

//    public void launch(Vector2 startPosition, Vector2 currentPosition) {
//        if (!isLaunched) {
//            // Calculate the pull vector
//            Vector2 pullVector = new Vector2(startPosition.x - currentPosition.x, startPosition.y - currentPosition.y);
//
//            // Limit the pull distance to avoid extreme forces
//            float pullDistance = Math.min(pullVector.len(), 1000f); // Cap pull distance at 100 units
//            pullVector.nor(); // Normalize the pull vector
//
//            // Apply a scaled force
//            float launchStrength = 300.0f;
////            Vector2 launchForce = pullVector.scl(pullDistance * launchStrength );
//            Vector2 launchForce = pullVector.scl(300f * 500f);
//
//
//            // Apply the impulse to the bird's center
//            birdbody.applyLinearImpulse(launchForce, birdbody.getWorldCenter(), true);
//            System.out.println("launch " + launchForce);
//
//            // Set the bird as launched
//            isLaunched = true;
//        }
//    }


    public void activateSpecialAbility() {
        if (isLaunched) {
            isSpecialAbilityActive = true;
            useSpecialAbility();

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
