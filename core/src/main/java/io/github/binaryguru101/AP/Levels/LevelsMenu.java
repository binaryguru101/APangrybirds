package io.github.binaryguru101.AP.Levels;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.viewport.Viewport;

import io.github.binaryguru101.AP.Collisions.ContactManager;
import io.github.binaryguru101.AP.Screens.Main;

public class LevelsMenu {
    private Main main;
    private Texture background;
    private Texture randomLevelButton;
    private Texture lockedLevelTexture;
    private Texture unlockedLevelTexture;
    private Texture exitButton;
    private Viewport viewport;
    public ContactManager collisionManager;

    // Status for each level (locked/unlocked)
    public static boolean[] levelStatuses = {true, false, false};

    public LevelsMenu(Main main, Viewport viewport) {
        this.main = main;
        this.viewport = viewport;

        background = new Texture("Background.jpg");
        randomLevelButton = new Texture("level1.png");
        lockedLevelTexture = new Texture("Level2.png");
        unlockedLevelTexture = new Texture("Level3.png");
        exitButton = new Texture("Quit.png");
        collisionManager = new ContactManager();
    }

    public void render(SpriteBatch batch) {
        batch.begin();
        // Draw the level menu background
        batch.draw(background, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        // Draw Random Level button
        batch.draw(randomLevelButton, Gdx.graphics.getWidth() / 2 - 50, Gdx.graphics.getHeight() / 2 + 50, 100, 50);

        // Draw the unlocked level button
        float unlockedLevelXPos = (Gdx.graphics.getWidth() / 4) - 50;
        float levelYPos = Gdx.graphics.getHeight() / 2 - 50;
        batch.draw(unlockedLevelTexture, unlockedLevelXPos, levelYPos, 100, 100);

        // Draw the locked levels
        for (int i = 1; i < 3; i++) {
            float levelXPos = (Gdx.graphics.getWidth() / 4) * (i + 1) - 50;
            if (levelStatuses[i]) {
                batch.draw(unlockedLevelTexture, levelXPos, levelYPos, 100, 100);  // Draw unlocked level button
            } else {
                batch.draw(lockedLevelTexture, levelXPos, levelYPos, 100, 100);  // Draw locked level button
            }
        }

        // Draw Exit button
        batch.draw(exitButton, Gdx.graphics.getWidth() - 120, 20, 100, 50);
        batch.end();

        // Handle input for buttons
        if (Gdx.input.justTouched()) {  // Detects only a single touch event
            float touchX = Gdx.input.getX();
            float touchY = Gdx.graphics.getHeight() - Gdx.input.getY();  // Invert Y axis

            // Check if Random Level button is pressed
            if (touchX > Gdx.graphics.getWidth() / 2 - 50 && touchX < Gdx.graphics.getWidth() / 2 + 50 &&
                touchY > Gdx.graphics.getHeight() / 2 + 50 && touchY < Gdx.graphics.getHeight() / 2 + 100) {
                // Start a random level
                World world = new World(new Vector2(0, -9.8f), true);
                world.setContactListener(collisionManager);

                BaseLevel structure = new BaseLevel(world, new Vector2(10, 3), 2,-1);  // Adjust structure position as needed
                main.startLevel(new LevelManager(viewport, structure, world));
            }

            // Check if any level button is pressed
            for (int i = 0; i < 3; i++) {
                float levelXPos = (Gdx.graphics.getWidth() / 4) * (i + 1) - 50;
                if (touchX > levelXPos && touchX < levelXPos + 100 &&
                    touchY > levelYPos && touchY < levelYPos + 100) {
                    if (i==0) {
                        resetLevel(i);
                    } else {
                        // Show a message or feedback that the level is locked
                        System.out.println("Level " + (i + 1) + " is locked!");
                    }
                }
            }

        }
    }


    // Function to initialize different structures for each level
    private BaseLevel getLevelStructure(int levelIndex, World world) {
        switch (levelIndex) {
            case 0:  // Level 1 Structure
                return new BaseLevel(world, new Vector2(10, 3), 2, 3);  // Level 1 structure with 2 floors
            case 1:  // Level 2 Structure
                return new BaseLevel(world, new Vector2(12, 4), 3, -1);  // Level 2 structure with 3 floors
            case 2:  // Level 3 Structure
                return new BaseLevel(world, new Vector2(14, 5), 4, 2);  // Level 3 structure with 4 floors
            default:
                return new BaseLevel(world, new Vector2(10, 3), 2, 0);  // Default to Level 1 structure
        }
    }

    // Function to reinitialize and load the corresponding level structure
    public void resetLevel(int levelIndex) {
        if (levelIndex >= 0 && levelIndex < levelStatuses.length) {
            // Reset the status of the level to "unlocked"
            levelStatuses[levelIndex] = true;

            // Reset the structure for the selected level
            World world = new World(new Vector2(0, -9.8f), true);
            world.setContactListener(collisionManager);

            BaseLevel structure = getLevelStructure(levelIndex, world);  // Get the structure based on the level
            main.startLevel(new LevelManager(viewport, structure, world));

            System.out.println("Level " + (levelIndex + 1) + " has been reset!");
        }
    }

    public void dispose() {
        background.dispose();
        randomLevelButton.dispose();
        lockedLevelTexture.dispose();
        unlockedLevelTexture.dispose();
        exitButton.dispose();
    }
}
