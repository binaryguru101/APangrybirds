package io.github.binaryguru101.AP.Birds;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;

public class Red extends Angrybird{

    public Red(World world, Vector2 Slingposition) {
        super(world,10f,Slingposition,"Red.png",1f);

    }


    @Override
    public void UseSpecialAbility() {

    }
}
