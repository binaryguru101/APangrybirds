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
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
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
import io.github.binaryguru101.AP.Birds.Red;
import io.github.binaryguru101.AP.Collisions.Contact;
import io.github.binaryguru101.AP.Collisions.Trajectory;
import io.github.binaryguru101.AP.HUD.HUD;
import io.github.binaryguru101.AP.Pigs.Pigs;
import io.github.binaryguru101.AP.Catapult.Catapult;
import io.github.binaryguru101.AP.Blocks.Block; // Import the Block class
import com.badlogic.gdx.scenes.scene2d.Stage;
import io.github.binaryguru101.AP.UI.PauseButton;


public class PlayScreen implements Screen {
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
        world.setContactListener(new Contact());

        // Add one test bird
        testBird = new Red(world, 100f, 200f, "Red.png");

        pig = new Pigs(world, 600f, 200f, "Pigs.png");

        // Add blocks around the pig
        createBlocks();



        trajectory = new Trajectory();
        shapeRenderer = new ShapeRenderer();

        backgroundTexture = new Texture(Gdx.files.internal("Background.jpg"));

        // Stage background
        stage.addActor(new Image(backgroundTexture));

        // Catapult setup
        catapult = new Catapult(50, 50, "Catapult.png");

        //blocks
        blocks = new Array<>();

        Gdx.input.setInputProcessor(new InputAdapter() {
            @Override
            public boolean touchDown(int screenX, int screenY, int pointer, int button) {
                dragStart.set(screenToWorld(screenX, screenY));
                isDragging = true;
                return true;
            }

            @Override
            public boolean touchDragged(int screenX, int screenY, int pointer) {
                if (isDragging) {
                    dragEnd.set(screenToWorld(screenX, screenY));
                }
                return true;
            }

            @Override
            public boolean touchUp(int screenX, int screenY, int pointer, int button) {
                if (isDragging) {
                    dragEnd.set(screenToWorld(screenX, screenY));
                    Vector2 launchForce = dragStart.cpy().sub(dragEnd).scl(0.05f);
                    testBird.launch(dragStart, launchForce);
                    drawTrajectory(dragStart, launchForce);
                    isDragging = false;
                }
                return true;
            }
        });
    }

    private void createBlocks() {

            try {
                Block block1 = new Block(world, 570f, 210f, "Block.png");
                blocks.add(block1);

                Block block2 = new Block(world, 630f, 210f, "Block.png");
                blocks.add(block2);

                Block block3 = new Block(world, 600f, 250f, "Block.png");
                blocks.add(block3);

                Block block4 = new Block(world, 600f, 180f, "Block.png");
                blocks.add(block4);
            } catch (Exception e) {
                Gdx.app.error("Error", "Block creation failed", e);
            }
        }


    private void createUI() {
        pauseTexture = new Texture(Gdx.files.internal("Pause.png"));

        // Create the pause button using the loaded image
        TextureRegionDrawable drawable = new TextureRegionDrawable(pauseTexture);
        pauseButton = new ImageButton(drawable);

        pauseButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                // Show the pause screen
                game.setScreen(new PauseScreen(game));
            }
        });

        stage.addActor(pauseButton);

        pauseButton.setPosition(Gdx.graphics.getWidth() - pauseButton.getWidth() - 10, Gdx.graphics.getHeight() - pauseButton.getHeight() - 10);
    }

    private Vector2 screenToWorld(int screenX, int screenY) {
        Vector2 worldCoords = new Vector2(screenX, screenY);
        viewport.unproject(worldCoords);
        return worldCoords;
    }


    //Work in Progress
    public void drawTrajectory(Vector2 startPosition, Vector2 launchForce) {
        shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
        shapeRenderer.setColor(Color.RED);

        // Simulate trajectory
        Vector2 currentPosition = new Vector2(startPosition);
        float timeStep = 0.1f;
        float totalTime = 2f;

        for (float t = 0; t < totalTime; t += timeStep) {
            Vector2 trajectoryPoint = currentPosition.cpy().add(launchForce.cpy().scl(t));
            trajectoryPoint.y -= 0.5f * 9.8f * t * t; // Gravity effect

            if (t > 0) {
                shapeRenderer.line(lastPoint.x, lastPoint.y, trajectoryPoint.x, trajectoryPoint.y);
            }
            lastPoint.set(trajectoryPoint);
        }
        shapeRenderer.end();
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

        testBird.update(delta);
        pig.update(delta);

        // If bird goes out of bounds, reset it
        if (testBird.getX() < 0 || testBird.getX() > viewport.getWorldWidth() ||
            testBird.getY() < 0 || testBird.getY() > viewport.getWorldHeight()) {
            testBird.reset();
            hud.decrementLives();
        }

        game.batch.setProjectionMatrix(camera.combined);
        game.batch.begin();

        catapult.render(game.batch);
        testBird.draw(game.batch);
        pig.draw(game.batch);

        for(Block block : blocks){
            block.draw(game.batch);
        }

        game.batch.end();

        hud.draw(game.batch);
        debugRenderer.render(world, camera.combined);


        if (Gdx.input.isKeyJustPressed(com.badlogic.gdx.Input.Keys.W)) {
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
