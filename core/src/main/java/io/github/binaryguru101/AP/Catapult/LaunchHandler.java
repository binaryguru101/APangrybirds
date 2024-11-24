package io.github.binaryguru101.AP.Catapult;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.viewport.Viewport;
import io.github.binaryguru101.AP.Birds.Angrybird;
import io.github.binaryguru101.AP.Blocks.WoodenBlock;
import io.github.binaryguru101.AP.Catapult.SlingShot;
import io.github.binaryguru101.AP.Catapult.TrajectoryView;
import io.github.binaryguru101.AP.Levels.BaseLevel;
import io.github.binaryguru101.AP.Pigs.NormalPig;
import io.github.binaryguru101.AP.Screens.PauseScreen;
import io.github.binaryguru101.AP.Levels.BaseLevel;
import java.util.List;

public class LaunchHandler {
    private List<Angrybird> birds;
    private int currentBirdIndex;
    private SlingShot slingshot;
    private TrajectoryView thread;
    private boolean enableDrag;
    private boolean birdInMotion;
    private PauseScreen pauseScreen;
    private boolean specialAbilityUsed;
    private BaseLevel BaseLevel;


    // Constants
    private static final float MAX_DRAG_DISTANCE = 1f;
    private static final float LAUNCH_FORCE_MULTIPLIER = 400f;
    private static final float MIN_VELOCITY_THRESHOLD = 0.1f;

    // Cached vectors for reuse
    private final Vector2 touchPos = new Vector2();
    private final Vector2 worldCoords = new Vector2();
    private final Vector2 dragPos = new Vector2();
    private final Vector2 launchDirection = new Vector2();
    private final Viewport viewport;
    private Vector2 slingPOS = new Vector2();

    public LaunchHandler(List<Angrybird> birds, BaseLevel BaseLevel, SlingShot slingshot, TrajectoryView thread, PauseScreen pauseScreen, Viewport viewport) {
        this.birds = birds;
        this.currentBirdIndex = 0; // Start with the first bird
        this.slingshot = slingshot;
        this.thread = thread;
        this.viewport = viewport;
        this.enableDrag = false;
        this.pauseScreen = pauseScreen;
        this.specialAbilityUsed = false;
        this.slingPOS = slingshot.getStart_position();
        this.BaseLevel = BaseLevel;
    }

