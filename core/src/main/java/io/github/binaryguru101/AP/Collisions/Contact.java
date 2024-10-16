package io.github.binaryguru101.AP.Collisions;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import io.github.binaryguru101.AP.Birds.Bird;
import io.github.binaryguru101.AP.Pigs.Pigs;

public class Contact implements ContactListener {

    @Override
    public void beginContact(com.badlogic.gdx.physics.box2d.Contact contact) {

            // Check if the contact involves a bird and a pig
            Object userDataA = contact.getFixtureA().getUserData();
            Object userDataB = contact.getFixtureB().getUserData();

            if ((userDataA instanceof Bird && userDataB instanceof Pigs) ||
                (userDataA instanceof Pigs && userDataB instanceof Bird)) {

                // Get the pig body and apply a downward impulse
                Body pigBody = (Body) (userDataA instanceof Pigs ? contact.getFixtureA().getBody() : contact.getFixtureB().getBody());
                Vector2 impulse = new Vector2(0, -10f); // Adjust the impulse strength as needed
                pigBody.applyLinearImpulse(impulse, pigBody.getWorldCenter(), true);
            }




    }

    @Override
    public void endContact(com.badlogic.gdx.physics.box2d.Contact contact) {

    }

    @Override
    public void preSolve(com.badlogic.gdx.physics.box2d.Contact contact, Manifold manifold) {

    }

    @Override
    public void postSolve(com.badlogic.gdx.physics.box2d.Contact contact, ContactImpulse contactImpulse) {

    }
}
