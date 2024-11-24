package io.github.binaryguru101.AP.Levels;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import io.github.binaryguru101.AP.Blocks.Block;
import io.github.binaryguru101.AP.Blocks.WoodenBlock;
import io.github.binaryguru101.AP.Pigs.BigPig;
import io.github.binaryguru101.AP.Pigs.NormalPig;
import io.github.binaryguru101.AP.Pigs.Pigs;
import io.github.binaryguru101.AP.Pigs.SmallPig;

import java.util.ArrayList;
import java.util.List;

public class BaseLevel {
    private List<WoodenBlock> blocks;  // List to hold all blocks in the structure
    private List<NormalPig> pigs;      // List to hold pigs in the structure

    public BaseLevel(World world, Vector2 startPosition, int level1_blocks,int level2_blocks) {
        blocks = new ArrayList<>();
        pigs = new ArrayList<>();
        createStructure(world, startPosition, level1_blocks,level2_blocks);
    }

    private void createStructure(World world, Vector2 startPosition, int numFloors1, int numFloors2) {
        // Check if numFloors1 is not -1 and create the first building
        if (numFloors1 != -1) {
            createBuilding(world, startPosition, numFloors1);  // Create the first building
        }

        // Check if numFloors2 is not -1 and create the second building
        if (numFloors2 != -1) {
            // Adjust the starting position for the second building (next to the first one)
            Vector2 secondBuildingStartPos = new Vector2(startPosition.x + 2.8f * 2, startPosition.y);
            createBuilding(world, secondBuildingStartPos, numFloors2);  // Create the second building
        }
    }

    private void createBuilding(World world, Vector2 startPosition, int numFloors) {
        // Starting position for the bottom-left block
        Vector2 position = new Vector2(startPosition.x , startPosition.y);

        if (numFloors == 0) {
            // Create a single pig at the starting position without any blocks
            Vector2 pigPosition = new Vector2(startPosition.x + 2.8f / 2, startPosition.y + 1f);
            NormalPig pig = new SmallPig(world, pigPosition);  // You can choose which pig type to create
            pigs.add(pig);
            return; // No need to create blocks if there are no floors
        }

        // Create blocks and pigs for each floor
        for (int floor = 0; floor < numFloors; floor++) {
            // Calculate the vertical offset for the floor
            float yOffset = (2.8f + 0.4f) * floor;

            // Vertical blocks (left and right)
            Vector2 leftVerticalBlockPos = new Vector2(position.x + 0.4f / 2, position.y + yOffset + 0.4f / 2);
            WoodenBlock leftVerticalBlock = new WoodenBlock(world, leftVerticalBlockPos);
            leftVerticalBlock.getBody().setTransform(leftVerticalBlock.getBody().getPosition(), (float) Math.toRadians(90));  // Rotate by 90 degrees
            blocks.add(leftVerticalBlock);

            Vector2 rightVerticalBlockPos = new Vector2(position.x + 2.8f - 0.4f / 2, position.y + yOffset + 0.4f / 2);
            WoodenBlock rightVerticalBlock = new WoodenBlock(world, rightVerticalBlockPos);
            rightVerticalBlock.getBody().setTransform(rightVerticalBlock.getBody().getPosition(), (float) Math.toRadians(90));  // Rotate by 90 degrees
            blocks.add(rightVerticalBlock);

            // Horizontal block (on top of the two vertical blocks)
            Vector2 horizontalBlockPos = new Vector2(position.x + 2.8f / 2, position.y + yOffset + 2.8f / 2 + 0.4f); // The Y offset is the height of the vertical blocks
            WoodenBlock horizontalBlock = new WoodenBlock(world, horizontalBlockPos);
            blocks.add(horizontalBlock);

            // Pig (BigPig for the first floor if numFloors > 1, otherwise SmallPig)
            Vector2 pigPosition = new Vector2(position.x + 2.8f / 2, position.y + yOffset - 2.8f / 2 + 1f);
            NormalPig pig;
            if (floor == 0 && numFloors > 1) {
                pig = new BigPig(world, pigPosition);  // First pig is BigPig if there are multiple floors
            } else {
                pig = new SmallPig(world, pigPosition);  // Otherwise, SmallPig
            }
            pigs.add(pig);
        }
    }

    public void render(SpriteBatch batch){
            for(WoodenBlock block : blocks){
                block.Render(batch);
            }

    }

    public List<WoodenBlock> getBlocks() {
        return blocks;
    }

    public void setBlocks(List<WoodenBlock> blocks) {
        this.blocks = blocks;
    }

    public List<NormalPig> getPigs() {
        return pigs;
    }

    public void setPigs(List<NormalPig> pigs) {
        this.pigs = pigs;
    }
}
