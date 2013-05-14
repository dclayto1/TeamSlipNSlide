package com.example.teamslipnslide;


import org.cocos2d.layers.CCScene;
import org.cocos2d.nodes.CCDirector;
import org.cocos2d.opengl.CCGLSurfaceView;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

public class GameActivity2 extends Activity 
{
	protected CCGLSurfaceView _glSurfaceView;
	
	public static MusicState gamesound = new MusicState();
	static MediaPlayer game; 
	GameState app;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		
		
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON, WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
		
		app = ((GameState)getApplicationContext());
		
		_glSurfaceView = new CCGLSurfaceView(this);
		setContentView(_glSurfaceView);
			
		game = MediaPlayer.create(this, R.raw.explosion);
		gamesound.setMedia(game);


		
	}


	@Override
	public void onStart()
	{
		super.onStart();
		
		CCDirector.sharedDirector().attachInView(_glSurfaceView);
		CCDirector.sharedDirector().setAnimationInterval(1.0f / 60.0f);
	
		CCScene scene = GameLayer.scene(app);
		CCDirector.sharedDirector().runWithScene(scene);
		
	}

	@Override
	public void onPause()
	{
		super.onPause();
		CCDirector.sharedDirector().pause();
	}
	
	@Override
	public void onResume()
	{
		super.onResume();
		CCDirector.sharedDirector().resume();
	}
	
	@Override
	public void onStop()
	{
		super.onStop();
		CCDirector.sharedDirector().end();
	}
		
	
	
	
	
	
	
}

