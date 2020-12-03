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
    private Animation<TextureRegion> meleeAnimationUp;
    private Animation<TextureRegion> meleeAnimationDown;
    private Animation<TextureRegion> meleeAnimationLeft;
    private Animation<TextureRegion> meleeAnimationRight;
    private Animation<TextureRegion> meleeAnimationIdleFrame;
    private String idleAnimation;
    private TiledMapRenderer renderer;
    private World world;
    private Body body;
    private SpriteBatch batch;
    private TextureAtlas atlas;
    private Sprite player;
    private OrthographicCamera camera;
    private float moveAmount = 1.0f;
    private final float PIXELS_TO_METERS = 100f;
    private Box2DDebugRenderer debugRenderer;
    private float elapsedTime = 0;
    private boolean drawSprite = true;
    private Matrix4 debugMatrix;
    private static float speed = 1f;

    @Override
    public void create() {
        float w = Gdx.graphics.getWidth();
        float h = Gdx.graphics.getHeight();

        batch = new SpriteBatch();
        atlas = new TextureAtlas("textures.atlas");
        player = new Sprite(atlas.findRegions("characters/MC/Character/CharacterDown").get(0));
        player.setPosition(200, 200);

        //gets frames from texture atlas and makes them in to a loop
        runningAnimationUp = new Animation<TextureRegion>(0.033f, atlas.findRegions("characters/MC/Character/CharacterUp"), PlayMode.LOOP);
        runningAnimationDown = new Animation<TextureRegion>(0.033f, atlas.findRegions("characters/MC/Character/CharacterDown"), PlayMode.LOOP);
        runningAnimationLeft = new Animation<TextureRegion>(0.033f, atlas.findRegions("characters/MC/Character/CharacterLeft"), PlayMode.LOOP);
        runningAnimationRight = new Animation<TextureRegion>(0.033f, atlas.findRegions("characters/MC/Character/CharacterRight"), PlayMode.LOOP);

        idleAnimationRight = new Animation<TextureRegion>(0.033f, atlas.findRegions("characters/MC/Character/CharacterRight").get(0));
        idleAnimationUp = new Animation<TextureRegion>(0.033f, atlas.findRegions("characters/MC/Character/CharacterUp").get(0));
        idleAnimationDown = new Animation<TextureRegion>(0.033f, atlas.findRegions("characters/MC/Character/CharacterDown").get(0));
        idleAnimationLeft = new Animation<TextureRegion>(0.033f, atlas.findRegions("characters/MC/Character/CharacterLeft").get(0));

        meleeAnimationIdleFrame = new Animation<TextureRegion>(0.033f, atlas.findRegions("characters/MC/Weapon/SwordBlank").get(0));
        meleeAnimationUp = new Animation<TextureRegion>(0.033f, atlas.findRegions("characters/MC/Weapon/SwordUp"), PlayMode.LOOP);
        meleeAnimationDown = new Animation<TextureRegion>(0.033f, atlas.findRegions("characters/MC/Weapon/SwordDown"), PlayMode.LOOP);
        meleeAnimationLeft = new Animation<TextureRegion>(0.033f, atlas.findRegions("characters/MC/Weapon/SwordLeft"), PlayMode.LOOP);
        meleeAnimationRight = new Animation<TextureRegion>(0.033f, atlas.findRegions("characters/MC/Weapon/SwordRight"), PlayMode.LOOP);
        idleAnimation = "Down";

        world = new World(new Vector2(0f, 0f), true);
        //sets up player hitbox
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
        fixtureDef.density = 0f;

        body.createFixture(fixtureDef);
        shape.dispose();

        camera = new OrthographicCamera();
        camera.setToOrtho(false, (w / h) * 320, 320);
        camera.update();
        Map map = new Map();
        
        renderer = new OrthogonalTiledMapRenderer(map.renderMap(world));

        debugRenderer = new Box2DDebugRenderer();
        camera = new OrthographicCamera(Gdx.graphics.getWidth() , Gdx.graphics.
                getHeight() );

    }

    @Override
    public void render() {
        
        elapsedTime += Gdx.graphics.getDeltaTime() / 7;
        Gdx.gl.glClearColor(0, 0, 0, 0);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        camera.update();
        
        renderer.setView(camera);
        renderer.render();
        
        Animation<TextureRegion> animation = null;
        Animation<TextureRegion> animationSword = meleeAnimationIdleFrame;
        float xMelee = 0;
        float yMelee = 0;

        world.step(1f / 60f, 6, 2);

        batch.setProjectionMatrix(camera.combined);
        debugMatrix = batch.getProjectionMatrix().cpy().scale(PIXELS_TO_METERS,
                PIXELS_TO_METERS, 0);

        batch.begin();

        player.setPosition((body.getPosition().x * PIXELS_TO_METERS) - player.
                getWidth() / 2,
                (body.getPosition().y * PIXELS_TO_METERS) - player.getHeight() / 2);
        if (idleAnimation.equals("Down")) {
            animation = idleAnimationDown;
            if (Gdx.input.isKeyPressed(Input.Keys.SPACE)) {
                xMelee = player.getX() - 5;
                yMelee = player.getY() - 10;
                animationSword = meleeAnimationDown;

            }
        } else if (idleAnimation.equals("Up")) {
            animation = idleAnimationUp;
            if (Gdx.input.isKeyPressed(Input.Keys.SPACE)) {
                xMelee = player.getX() + 5;
                yMelee = player.getY() + 10;
                animationSword = meleeAnimationUp;

            }
        } else if (idleAnimation.equals("Left")) {
            animation = idleAnimationLeft;
            if (Gdx.input.isKeyPressed(Input.Keys.SPACE)) {
                xMelee = player.getX() - 10;
                yMelee = player.getY() + 5;
                animationSword = meleeAnimationLeft;

            }
        } else if (idleAnimation.equals("Right")) {
            animation = idleAnimationRight;
            if (Gdx.input.isKeyPressed(Input.Keys.SPACE)) {
                xMelee = player.getX() + 10;
                yMelee = player.getY() - 5;
                animationSword = meleeAnimationRight;

            }
        }
        if (playerMovement().equals("Up")) {
            animation = runningAnimationUp;
            if (Gdx.input.isKeyPressed(Input.Keys.SPACE)) {
                xMelee = player.getX() + 5;
                yMelee = player.getY() + 10;
                animationSword = meleeAnimationUp;

            }
        } else if (playerMovement().equals("Down")) {
            animation = runningAnimationDown;
            if (Gdx.input.isKeyPressed(Input.Keys.SPACE)) {
                xMelee = player.getX() - 5;
                yMelee = player.getY() - 10;
                animationSword = meleeAnimationDown;

            }
        } else if (playerMovement().equals("Left")) {
            animation = runningAnimationLeft;
            if (Gdx.input.isKeyPressed(Input.Keys.SPACE)) {
                xMelee = player.getX() - 10;
                yMelee = player.getY() + 5;
                animationSword = meleeAnimationLeft;

            }
        } else if (playerMovement().equals("Right")) {
            animation = runningAnimationRight;
            if (Gdx.input.isKeyPressed(Input.Keys.SPACE)) {
                xMelee = player.getX() + 10;
                yMelee = player.getY() - 5;
                animationSword = meleeAnimationRight;

            }
        }
        camera.position.set(player.getX(), player.getY(), 0);
        camera.update();

        batch.draw(animation.getKeyFrame(elapsedTime, true), player.getX(), player.getY(), player.getOriginX(),
                player.getOriginY(),
                player.getWidth(), player.getHeight(), player.getScaleX(), player.
                getScaleY(), player.getRotation());
        batch.draw(animationSword.getKeyFrame(elapsedTime /2, true), xMelee, yMelee);
        batch.end();
        // remove debug to make hitboxes invicible
        debugRenderer.render(world, debugMatrix);
    }

    @Override
    public void dispose() {
        batch.dispose();
        atlas.dispose();
        world.dispose();

    }

    private String playerMovement() {
        if (Gdx.input.isKeyPressed(Input.Keys.SHIFT_LEFT)) {
            speed = 3f;
        } else {
            speed = 1f;
        }
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
