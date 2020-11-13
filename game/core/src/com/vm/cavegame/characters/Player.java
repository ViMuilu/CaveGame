
package com.vm.cavegame.characters;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;
public class Player extends Sprite{
    public World world;
    public Body playerpBody;
    
    public Player (World world){
        this.world = world;
        setPlayer();
    }
    
    public void setPlayer(){
        BodyDef bdef = new BodyDef();
        bdef.position.set(100,100);
        bdef.type = BodyDef.BodyType.DynamicBody;
        playerpBody = world.createBody(bdef);
        
        FixtureDef fdef = new FixtureDef();
        
        
    }
    
}
