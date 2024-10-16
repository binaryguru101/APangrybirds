package io.github.binaryguru101.AP.Birds;

import com.badlogic.gdx.physics.box2d.World;

public class Blue extends Bird{

    public Blue(World world, Float x, Float y, String texturePath) {
        super(world, x, y, "Blue.jpeg");
    }

    @Override
    public void useSpecialAbility() {

    }
}
