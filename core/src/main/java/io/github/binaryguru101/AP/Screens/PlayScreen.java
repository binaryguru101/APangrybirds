package io.github.binaryguru101.AP.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import io.github.binaryguru101.AP.AngryBirdsGame;
import io.github.binaryguru101.AP.Birds.Bird;
import io.github.binaryguru101.AP.Birds.Red;
import io.github.binaryguru101.AP.Birds.Yellow;
import io.github.binaryguru101.AP.Catapult.Catapult;
import io.github.binaryguru101.AP.Collisions.Contact;
import io.github.binaryguru101.AP.Collisions.Trajectory;
import io.github.binaryguru101.AP.HUD.HUD;
import io.github.binaryguru101.AP.Pigs.Pigs;

public class PlayScreen implements Screen {
    private AngryBirdsGame game;
    private OrthographicCamera camera;
    private Viewport viewport;
    private HUD hud;


    private Bird testBird;
    private Pigs pig;// Just one bird for now
    private World world;
    private Box2DDebugRenderer debugRenderer;


    private boolean isDragging = false;
    private Vector2 dragStart = new Vector2();
    private Vector2 dragEnd = new Vector2();

    private Trajectory trajectory;

    private Catapult catapult;

    private ShapeRenderer shapeRenderer;
    private Vector2 lastPoint = new Vector2();



    public PlayScreen(AngryBirdsGame game) {
        this.game = game;
        camera = new OrthographicCamera();
        viewport = new FitViewport(800, 480, camera);
        camera.position.set(viewport.getWorldWidth() / 2, viewport.getWorldHeight() / 2, 0);
        hud = new HUD(game.batch);

        // World setup
        world = new World(new Vector2(0, 0f), true);
        debugRenderer = new Box2DDebugRenderer();
        world.setContactListener(new Contact());

        // Add one test bird at a visible position
//        testBird = new Red(world, 100f, 200f, "Blue.jpeg");
        testBird = new Yellow(world, 100f, 200f, "Blue.jpeg");

        //only finna add 1 pig
        pig = new Pigs(world, 600f, 200f, "Yellow.jpeg");

        trajectory = new Trajectory();

        shapeRenderer = new ShapeRenderer();


        Gdx.input.setInputProcessor(new InputAdapter() {
            private boolean dragging = false;
            private Vector2 initialTouchPos;

            @Override
            public boolean touchDown(int screenX, int screenY, int pointer, int button) {
                initialTouchPos = new Vector2(screenX, screenY);
                dragging = true;
                return true;
            }

            @Override
            public boolean touchDragged(int screenX, int screenY, int pointer) {
                if (dragging) {

                    float dragX = screenX - initialTouchPos.x;
                    float dragY = screenY - initialTouchPos.y;
                    testBird.setPosition(initialTouchPos.x + dragX, Gdx.graphics.getHeight() - (initialTouchPos.y + dragY)); // Invert Y for screen space
                }
                return true; // Process this event
            }

            @Override
            public boolean touchUp(int screenX, int screenY, int pointer, int button) {
                if (dragging) {
                    dragging = false;

                    // Fin
                    Vector2 finalTouchPos = new Vector2(screenX, screenY);


                    Vector2 force = initialTouchPos.cpy().sub(finalTouchPos).scl(0.1f); // Adjust scale for power

                    testBird.reset();
                    testBird.launch(dragStart,new Vector2(Gdx.input.getX(), Gdx.input.getY())); // Launch the bird with the calculated force
                }
                return true; // Process this event
            }
        });


    }

    public void drawTrajectory(Vector2 startPosition, Vector2 launchForce) {
        shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
        shapeRenderer.setColor(Color.RED); // Set line color

        // Start at the bird's initial position
        Vector2 currentPosition = new Vector2(startPosition);

        // Time step and total simulation time
        float timeStep = 0.1f; // Adjust this value for smoother lines
        float totalTime = 2f;  // How long the trajectory should be shown

        // Calculate trajectory points
        for (float t = 0; t < totalTime; t += timeStep) {
            // Calculate position using physics equations
            Vector2 trajectoryPoint = currentPosition.cpy().add(launchForce.cpy().scl(t)); // Simplified motion equation
            trajectoryPoint.y -= 0.5f * 9.8f * t * t; // Apply gravity effect (adjust for your needs)

            // Draw a line from the last position to the current trajectory point
            if (t > 0) {
                shapeRenderer.line(lastPoint.x, lastPoint.y, trajectoryPoint.x, trajectoryPoint.y);
            }
            lastPoint.set(trajectoryPoint); // Update last point
        }

        shapeRenderer.end();
    }



    @Override
    public void show() {
        catapult = new Catapult(50, 50, "Catapult.jpeg");
    }

    @Override
    public void render(float delta) {
        // Clear screen
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);



        hud.updateTimer(delta);

        // Update logic
        world.step(1 / 120f, 10, 3);  // Step the physics world
        testBird.update(delta);// Update the bird

        pig.update(delta);

        if (Gdx.input.isTouched()) {
            Vector2 currentPosition = new Vector2(Gdx.input.getX(), Gdx.input.getY());

            if (!isDragging) {
                // Start dragging
                isDragging = true;
                dragStart.set(currentPosition);
            } else {
                // Update drag end position
                dragEnd.set(Gdx.input.getX(), Gdx.input.getY());
            }
        } else {
            if (isDragging) {
                // Launch the bird when the mouse is released
                Vector2 launchForce = dragStart.sub(dragEnd).scl(0.1f); // Scale the force
//                testBird.launch(dragStart,new Vector2(Gdx.input.getX(), Gdx.input.getY())); // Launch the bird with the calculated force
                testBird.launch(new Vector2(catapult.getX(), catapult.getY()), new Vector2(Gdx.input.getX(), Gdx.input.getY()));
                Vector2 currentPosition = new Vector2(Gdx.input.getX(), Gdx.input.getY());

                drawTrajectory(testBird.getBirdbody().getPosition(), launchForce);



                isDragging = false;
            }
        }

        // Reset the bird if it goes out of bounds
        if (testBird.getX() < 0 || testBird.getX() > viewport.getWorldWidth() ||
            testBird.getY() < 0 || testBird.getY() > viewport.getWorldHeight()) {
            testBird.reset();
            hud.decrementLives();

        }


        // Draw the bird
        game.batch.setProjectionMatrix(camera.combined);
        game.batch.begin();

        catapult.render(game.batch);
        try {
            if (testBird != null) {
                testBird.useSpecialAbility();
                testBird.draw(game.batch);
            } else {
                System.out.println("testBird is null");
            }
        } catch (Exception e) {
            System.out.println("Exception when drawing testBird: " + e.getMessage());
            e.printStackTrace();
        }

        try {
            if (pig != null) {
                pig.draw(game.batch);
            } else {
                System.out.println("pig is null");
            }
        } catch (Exception e) {
            System.out.println("Exception when drawing pig: " + e.getMessage());
            e.printStackTrace();
        }
        System.out.println(testBird.getTexture());
        game.batch.end();
        hud.draw(game.batch);


        // Debug renderer
        debugRenderer.render(world, camera.combined);
    }




    @Override
    public void resize(int width, int height) {
        viewport.update(width, height);

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        world.dispose();
        debugRenderer.dispose();
        hud.dispose();
    }

    // Other methods like pause, resume, hide, etc. can remain empty for now
}
