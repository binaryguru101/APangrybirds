package io.github.binaryguru101.AP.Birds;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
public class Yellow extends Bird {
    private static final float SPEED_BOOST = 10000f; // Adjust this value for desired boost

    public Yellow(World world, Float x, Float y, String texturePath) {
        super(world, x, y, "Yellow.png");
    }

    @Override
    public void useSpecialAbility() {
        // Check if the bird is currently launched
        System.out.println("Yellow useSpecialAbility");

        System.out.println("Yellow useSpecialAbility2");

                System.out.println("Yellow useSpecialAbility3");
                // Get the current velocity
                Vector2 currentVelocity = birdbody.getLinearVelocity();
                System.out.println("Current Velocity: " + currentVelocity);
                // Apply the speed boost

                Vector2 boostedVelocity = new Vector2(currentVelocity.x * 100, currentVelocity.y*20);
                birdbody.setLinearVelocity(boostedVelocity);
                Vector2 force = currentVelocity.nor().scl(1000); // Apply force in the direction of current velocity
                birdbody.applyForceToCenter(force, true);
                birdbody.setLinearDamping(0);

                System.out.println("Boosted Velocity: " + birdbody.getLinearVelocity());





    }
}
