
package com.vm.cavegame.characters;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.physics.box2d.*;


public class player extends Sprite{
    public World world;
    public Body playerpBody;
    
    public player (World world){
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
