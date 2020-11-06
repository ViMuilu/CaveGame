package com.vm.cavegame;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;

public class MainGame extends ApplicationAdapter {

    SpriteBatch batch;
    TextureAtlas atlas;
    private Sprite player;
    private float playerX;
    private float playerY;
    float playerSpeed = 10.0f;
    /**
     * Creates textures and gives them a position
     */
    @Override
    public void create() {
        batch = new SpriteBatch();
        atlas = new TextureAtlas("textures.atlas");
        player = new Sprite(atlas.findRegions("characters/MC/Character/CharacterDown").get(0));
        playerX = 0;
        playerY = 0;
    }

    /**
     * renders textures and atm checks for keys pressed
     */
    @Override
    public void render() {

        if (Gdx.input.isKeyPressed(Keys.DPAD_LEFT)) {
            playerX -= Gdx.graphics.getDeltaTime() * playerSpeed;
        }
        if (Gdx.input.isKeyPressed(Keys.DPAD_RIGHT)) {
            playerX += Gdx.graphics.getDeltaTime() * playerSpeed;
        }
        if (Gdx.input.isKeyPressed(Keys.DPAD_UP)) {
            playerY += Gdx.graphics.getDeltaTime() * playerSpeed;
        }
        if (Gdx.input.isKeyPressed(Keys.DPAD_DOWN)) {
            playerY -= Gdx.graphics.getDeltaTime() * playerSpeed;
        }

        Gdx.gl.glClearColor(1, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin();
        batch.draw(player, (int) playerX, (int) playerY);
        batch.end();
    }
    
    @Override
    public void dispose() {
        batch.dispose();
        atlas.dispose();
    }
}
