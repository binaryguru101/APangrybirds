package io.github.binaryguru101.AP.Blocks;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import io.github.binaryguru101.AP.Birds.GameMembers;

public class WoodenBlock extends GameMembers {
    private Texture texture;
    private TextureRegion block;
    World world;
    private Body Body;


    public WoodenBlock(World world, Vector2 pos) {
        texture = new Texture("Block.png");

        block = new TextureRegion(texture);

        Init_Block(world, pos);
        this.health=5f;
        Body.setUserData(this);
        this.world = world;

    }


    @Override
    public void destory() {
        System.out.println("Destory");

        if(body!=null){
            world.destroyBody(body);
        }

        if(texture!=null){
            texture.dispose();

        }
    }

    public void ReduceHealth(float amount){
        if(health>0){
            health-=amount;
        }
        if(health<=0){
            this.destory();
        }
    }

    public void Init_Block(World world, Vector2 pos){
        BodyDef blockbod = new BodyDef();
        blockbod.position.set(pos);
        blockbod.type = BodyDef.BodyType.DynamicBody;

        Body = world.createBody(blockbod);
        PolygonShape shape = new PolygonShape();
        shape.setAsBox(2.8f, 0.4f);
        FixtureDef fdef = new FixtureDef();
        fdef.shape = shape;
        fdef.density = 1f;

        fdef.friction = 0f;

        Body.createFixture(fdef);
        shape.dispose();
    }

    public void Render(SpriteBatch sr){
        if(health<=0){
            return;
        }
        Vector2 pos = Body.getPosition();

        float x=(pos.x-2.8f/2) ;
        float y= (pos.y-0.4f/2);

        float rotation = Body.getAngle();
        float actual_rotation = (float) Math.toDegrees((double)rotation);

        sr.begin();

        sr.draw(block, x, y,2.8f/2,0.4f/2,2.8f,0.4f,1,1,actual_rotation);
        sr.end();
    }

    public void dispose(){
        if(texture!=null){
            texture.dispose();
        }
    }
    public Body getBody(){
        return Body;
    }

    public Vector2 getPosition(){
        return body!=null?body.getPosition():new Vector2(0,0);
    }
}
