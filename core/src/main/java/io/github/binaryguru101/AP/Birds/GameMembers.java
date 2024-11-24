package io.github.binaryguru101.AP.Birds;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;

import java.util.ArrayList;
import java.util.List;

public abstract class GameMembers {
    public float health;
    public Body body;
    public boolean isLaunched= false;
    public Texture texture;
    public boolean collision=false;
    public World world;


    private static List<GameMembers> members = new ArrayList<>();

    public void _Collision(Body currbody,float Force){
        if(isLaunched){
            collision=true;
        }

        float thresh =1f;
        if(Force>=thresh){
            float damage =(Force-thresh);
            health+=damage;
            System.out.println(damage);

            if(health<=0){
                _Dead();
            }
        }
    }

    public void _Dead(){
        members.add(this);
    }


    public static void _RemoveMembers(){
        for(GameMembers member : members){
            member.destory();
        }
        members.clear();
    }

    public void HealthUpdate(){
        this.health=30f;
    }

    public abstract void destory();
}
