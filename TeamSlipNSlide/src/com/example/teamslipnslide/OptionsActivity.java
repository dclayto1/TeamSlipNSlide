package com.example.teamslipnslide;

import java.util.Random;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.format.Time;
import android.view.View;
import android.widget.RadioButton;

public class OptionsActivity extends Activity {
	 MusicState music = new MusicState();
	 private RadioButton offbtnDisplay;
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.options);
        
       //addListenerOnButton();
        
        /*
       mp = MediaPlayer.create(OptionsActivity.this, R.raw.sound);
      
        mp.start();
        if (mp.getCurrentPosition() == mp.getDuration()/4){
        	mp.stop();
        	mp.reset();
        }
        */
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

	public void onSet(View view)
	{	
		//	RadioButton Rad = new RadioButton();
		 boolean checked = ((RadioButton) view).isChecked();
		 offbtnDisplay =(RadioButton) findViewById(R.id.offRadBtn);
		    // Check which radio button was clicked
		    switch(view.getId()) {
		        case R.id.offRadBtn:
		            if (checked)
		              // music.getMedia().pause();
		            	MainActivity.musicstate.pause();
		            break;
		        case R.id.onRadBtn:
		            if (checked)
		            	MainActivity.musicstate.resume();
		            break;
		    }
	
		//Intent playIntent = new Intent(this, MainActivity.class);
		//MainActivity.mp.pause(); <-- This was uncommented but throwing errors. 
	}
	/*
	public void addListenerOnButton(){
		
		//Intent intent = new Intent(this,MainActivity.class);
		radioSoundGroup = (RadioGroup) findViewById(R.id.radioSet);
		RadioButton cbutf = (RadioButton) findViewById(R.id.offRadBtn);
	  
		RadioButton cbutt = (RadioButton) findViewById(R.id.onRadBtn);
	        // get selected radio button from radioGroup
		if(cbutf.isChecked()){
			MainActivity.mp.setVolume(0, 0);
    		MainActivity.mp.pause();
    		cbutf.setChecked(true);
    		cbutt.setChecked(false);
		}
		if(cbutt.isChecked()){
			MainActivity.mp.setVolume(0, 0);
    		MainActivity.mp.pause();
    		cbutt.setChecked(true);
    		cbutf.setChecked(false);
		}

		}
*/
	}

