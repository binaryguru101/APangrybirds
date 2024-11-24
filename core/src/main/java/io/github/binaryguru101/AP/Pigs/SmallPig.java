package io.github.binaryguru101.AP.Pigs;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;

public class SmallPig extends NormalPig {
    private static final float DIAMETER = 0.2f; // Diameter in pixels
    private static final float HEALTH = 1f;   // Health points
    private static final float WEIGHT = 0.4f;    // Weight for physics body

    public SmallPig(World world, Vector2 position) {
        super(world,WEIGHT,DIAMETER,HEALTH,"pigs.png",position);
    }
}
