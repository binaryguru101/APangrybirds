package io.github.binaryguru101.AP.Catapult;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.viewport.Viewport;

public class TrajectoryView {
    private Viewport viewport;
    private Texture texture;
    private SlingShot slingshot;
    private Vector2 birdPos;  // Store bird position instead of passing Bird object
    private boolean isVisible;  // Visibility flag


    public TrajectoryView(Viewport viewport,SlingShot slingshot) {
        texture = new Texture("thread.png");
        this.viewport = viewport;
        this.slingshot = slingshot;
        this.birdPos = new Vector2();
        this.isVisible = true;
    }

    public void Render(SpriteBatch batch){
        if(isVisible){
            Vector2 slingshotStart =slingshot.getStart_position();

            Vector2 slingStartPosPixels = viewport.project(new Vector2(slingshotStart).scl(5f));
            Vector2 birdPosPixels = viewport.project(new Vector2(birdPos).scl(5f));

            Vector2 direction = birdPosPixels.cpy().sub(slingStartPosPixels);
            float slingLength = direction.len();
            float slingAngle = direction.angleDeg();

            batch.begin();
            // Draw the texture representing the thread stretched between the sling and the bird
            batch.draw(texture,
                slingStartPosPixels.x, slingStartPosPixels.y,  // Start position in pixels
                0, 0,  // Origin for rotation
                slingLength, texture.getHeight(),  // Width (distance between bird and sling) and height of the texture
                1, 1,  // Scaling factors
                slingAngle,  // Rotation angle
                0, 0,  // Source texture position (starting at 0,0 of texture)
                texture.getWidth(), texture.getHeight(),  // Texture size
                false, false);  // Do not flip the texture
            batch.end();
        }
    }

    public void dispose(){
        texture.dispose();
    }

    public void SetVisible(boolean visible){
        isVisible = visible;
    }
}
