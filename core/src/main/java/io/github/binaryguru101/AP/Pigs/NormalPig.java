package io.github.binaryguru101.AP.Pigs;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import io.github.binaryguru101.AP.Birds.GameMembers;

public class NormalPig extends GameMembers {
    private Texture texture;
    private Body body;
    private float density;
    World world;
    private float diameter;


    public NormalPig(World world, float density, float diameter, float health, String image, Vector2 position) {
        this.world = world;
        this.density = density;
        this.diameter = diameter;
        this.health = health;
        this.texture= new Texture(image);

        Init_Pig(world,position);
        body.setUserData(this);
    }

    public void Init_Pig(World world, Vector2 position) {
        BodyDef pigBodyDef = new BodyDef();
        pigBodyDef.type = BodyDef.BodyType.DynamicBody;

        pigBodyDef.position.set(position);

        body = world.createBody(pigBodyDef);

        CircleShape pigShape = new CircleShape();
        pigShape.setRadius((diameter / 2) * 5f);

        FixtureDef pigFixtureDef = new FixtureDef();
        pigFixtureDef.shape = pigShape;
        pigFixtureDef.density = density;

        body.createFixture(pigFixtureDef);
        pigShape.dispose();
    }

    public void render(SpriteBatch batch){
        if (health>0) {
            float pigDiameterInPixels = diameter * 5f;
            float pigPosX = body.getPosition().x;
            float pigPosY = body.getPosition().y;

            batch.begin();
            batch.draw(texture, pigPosX - pigDiameterInPixels / 2,
                pigPosY - pigDiameterInPixels / 2,
                pigDiameterInPixels, pigDiameterInPixels);
            batch.end();
        }
    }
    public boolean isAlive () {
        return health>0;
    }

    public void dispose() {
        if (texture != null) {
            texture.dispose();
            texture = null;  // Mark it null to prevent multiple disposals
        }
    }
    public Body getBody() {
        return body;
    }

    public Vector2 getPosition() {
        if (body != null) {
            return body.getPosition();
        }
        return new Vector2(0, 0);
    }


    @Override
    public void destory(){
        System.out.println("Pig collapsed! Removing texture and destroying body.");

        if (body != null) {
            world.destroyBody(body);
            body = null;
        }

        // Remove the texture
        if (texture != null) {
            texture.dispose();
            texture = null;
        }
    }

    public void TakeDamage(float damage){
        this.health -= damage;
    }
}
