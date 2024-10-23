package io.github.binaryguru101.AP.Birds;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;

public class Red extends Bird{

    public Red(World world, Float x, Float y, String Texturepath) {
        super(world, x, y, "Red.png");
    }

    @Override
    public void useSpecialAbility() {
        //no ability for red
    }
}
