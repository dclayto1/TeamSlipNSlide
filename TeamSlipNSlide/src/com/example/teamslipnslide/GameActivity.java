//Action while button is pressed example : 
//http://stackoverflow.com/questions/10511423/android-repeat-action-on-pressing-and-holding-a-button

package com.example.teamslipnslide;

import java.util.Random;

import android.os.Bundle;
import android.os.Handler;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.text.format.Time;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.content.Context;

public class GameActivity extends Activity implements OnTouchListener, AnimationListener
{
	//Objects on screen
	Button ArrowLeft;
	Button ArrowRight;
	ImageView barrel;
	
	//Cannon Rotation
	int degree = 0;    //Initial state of cannon, 0 = straight up
	int rotation = 1;  //How many degrees per 1 tick held down
	int rotateDelay = 10;  //ticks between each rotation. 
	private Handler mHandler;
	int RotateDirection = 0;  //0 = left, 1 = right
	
	//Move to different activities. 
	final Context context = this;

	//Animations
	Animation movement1;
	Animation movement2;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
	   // screen = new gameScreen(this);
	    
		setContentView(R.layout.activity_game);
		
		Time t = new Time();
	    t.setToNow();		
		Random rnd = new Random(t.toMillis(false)); 
		//Create a new random color, dark. Also take into account the transparent black on the image itself. 
		int color = Color.argb(255, rnd.nextInt(155)+100, rnd.nextInt(155)+100, rnd.nextInt(155)+100);   
		//Get the actual activity view. 
		View view = this.getWindow().getDecorView();
	    view.setBackgroundColor(color);
	    
	    ArrowRight = (Button) findViewById(R.id.ArrowRight);
	    ArrowRight.setOnTouchListener(this);
	    ArrowLeft = (Button) findViewById(R.id.ArrowLeft);
	    ArrowLeft.setOnTouchListener(this);
	    
	    
	    //Animation movement -Dylan
	    
	    ImageView[] enemies = new ImageView[10];
	    for(int i=0; i<enemies.length; i++)
	    {
	    	/*if(i % 5 == 0)
	    		enemies[i] = (ImageView)findViewById(R.id.enemySpawn5);
	    	else if(i % 4 == 0)
	    		enemies[i] = (ImageView)findViewById(R.id.enemySpawn4);
	    	else if(i % 3 == 0)
	    		enemies[i] = (ImageView)findViewById(R.id.enemySpawn3);
	    	else */if(i % 2 == 0)
	    		enemies[i] = (ImageView)findViewById(R.id.enemySpawn2);
	    	else
	    		enemies[i] = (ImageView)findViewById(R.id.enemySpawn1);
	    }
	    
	    movement1 = AnimationUtils.loadAnimation(this, R.layout.animation1);
        movement1.reset();
        movement1.setAnimationListener(this);
        
        movement2 = AnimationUtils.loadAnimation(this, R.layout.animation2);
        movement2.reset();
        movement2.setAnimationListener(this);
        
	    
        enemies[0].setImageResource(R.drawable.cannon);
        enemies[0].startAnimation(movement1);
        enemies[1].setImageResource(R.drawable.arrowleft);
        enemies[1].startAnimation(movement2);
        
        //ImageView enemy = (ImageView) findViewById(R.id.enemy);
        //enemy.setImageResource(R.drawable.cannon);
        //enemy.startAnimation(movement1);
        
	    
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) 
	{
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.game, menu);
		return true;
	}
	
	public void onClickPause(View v)
	{
		Intent intent = new Intent(context, MainActivity.class);
	    startActivity(intent);
	}
	
	
	public void addListenerOnButton() 
	{
		//Do Something. 
	}

	@Override public boolean onTouch(View v, MotionEvent event) 
	{
        //What button is actually being held down. 
		if (v.getId() == R.id.ArrowLeft)
        	RotateDirection = 0;
        if (v.getId() == R.id.ArrowRight)
        	RotateDirection = 1;
        	
		switch(event.getAction()) 
        {
        case MotionEvent.ACTION_DOWN:
            if (mHandler != null) 
            	return true;
            mHandler = new Handler();
            mHandler.postDelayed(mAction, rotateDelay);
            break;
        case MotionEvent.ACTION_UP:
            if (mHandler == null) 
            	return true;
            mHandler.removeCallbacks(mAction);
            mHandler = null;
            break;
        }
        return false;
    }
	
	
	Runnable mAction = new Runnable() 
	{
        @Override public void run() 
        {
        	barrel = (ImageView) findViewById(R.id.barrel);
    		barrel.setPivotY(barrel.getHeight()/1);
    		barrel.setPivotX(barrel.getWidth()/2);
    		
    		if (RotateDirection == 0)
    		{
	        	if (degree > -90)
	        	{
	        		barrel.setRotation(barrel.getRotation() - (float)rotation);		
	        		degree -= rotation;
	        	}
    		}
    		if (RotateDirection == 1)
    		{
	        	if (degree <= 90)
	        	{
	        		barrel.setRotation(barrel.getRotation() + (float)rotation);		
	        		degree += rotation;
	        	}
    		}
    		
            mHandler.postDelayed(this, rotateDelay);
        }
    };


	@Override
	public void onAnimationEnd(Animation animation) {
		// TODO Auto-generated method stub
		animation.reset();
		animation.start();
	}

	@Override
	public void onAnimationRepeat(Animation animation) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onAnimationStart(Animation animation) {
		// TODO Auto-generated method stub
		
	}
}
