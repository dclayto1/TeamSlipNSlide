package com.example.teamslipnslide;

import java.util.Random;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.TextView;
import android.text.format.Time;
import android.view.View;
import android.content.Intent;
import android.graphics.Color;


public class UpgradeActivity extends Activity {
	
	RatingBar fireSpeedBar;
	RatingBar fireSizeBar;
	TextView totalInt;
	TextView fireSpeedPrice;
	TextView lifePrice;
	TextView fireSizePrice;
	Button fireSpeedButton;
	Button lifeBtn;
	Button fireSizeButton;
	
	GameState app;

	
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		//Random BG color
        Time t = new Time();
	    t.setToNow();		
		Random rnd = new Random(t.toMillis(false)); 
		//Create a new random color, dark. Also take into account the transparent black on the image itself. 
		int color = Color.argb(255, rnd.nextInt(155)+100, rnd.nextInt(155)+100, rnd.nextInt(155)+100);   
		//Get the actual activity view. 
		View view = this.getWindow().getDecorView();
	    view.setBackgroundColor(color);
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.upgrade);
		totalInt = (TextView)findViewById(R.id.totalInt);
		
		fireSpeedBar = (RatingBar) findViewById(R.id.fireSpeedStars1);
		fireSpeedBar.setRating(app.fireSpeedRating);
		fireSpeedPrice = (TextView)findViewById(R.id.fireSpeedPrice);
		fireSpeedButton = (Button)findViewById(R.id.fireSpeedBtn);
		
		lifePrice = (TextView)findViewById(R.id.lifePrice);
		lifeBtn = (Button)findViewById(R.id.lifeBtn);

		fireSizeBar = (RatingBar) findViewById(R.id.fireSizeStars1);
		fireSizeBar.setRating(app.fireSizeRating);
		fireSizePrice = (TextView)findViewById(R.id.fireSizePrice);
		fireSizeButton = (Button)findViewById(R.id.fireSizeBtn);

		app = ((GameState)getApplicationContext());

		
		String totalString = "" + app.getScore();
		totalInt.setText(totalString);
		
		String totalFireRate = "" + app.getPrice((int)fireSpeedBar.getRating());
		fireSpeedPrice.setText(totalFireRate);
		
		String lifePriceStr = "" + app.getLifePrice();
		lifePrice.setText(lifePriceStr);
		
		String totalFireSize = "" + app.getPrice((int)fireSizeBar.getRating());
		fireSizePrice.setText(totalFireSize);
		
		if(app.getPrice((int)fireSpeedBar.getRating()) > app.getScore())
			fireSpeedButton.setEnabled(false);
		else
			fireSpeedButton.setEnabled(true);
		
		if(app.getLifePrice() > app.getScore())
			lifeBtn.setEnabled(false);
		else
			lifeBtn.setEnabled(true);
		
		if(app.getPrice((int)fireSizeBar.getRating()) > app.getScore())
			fireSizeButton.setEnabled(false);
		else
			fireSizeButton.setEnabled(true);
		
	}
	
	public void onClickFireSpeed(View view)
	{	
		fireSpeedBar = (RatingBar) findViewById(R.id.fireSpeedStars1);
		if(fireSpeedBar.getRating() < fireSpeedBar.getNumStars())
		{
			fireSpeedBar.setRating(fireSpeedBar.getRating() + 1);
			app.fireSpeedRating = (int)fireSpeedBar.getRating();
			app.setMissileSpeed();
			app.setScore(app.getScore() - app.getPrice((int)fireSpeedBar.getRating()-1));
			fireSpeedPrice.setText("" + app.getPrice((int)fireSpeedBar.getRating()));
			totalInt.setText("" + app.getScore());

			
		}
			
		if(fireSpeedBar.getRating() == fireSpeedBar.getNumStars() || app.getPrice((int)fireSpeedBar.getRating()) > app.getScore())
			view.setEnabled(false);
		
		
	}
	
	public void onClickBuyHealth(View view)
	{
		app.setHealth(100);
		app.setScore(app.getScore() - app.getLifePrice());
		totalInt.setText("" + app.getScore());
		if(app.getLifePrice() > app.getScore())
			view.setEnabled(false);
		
		
	}
	

	public void onClickFireSize(View view)
	{
		fireSizeBar = (RatingBar) findViewById(R.id.fireSizeStars1);
		if(fireSizeBar.getRating() < fireSizeBar.getNumStars())
		{
			fireSizeBar.setRating(fireSizeBar.getRating() + 1);
			app.fireSizeRating = (int)fireSizeBar.getRating();
			app.setProjectileSize();
			app.setScore(app.getScore() - app.getPrice((int)fireSizeBar.getRating()-1));
			fireSizePrice.setText("" + app.getPrice((int)fireSizeBar.getRating()));
			totalInt.setText("" + app.getScore());

			

		}
			
		if(fireSizeBar.getRating() == fireSizeBar.getNumStars() || app.getPrice((int)fireSizeBar.getRating()) > app.getScore())
			view.setEnabled(false);
	}
	
	public void onClickQuit(View view)
	{
		Intent intent = new Intent(this, MainActivity.class);
		startActivity(intent);
	}
	
	public void onClickReady(View view)
	{
		Intent intent = new Intent(this, GameActivity2.class);
		startActivity(intent);
		
	}
	
	
}
