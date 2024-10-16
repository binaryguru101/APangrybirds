package io.github.binaryguru101.AP.Collisions;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import java.util.ArrayList;
import java.util.List;

public class Trajectory {
    private List<Vector2> trajectoryPoints;
    private ShapeRenderer shapeRenderer;

    public Trajectory() {
        trajectoryPoints = new ArrayList<>();
        shapeRenderer = new ShapeRenderer();
    }

    public void calculateTrajectory(Body birdBody, Vector2 initialVelocity) {
        trajectoryPoints.clear();

        Vector2 position = birdBody.getPosition().cpy();
        Vector2 velocity = initialVelocity.cpy();
        float timeStep = 1 / 60f; // The time step for prediction

        // Simulate trajectory by predicting future positions
        for (int i = 0; i < 50; i++) {
            position.add(velocity.cpy().scl(timeStep));
            velocity.add(0, -9.8f * timeStep);

            trajectoryPoints.add(new Vector2(position));
        }
    }

    public void render() {
        shapeRenderer.setColor(1, 1, 1, 1);
        shapeRenderer.begin(ShapeRenderer.ShapeType.Line);

        shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
        for (int i = 0; i < trajectoryPoints.size() - 1; i++) {
            shapeRenderer.line(trajectoryPoints.get(i), trajectoryPoints.get(i + 1));
        }
        shapeRenderer.end();
    }

    public void dispose() {
        shapeRenderer.dispose();
    }
}
