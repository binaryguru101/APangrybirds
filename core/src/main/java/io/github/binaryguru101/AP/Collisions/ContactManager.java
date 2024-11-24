package io.github.binaryguru101.AP.Collisions;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import io.github.binaryguru101.AP.Birds.Bird;
import io.github.binaryguru101.AP.Birds.GameMembers;
import io.github.binaryguru101.AP.Pigs.Pigs;

public class ContactManager implements ContactListener {

    @Override
    public void beginContact(com.badlogic.gdx.physics.box2d.Contact contact) {

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
        public void postSolve(com.badlogic.gdx.physics.box2d.Contact contact, ContactImpulse impulse) {
            Object objA = contact.getFixtureA().getBody().getUserData();
            Object objB = contact.getFixtureB().getBody().getUserData();

            if (objA instanceof GameMembers && objB instanceof GameMembers) {
                // Calculate the total impulse (force) from the contact
                float totalImpulse = 0f;
                for (float normalImpulse : impulse.getNormalImpulses()) {
                    totalImpulse += normalImpulse;
                }

                // Pass the calculated force to the onCollision method
                ((GameMembers) objA)._Collision(contact.getFixtureB().getBody(), totalImpulse);
                ((GameMembers) objB)._Collision(contact.getFixtureA().getBody(), totalImpulse);
            }
        }
    }


