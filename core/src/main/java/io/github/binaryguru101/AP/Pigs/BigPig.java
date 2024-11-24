package io.github.binaryguru101.AP.Pigs;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;

public class BigPig  extends NormalPig {
    private static final float DIAMETER = 0.35f; // Diameter in pixels
    private static final float HEALTH = 1.25f;   // Health points
    private static final float WEIGHT = 0.6f;    // Weight for physics body

    public BigPig(World world, Vector2 position) {
        super(world,WEIGHT,DIAMETER,HEALTH,"Pigs.png",position);
    }
}
