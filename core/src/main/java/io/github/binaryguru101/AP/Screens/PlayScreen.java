package io.github.binaryguru101.AP.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import io.github.binaryguru101.AP.AngryBirdsGame;
import io.github.binaryguru101.AP.Birds.Bird;
import io.github.binaryguru101.AP.Birds.Black;
import io.github.binaryguru101.AP.Birds.Red;
import io.github.binaryguru101.AP.Birds.Yellow;
//import io.github.binaryguru101.AP.Collisions.Contact;
import io.github.binaryguru101.AP.Collisions.Trajectory;
import io.github.binaryguru101.AP.HUD.HUD;
import io.github.binaryguru101.AP.Pigs.Pigs;
import io.github.binaryguru101.AP.Catapult.Catapult;
import io.github.binaryguru101.AP.Blocks.Block; // Import the Block class
import com.badlogic.gdx.scenes.scene2d.Stage;
import io.github.binaryguru101.AP.UI.PauseButton;

import static com.badlogic.gdx.utils.JsonValue.ValueType.array;


public class PlayScreen implements Screen {

    // Store the catapult's position
    private static final float CATAPULT_X = 50f;
    private static final float CATAPULT_Y = 50f;
    private static final float GROUND_Y = 70;

    private AngryBirdsGame game;
    private OrthographicCamera camera;
    private Viewport viewport;
    private HUD hud;

    private Texture backgroundTexture;
    private final Stage stage;

    private Bird testBird;
    private Pigs pig;
    private World world;
    private Box2DDebugRenderer debugRenderer;

    private boolean isDragging = false;
    private Vector2 dragStart = new Vector2();
    private Vector2 dragEnd = new Vector2();

    private Trajectory trajectory;

    private Catapult catapult;
    private ShapeRenderer shapeRenderer;
    private Vector2 lastPoint = new Vector2();

    private Array<Block> blocks;

    private ImageButton pauseButton;
    private Texture pauseTexture;

    private Array<Bird> birds;
    private int currindex;

    public PlayScreen(AngryBirdsGame game) {
        this.game = game;
        camera = new OrthographicCamera();
        viewport = new FitViewport(800, 480, camera);
        camera.position.set(viewport.getWorldWidth() / 2, viewport.getWorldHeight() / 2, 0);
        hud = new HUD(game.batch);
        stage = new Stage(new ScreenViewport());

        // World setup
        world = new World(new Vector2(0, -9.8f), true); // Gravity set for downward motion
        debugRenderer = new Box2DDebugRenderer();
//        world.setContactListener(new Contact());

        // Ground setup
        createGround();

        birds = new Array<>();
//        birds.add(new Red(world, 100f, 200f, "Red.png"));
        birds.add(new Yellow(world, 50f, 100f, "Yellow.png"));
        birds.add(new Yellow(world, 25f, 100f, "Yellow.png"));


        for (Bird bird : birds) {
            bird.getBirdbody().setActive(false);
        }
        currindex = 0;

        pig = new Pigs(world, 600f, 120, "Pigs.png");

        trajectory = new Trajectory();
        shapeRenderer = new ShapeRenderer();

        backgroundTexture = new Texture(Gdx.files.internal("Background.jpg"));

        // Stage background
        stage.addActor(new Image(backgroundTexture));

        // Catapult setup
        catapult = new Catapult(50, 50, "Catapult.png");

        // Blocks
        blocks = new Array<>();
//        blocks.add(new Block(world, 600f, 200f, "Block.png"));
        // Add blocks starting from the ground
        float blockWidth = 70;  // Adjust based on your texture
        float blockHeight = 20; // Adjust based on your texture
        blocks.add(new Block(world,550,90,"Block.png"));
        blocks.add(new Block(world,550,133,"Block.png"));
        blocks.add(new Block(world,550,186,"Block.png"));
        blocks.add(new Block(world,650,90,"Block.png"));
        blocks.add(new Block(world,650,133,"Block.png"));
        blocks.add(new Block(world,650,186,"Block.png"));
        blocks.add(new Block(world,600,90,"BlockRot.png"));



        Bird testBird = birds.get(currindex);

        Gdx.input.setInputProcessor(new InputAdapter() {
            @Override
            public boolean touchDown(int screenX, int screenY, int pointer, int button) {
                dragStart.set(screenToWorld(screenX, screenY));
                System.out.println("START "+dragStart);
                System.out.println("END:"+dragEnd);
                Bird testBird = birds.get(currindex);
                testBird.getBirdbody().setActive(true);
                testBird.activateSpecialAbility();

                System.out.println("TOUCHDOWN FUNCTION ");

                isDragging = true;
                return true;
            }

            @Override
            public boolean touchDragged(int screenX, int screenY, int pointer) {
                if (isDragging) {
                    Bird testBird = birds.get(currindex);
                    testBird.getBirdbody().setActive(true);

                    dragEnd.set(screenToWorld(screenX, screenY));
                    System.out.println("START"+dragStart);
                    System.out.println("END"+dragEnd);
                    System.out.println("TOUCHDRAG FUNCTION " + dragEnd);

                }
                return true;
            }

            @Override
            public boolean touchUp(int screenX, int screenY, int pointer, int button) {
                if (isDragging) {
                    Bird testBird = birds.get(currindex);

                    testBird.getBirdbody().setActive(true);
                    testBird.activateSpecialAbility();
                    dragEnd.set(screenToWorld(screenX, screenY));
                    Vector2 launchForce = dragStart.cpy().sub(dragEnd).scl(0.0f);
                    testBird.launch(dragStart, launchForce);
                    System.out.println("TOUCHUP FUNCTION " + dragEnd + launchForce);
                    System.out.println("START"+dragStart);
                    System.out.println("END"+dragEnd);

                    isDragging = false;
                }
                return true;
            }
        });
    }


