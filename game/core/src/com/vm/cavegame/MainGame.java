package com.vm.cavegame;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;

import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Animation.PlayMode;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMapRenderer;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;

import com.vm.cavegame.map.Map;

public class MainGame extends ApplicationAdapter {

    private Animation<TextureRegion> runningAnimationUp;
    private Animation<TextureRegion> runningAnimationDown;
    private Animation<TextureRegion> runningAnimationLeft;
    private Animation<TextureRegion> runningAnimationRight;
    private Animation<TextureRegion> idleAnimationRight;
    private Animation<TextureRegion> idleAnimationDown;
    private Animation<TextureRegion> idleAnimationLeft;
    private Animation<TextureRegion> idleAnimationUp;
    private String idleAnimation;
   
    private TiledMapRenderer renderer;
    private World world;
    private Body body;
    private SpriteBatch batch;
    private TextureAtlas atlas;
    private Sprite player;
    private float playerX;
    private float playerY;
    private OrthographicCamera camera;
    private float moveAmount = 1.0f;
    private final float PIXELS_TO_METERS = 100f;
    private Box2DDebugRenderer debugRenderer;
    private float elapsedTime = 0;
    private boolean drawSprite = true;
    private Matrix4 debugMatrix;
    
    @Override
    public void create() {
        float w = Gdx.graphics.getWidth();
        float h = Gdx.graphics.getHeight();

        batch = new SpriteBatch();
        atlas = new TextureAtlas("textures.atlas");
        player = new Sprite(atlas.findRegions("characters/MC/Character/CharacterDown").get(0));
        player.setPosition(1, 1);
        runningAnimationUp = new Animation<TextureRegion>(0.033f, atlas.findRegions("characters/MC/Character/CharacterUp"), PlayMode.LOOP);
        runningAnimationDown = new Animation<TextureRegion>(0.033f, atlas.findRegions("characters/MC/Character/CharacterDown"), PlayMode.LOOP);
        runningAnimationLeft = new Animation<TextureRegion>(0.033f, atlas.findRegions("characters/MC/Character/CharacterLeft"), PlayMode.LOOP);
        runningAnimationRight = new Animation<TextureRegion>(0.033f, atlas.findRegions("characters/MC/Character/CharacterRight"), PlayMode.LOOP);
        
        idleAnimationRight = new Animation<TextureRegion>(0.033f, atlas.findRegions("characters/MC/Character/CharacterRight").get(0)); 
        idleAnimationUp = new Animation<TextureRegion>(0.033f, atlas.findRegions("characters/MC/Character/CharacterUp").get(0)); 
        idleAnimationDown = new Animation<TextureRegion>(0.033f, atlas.findRegions("characters/MC/Character/CharacterDown").get(0)); 
        idleAnimationLeft = new Animation<TextureRegion>(0.033f, atlas.findRegions("characters/MC/Character/CharacterLeft").get(0)); 
        
        idleAnimation = "Down";
        world = new World(new Vector2(0f, 0f), true);

        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.position.set((player.getX() + player.getWidth() / 2)
                / PIXELS_TO_METERS,
                (player.getY() + player.getHeight() / 2) / PIXELS_TO_METERS);
        body = world.createBody(bodyDef);
        PolygonShape shape = new PolygonShape();
        shape.setAsBox(player.getWidth() / 4 / PIXELS_TO_METERS, player.getHeight()
                / 3 / PIXELS_TO_METERS);

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        fixtureDef.density = 0.1f;

        body.createFixture(fixtureDef);
        shape.dispose();

        camera = new OrthographicCamera();
        camera.setToOrtho(false, (w / h) * 320, 320);
        camera.update();

        Map map = new Map();

        renderer = new OrthogonalTiledMapRenderer(map.renderMap(world));

        debugRenderer = new Box2DDebugRenderer();
        camera = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.
                getHeight());

    }

    @Override
    public void render() {
        elapsedTime += Gdx.graphics.getDeltaTime()/7;
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        camera.update();
        player.setPosition(playerX, playerY);
        renderer.setView(camera);
        renderer.render();
        Animation<TextureRegion> animation = null;
        
        if(idleAnimation.equals("Down")){
            animation = idleAnimationDown;
        } else if(idleAnimation.equals("Up")){
            animation = idleAnimationUp;
        } else if(idleAnimation.equals("Left")){
            animation = idleAnimationLeft;
        } else if(idleAnimation.equals("Right")){
            animation = idleAnimationRight;
        }
        world.step(1f / 60f, 6, 2);

        batch.setProjectionMatrix(camera.combined);
        debugMatrix = batch.getProjectionMatrix().cpy().scale(PIXELS_TO_METERS,
                PIXELS_TO_METERS, 0);
        
        batch.begin();

        player.setPosition((body.getPosition().x * PIXELS_TO_METERS) - player.
                getWidth() / 2,
                (body.getPosition().y * PIXELS_TO_METERS) - player.getHeight() / 2);

        if(playerMovement().equals("Up")){
            animation = runningAnimationUp;
        } else if(playerMovement().equals("Down")){
            animation = runningAnimationDown;
        }else if(playerMovement().equals("Left")){
            animation = runningAnimationLeft;
        }else if(playerMovement().equals("Right")){
            animation = runningAnimationRight;
        }
        camera.position.set(player.getX(), player.getY(), 0);
        camera.update();
        
        batch.draw(animation.getKeyFrame(elapsedTime,true), player.getX(), player.getY(), player.getOriginX(),
                player.getOriginY(),
                player.getWidth(), player.getHeight(), player.getScaleX(), player.
                getScaleY(), player.getRotation());

        batch.end();
        debugRenderer.render(world, debugMatrix);
    }

    @Override
    public void dispose() {
        batch.dispose();
        atlas.dispose();
        world.dispose();

    }
    private static float speed = 1f;

    private String playerMovement() {
        if (Gdx.input.isKeyPressed(Input.Keys.W) || Gdx.input.isKeyPressed(Input.Keys.UP)) {
            idleAnimation = "Up";
            if (Gdx.input.isKeyPressed(Input.Keys.A) || Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
                body.setLinearVelocity(-speed, speed);
                return "Up";
            } else if (Gdx.input.isKeyPressed(Input.Keys.D) || Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
                body.setLinearVelocity(speed, speed);
                return "Up";
            } else {
                body.setLinearVelocity(0f, speed);
                return "Up";
            }
        } else if (Gdx.input.isKeyPressed(Input.Keys.S) || Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
            idleAnimation = "Down";
            if (Gdx.input.isKeyPressed(Input.Keys.A) || Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
                body.setLinearVelocity(-speed, -speed);
                return "Down";
            } else if (Gdx.input.isKeyPressed(Input.Keys.D) || Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
                body.setLinearVelocity(speed, -speed);
                return "Down";
            } else {
                body.setLinearVelocity(0f, -speed);
                return "Down";
            }
        } else if (Gdx.input.isKeyPressed(Input.Keys.A) || Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
            idleAnimation = "Left";
            body.setLinearVelocity(-speed, 0f);
            return "Left";
        } else if (Gdx.input.isKeyPressed(Input.Keys.D) || Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
            idleAnimation = "Right";
            body.setLinearVelocity(speed, 0f);
            return "Right";
        } else {
            body.setLinearVelocity(0f, 0f);
            return "";
        }
        
    }
}
