package io.github.binaryguru101.AP.Levels;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import io.github.binaryguru101.AP.Birds.*;
import io.github.binaryguru101.AP.Catapult.LaunchHandler;
import io.github.binaryguru101.AP.Catapult.SlingShot;
import io.github.binaryguru101.AP.Catapult.TrajectoryView;
import io.github.binaryguru101.AP.Screens.PauseScreen;

import java.util.List;

public class LevelManager {
    private boolean levelUnlocked;
    private World world;
    private SpriteBatch batch;
    private Viewport viewport;
    private Box2DDebugRenderer debugRenderer;
    private Texture background;
    private SlingShot slingshot;
    private BaseLevel structure;
    private Body groundBody; // Direct reference to the ground body
    private LaunchHandler launchHandler;
    public Angrybird[] Angrybird;
    public TrajectoryView thread;
    private PauseScreen pauseScreen;

    public  LevelManager(Viewport viewport, BaseLevel structure, World world) {
        this.viewport = new FitViewport(20f, 12f);  // Use FitViewport for consistent scaling
        batch = new SpriteBatch();
        background = new Texture("Background.jpg");

        // Create the Box2D world with gravity
        this.world = world;

        Angrybird = createBirds();
        debugRenderer = new Box2DDebugRenderer();

        // Initialize the pause screen
//        pauseScreen = new PauseScreen(viewport);

        // Create the slingshot
        slingshot = new SlingShot();
        thread = new TrajectoryView( viewport,slingshot);

        // Create the structure
        this.structure = structure;  // Adjust structure position as needed

        // Create the ground directly within the Level class
        createGround();

        // Create the launch handler
        launchHandler = new LaunchHandler(List.of(Angrybird), structure, slingshot, thread, pauseScreen, viewport);
        levelUnlocked = false;
    }

    public boolean getLevelStatus() {
        return levelUnlocked;
    }

    public void unlockLevel() {
        levelUnlocked = true;
    }

    private Angrybird[] createBirds() {
        Angrybird[] birds = new Angrybird[3];  // Example with 3 birds
        birds[0] = new Red(world, new Vector2(2, 3));  // Set bird positions as needed
        birds[1] = new Chuck(world, new Vector2(2, 3));  // Set bird positions as needed
        birds[2] = new Chuck(world, new Vector2(2, 3));  // Set bird positions as needed
        return birds;
    }

    private void createGround() {
        // Define the ground body
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.StaticBody;
        bodyDef.position.set(20f / 2, 12f * 0.075f); // Ground position (centered horizontally)

        // Create the body in the world
        groundBody = world.createBody(bodyDef);

        // Define the shape
        PolygonShape shape = new PolygonShape();
        shape.setAsBox(20f / 2, 12f * 0.075f); // Ground dimensions (half-width, half-height)

        // Define the fixture with friction
        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        fixtureDef.friction = 1f; // Moderate friction for realistic interaction
        fixtureDef.restitution = 0.5f; // No bounce for the ground
        fixtureDef.density = 0.0f; // Density is irrelevant for static bodies

        // Attach the fixture to the body
        groundBody.createFixture(fixtureDef);

        // Dispose of the shape
        shape.dispose();
    }

    public void render() {
        ScreenUtils.clear(Color.BLACK);

        // Update the viewport dimensions
        viewport.update(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), true);
        float delta = Gdx.graphics.getDeltaTime();


            world.step(1 / 60f, 6, 2);  // Fixed time step for the physics simulation
            // Update objects to destroy after stepping the world

        GameMembers._RemoveMembers();


        // Start drawing with SpriteBatch
        batch.setProjectionMatrix(viewport.getCamera().combined);
        batch.begin();
        batch.draw(background, 0, 0, 20f, 12f);  // Draw the background
        batch.end();

        slingshot.render(batch);
        thread.Render(batch);

        for (Angrybird bird : Angrybird) {
            bird.render(batch);
        }

        structure.render(batch);

        // Render the Box2D debug lines
        debugRenderer.render(world, viewport.getCamera().combined);

        // Render the pause screen
//        pauseScreen.render();
        launchHandler.handleInput();  // Handle input for launching the bird
    }

    public void dispose() {
        world.dispose();
        batch.dispose();
        background.dispose();
        debugRenderer.dispose();
//        structure.ispose();
        pauseScreen.dispose();  // Dispose of pause screen resources
    }
}
