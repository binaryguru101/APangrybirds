package io.github.binaryguru101.AP.Birds;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;

public abstract class Angrybird extends GameMembers {
    World world;
    private float diameter;
    private Texture texture;
    private Body bird;
    private float density;

    public Angrybird(World world, float diameter, Vector2 Startpos,String image,float density) {
        this.world = world;
        this.diameter = diameter;
        this.texture = new Texture(image);
        this.density = density;

        Init_Bird(world,Startpos);
        bird.setUserData(this);
    }

    public boolean IsColliding(){return this.collision;}

    @Override
    public void destory(){
        if(world!=null && bird!=null){
            world.destroyBody(bird);
            System.out.println("REMOVING BIRD "+bird);
        }
        if(texture!=null){
            texture.dispose();

        }
    }

    public void Init_Bird(World world, Vector2 Startpos){
        BodyDef bdef = new BodyDef();
        bdef.type = BodyDef.BodyType.DynamicBody;
        bdef.position.set(Startpos);

        bird = world.createBody(bdef);

        CircleShape cshape = new CircleShape();
        cshape.setRadius(diameter/2*5f);

        FixtureDef fdef = new FixtureDef();
        fdef.shape = cshape;
        fdef.density = density;
        fdef.friction = 0.8f;

        bird.createFixture(fdef);

        cshape.dispose();
    }


    public void render(SpriteBatch batch){
        if(health>0){
            float dimensions = diameter/2*5f;
            float X_pos =bird.getPosition().x;
            float Y_pos =bird.getPosition().y;

            batch.begin();

            batch.draw(texture,X_pos-dimensions,Y_pos-dimensions,dimensions,dimensions);
            batch.end();
        }
    }

    public abstract  void UseSpecialAbility();

    public Body GetBird(){
        return bird;
    }

}
