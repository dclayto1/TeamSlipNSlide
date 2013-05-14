package com.example.teamslipnslide;

import java.util.Random;

import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.text.format.Time;
import android.view.Menu;
import android.view.View;

public class PreGameMenu extends Activity 
{
	GameState app;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) 
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pre_game_menu);
        
        app = ((GameState) getApplicationContext());
        app.hardReset();
        
        //Random BG color
        Time t = new Time();
	    t.setToNow();		
		Random rnd = new Random(t.toMillis(false)); 
		//Create a new random color, dark. Also take into account the transparent black on the image itself. 
		int color = Color.argb(255, rnd.nextInt(155)+100, rnd.nextInt(155)+100, rnd.nextInt(155)+100);   
		//Get the actual activity view. 
		View view = this.getWindow().getDecorView();
	    view.setBackgroundColor(color);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
    
    public void onClickContinue(View view)
    {
    	Intent intent = new Intent(this, UpgradeActivity.class);
    	startActivity(intent);
    }
    
    public void onClickNew(View view)
    {
    	Intent intent = new Intent(this, UpgradeActivity.class);
    	startActivity(intent);
    	
    }
    
    public void onClickReturn(View view) 
    {
		 Intent intent = new Intent(this, MainActivity.class);
		 startActivity(intent);

    }
    
    public void returnMenu(View view) {
		 Intent intent = new Intent(this, PreGameMenu.class);
		 startActivity(intent);
		    //Intent intent = new Intent(this, DisplayMessageActivity.class);
		   // EditText editText = (EditText) findViewById(R.id.edit_message);
		   // String message = editText.getText().toString();
		   // intent.putExtra(EXTRA_MESSAGE, message);
		  //  startActivity(intent);
		}
    /*
    public void sendMessage(View view) {
	    Intent intent = new Intent(this, DisplayMessageActivity.class);
	    EditText editText = (EditText) findViewById(R.id.edit_message);
	    String message = editText.getText().toString();
	    intent.putExtra(EXTRA_MESSAGE, message);
	    startActivity(intent);
	}
	*/   
}




