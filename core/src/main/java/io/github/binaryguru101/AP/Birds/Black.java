package io.github.binaryguru101.AP.Birds;

import com.badlogic.gdx.physics.box2d.World;

public class Black extends Bird{
    public Black(World world, Float x, Float y, String texturePath) {
        super(world, x, y, "libgdx.png");
    }

    @Override
    public void useSpecialAbility() {

    }
}
