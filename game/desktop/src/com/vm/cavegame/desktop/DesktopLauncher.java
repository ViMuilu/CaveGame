package com.vm.cavegame.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
//import com.badlogic.gdx.tools.texturepacker.TexturePacker;
import vm.cavegame.MainGame;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.title = "CaveGame";
                config.resizable = true;    
                config.height=360;
                config.width = 640;
                
                // Remove comments on the code below if texture atlas is corrupted or textures are not loading         
//                TexturePacker.Settings sets = new TexturePacker.Settings();
//                sets.pot = true;
//                sets.fast = true;
//                sets.combineSubdirectories = true;
//                sets.paddingX = 1;
//                sets.paddingY = 1;
//                TexturePacker.process("rawTextures", "./", "textures");

                new LwjglApplication(new MainGame(), config);
	}
}