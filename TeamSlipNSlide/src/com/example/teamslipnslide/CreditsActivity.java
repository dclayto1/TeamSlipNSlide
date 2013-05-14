package com.example.teamslipnslide;

import java.util.Random;

import android.app.Activity;
import android.os.Bundle;
import android.text.format.Time;
import android.view.View;
import android.content.Intent;
import android.graphics.Color;

public class CreditsActivity extends Activity {

	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.credits);
        
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
   
	public void onClickReturn(View v)
	{
		Intent intent = new Intent(this, MainActivity.class);
		startActivity(intent);
	}
	
	
}