    private void createGround() {
        // Define the body and its properties
        BodyDef groundBodyDef = new BodyDef();
        groundBodyDef.position.set(400, 70); // Center the ground horizontally and set it near the bottom
        groundBodyDef.type = BodyDef.BodyType.StaticBody;

        Body groundBody = world.createBody(groundBodyDef);

        // Define the shape and fixture
        PolygonShape groundShape = new PolygonShape();
        groundShape.setAsBox(400, 10); // Ground width = 800, height = 20 (scaled to Box2D units)

        FixtureDef groundFixtureDef = new FixtureDef();
        groundFixtureDef.shape = groundShape;
        groundFixtureDef.friction = 0.8f; // Add friction for realism
        groundBody.createFixture(groundFixtureDef);

        // Dispose of the shape after use
        groundShape.dispose();
    }

    private Vector2 screenToWorld(int screenX, int screenY) {
        Vector2 worldCoords = new Vector2(screenX, screenY);
        viewport.unproject(worldCoords);
        return worldCoords;
    }


    @Override
    public void show() {
        catapult = new Catapult(50, 50, "Catapult.png");
    }


    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        stage.act();
        stage.draw();

        hud.updateTimer(delta);

        // Step the physics simulation
        world.step(1 / 60f, 6, 2);

        pig.update(delta);


        // Get the current bird
        Bird currentBird = birds.get(currindex);

        // Handle when a bird goes out of bounds
        if (currentBird.getX() < 0 || currentBird.getX() > viewport.getWorldWidth() ||
            currentBird.getY() < 0 || currentBird.getY() > viewport.getWorldHeight()


        ) {

            // Decrease lives and remove the current bird
            hud.decrementLives();
            birds.removeIndex(currindex);

            // Ensure the current index is within bounds
            if (currindex >= birds.size) {
                currindex = birds.size - 1; // Reset to last bird if needed
            }

            if (!birds.isEmpty()) {
                Bird nextBird = birds.get(currindex);
                nextBird.getBirdbody().setTransform(100, 200, 0); // Reset position to catapult
                nextBird.getBirdbody().setLinearVelocity(0, 0); // Stop moving
                nextBird.getBirdbody().setActive(false); // Reactivate bird for launch
            } else {
                game.setScreen(new GameOverScreen(game)); // Game over if no birds left
            }
        }

        game.batch.setProjectionMatrix(camera.combined);
        game.batch.begin();

        catapult.render(game.batch);
        pig.draw(game.batch);

        // Draw and update all birds
        for (Bird bird : birds) {
            bird.draw(game.batch);
            bird.update(delta);
        }

        for(Block block:blocks){
            block.draw(game.batch);
            block.update(delta);
//            block.getBody().setActive(false);
//            System.out.println("PIGS POSITION"+pig.getX()+","+pig.getY());
//            System.out.println("BLOCK POSITION"+block.getX()+","+block.getY());

        }

        game.batch.end();
        hud.draw(game.batch);
        debugRenderer.render(world, camera.combined);

        // Check for SPACE key press to trigger special ability
        if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) {
            System.out.println("SPACE pressed! Activating special ability.");
            currentBird.useSpecialAbility();
        }

        // Other key press detection (e.g., for GameOverScreen or PauseScreen)
        if (Gdx.input.isKeyJustPressed(Input.Keys.W)) {
            game.setScreen(new GameOverScreen(game));
        }

        if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
            game.setScreen(new PauseScreen(game));
        }



        game.batch.setProjectionMatrix(camera.combined);
        game.batch.begin();

        catapult.render(game.batch);
        pig.draw(game.batch);

        // Draw all birds
        for (Bird bird : birds) {
            bird.draw(game.batch);
            bird.update(delta);
        }

        game.batch.end();

        hud.draw(game.batch);
        debugRenderer.render(world, camera.combined);

        if (Gdx.input.isKeyJustPressed(Input.Keys.W)) {
            game.setScreen(new GameOverScreen(game)); // Restart the game or go back to the main menu
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
            game.setScreen(new PauseScreen(game));
        }
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height);
        stage.getViewport().update(width, height, true);
    }

    @Override
    public void pause() {}

    @Override
    public void resume() {}

    @Override
    public void hide() {}

    @Override
    public void dispose() {
        world.dispose();
        debugRenderer.dispose();
        hud.dispose();
    }
}
