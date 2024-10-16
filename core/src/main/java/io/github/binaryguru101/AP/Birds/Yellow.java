package io.github.binaryguru101.AP.Birds;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
public class Yellow extends Bird {
    private static final float SPEED_BOOST = 10000f; // Adjust this value for desired boost

    public Yellow(World world, Float x, Float y, String texturePath) {
        super(world, x, y, "Yellow.jpeg");
    }

    @Override
    public void useSpecialAbility() {
        // Check if the bird is currently launched
        if (isLaunched) {
            // Get the current velocity
            Vector2 currentVelocity = birdbody.getLinearVelocity();
            System.out.println("Current Velocity: " + currentVelocity);
            // Apply the speed boost
            Vector2 boostedVelocity = new Vector2(currentVelocity.x + SPEED_BOOST, currentVelocity.y);
            birdbody.setLinearVelocity(boostedVelocity);
            System.out.println("Boosted Velocity: " + birdbody.getLinearVelocity());

        }
    }
}
