package io.github.binaryguru101.AP.Birds;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;

public class Chuck extends Angrybird{
    public Chuck(World world, Vector2 slingStartPos) {
        super(world,1f,slingStartPos,"Yellow.png",1f);
    }


    @Override
    public void UseSpecialAbility() {

    }
}