    public void handleInput() {
        System.out.println("Checking Status");
        if (currentBirdIndex >= birds.size()) {
            try{
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

            boolean Win=true;
            for (NormalPig pig : BaseLevel.getPigs()) {
                if (pig.isAlive())
                    Win = false;
                break;
            }
            if (Win) {
                System.out.println("Won!!!!");
            } else {
                System.out.println("Lose");
            }
            return;
        }

        Angrybird currentBird = birds.get(currentBirdIndex);
        currentBird.isLaunched = true;
        float deltaTime = Gdx.graphics.getDeltaTime();
        Vector2 slingStartPos = new Vector2(slingPOS).add(new Vector2(1, 1.7f));

        // Debug logs for sling start position
        System.out.println("Sling Start Position (bird " + (currentBirdIndex + 1) + "): " + slingStartPos);

        if (Gdx.input.justTouched()) {
            touchPos.set(Gdx.input.getX(), Gdx.input.getY());
            worldCoords.set(viewport.unproject(new Vector2(touchPos)));

            // Begin drag
            currentBird.GetBird().setType(BodyDef.BodyType.KinematicBody);
            float distanceToSling = worldCoords.dst(slingStartPos);
            if (distanceToSling > MAX_DRAG_DISTANCE) {
                dragPos.set(worldCoords).sub(slingStartPos).nor().scl(MAX_DRAG_DISTANCE).add(slingStartPos);
            } else {
                dragPos.set(worldCoords);
            }

            enableDrag = true;
            currentBird.GetBird().setTransform(dragPos, 0);
            thread.SetVisible(true);

        } else if (Gdx.input.isTouched() && enableDrag) {
            // Update drag position
            touchPos.set(Gdx.input.getX(), Gdx.input.getY());
            worldCoords.set(viewport.unproject(new Vector2(touchPos)));

            float distanceToSling = worldCoords.dst(slingStartPos);
            if (distanceToSling > MAX_DRAG_DISTANCE) {
                dragPos.set(worldCoords).sub(slingStartPos).nor().scl(MAX_DRAG_DISTANCE).add(slingStartPos);
            } else {
                dragPos.set(worldCoords);
            }

            currentBird.GetBird().setTransform(dragPos, 0);
            thread.SetVisible(true);

        } else if (enableDrag) {
            // Launch bird
            enableDrag = false;

            Vector2 birdPos = currentBird.GetBird().getPosition();
            launchDirection.set(slingStartPos).sub(birdPos).nor();
            float stretchDistance = slingStartPos.dst(birdPos);

            // Debug logs for launch force calculation
            System.out.println("Bird Position: " + birdPos);
            System.out.println("Stretch Distance: " + stretchDistance);
            System.out.println("Delta Time: " + deltaTime);

            currentBird.GetBird().setType(BodyDef.BodyType.DynamicBody);
            currentBird.GetBird().applyLinearImpulse(
                launchDirection.scl(stretchDistance * LAUNCH_FORCE_MULTIPLIER * deltaTime),
                currentBird.GetBird().getWorldCenter(), true
            );

            birdInMotion = true;
            specialAbilityUsed = false;
            thread.SetVisible(false);

            // Debug log for launch velocity
            System.out.println("Bird Launch Velocity: " + currentBird.GetBird().getLinearVelocity());
        }

        // Special ability logic
        if (birdInMotion && Gdx.input.isKeyJustPressed(Input.Keys.SPACE) && !specialAbilityUsed) {
            executeSpecialAbility(currentBird);
            specialAbilityUsed = true;
        }

        // Check if bird stopped or collided
        if (birdInMotion) {
            Vector2 velocity = currentBird.GetBird().getLinearVelocity();
            if (currentBird.IsColliding() || velocity.len() < MIN_VELOCITY_THRESHOLD) {
                moveToNextBird();
            }
        }
    }

    private void moveToNextBird() {
        birdInMotion = false;
        currentBirdIndex++;
        boolean Win=true;
        for (NormalPig pig : BaseLevel.getPigs()) {
            if (pig.isAlive())
                Win = false;
            break;
        }
        if (Win) {
            System.out.println("Won!!!!");
        } else {
            System.out.println("Moving to the next bird.");
        }

    }

    private void executeSpecialAbility(Angrybird bird) {
        if (bird instanceof Angrybird) {
            System.out.println("Red bird special ability: None");
        } else if (bird instanceof Angrybird) {
            Vector2 velocity = bird.GetBird().getLinearVelocity();
            bird.GetBird().setLinearVelocity(velocity.scl(1.5f));
            System.out.println("Chuck bird special ability: Speed boost");
        } else if (bird instanceof Angrybird) {
            dealDamageInRadius(bird.GetBird().getPosition(), 2.0f);
            bird.GetBird().setLinearVelocity(0, 0);
            System.out.println("Bomb bird special ability: Explosive damage");
        }

        moveToNextBird();
    }

    private void dealDamageInRadius(Vector2 position, float radius) {
        System.out.println("Dealing damage in radius around " + position);

        for (NormalPig pig : BaseLevel.getPigs()) {
            float distance = pig.getPosition().dst(position); // Calculate distance
            if (distance <= radius) {
                pig.TakeDamage(5); // Apply 10 damage (example value)
                System.out.println("Damaged pig at " + pig.getPosition() + " within radius.");
            }
        }

        // Damage to blocks
        for (WoodenBlock block : BaseLevel.getBlocks()) {
            float distance = block.getPosition().dst(position); // Calculate distance
            if (distance <= radius) {
                block.ReduceHealth(5);
                System.out.println("Damaged block at " + block.getPosition() + " within radius.");
            }
        }
    }



}
