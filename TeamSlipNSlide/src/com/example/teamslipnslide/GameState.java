package com.example.teamslipnslide;

import android.app.Application;
import android.content.Context;
import android.content.Intent;

public class GameState extends Application {

	private final int default_score = 0;
	private final int default_fireSpeedRating = 0;
	private final int default_fireSizeRating = 0;
	private final int default_health = 100;
	private final int default_level = 1;
	private final int default_missileSpeed = 4;
	private final float default_projectileSize = 2.5f;
	
	
	private static int score = 0;
	public static int fireSpeedRating = 0;
	public static int fireSizeRating = 0;
	private final int price1 = 100;
	private final int price2 = 200;
	private final int price3 = 400;
	private final int lifePrice = 500;
	
	private static int health = 100;
	private static int level = 1;
	private static int missileSpeed = 4; //Less = faster
	private static float projectileSize = 2.0f; //Less = bigger
	
	public void hardReset()
	{
		score = default_score;
		fireSpeedRating = default_fireSpeedRating;
		fireSizeRating = default_fireSizeRating;
		health = default_health;
		level = default_level;
		missileSpeed = default_missileSpeed;
		projectileSize = default_projectileSize;
	}
	
	public void setProjectileSize()
	{
		projectileSize -= 0.5f;
	}
	
	public float getProjectileSize()
	{
		return projectileSize;
	}
	
	public void setMissileSpeed()
	{
		missileSpeed -= 1;
	}
	
	public int getMissileSpeed()
	{
		return missileSpeed;
	}
	
	public int getLifePrice()
	{
		return lifePrice;
	}
	
	public void incLevel()
	{
		level++;
	}
	
	public int getLevel()
	{
		return level;
	}
	
	public int getHealth(){
		return health;
	}
	
	public void setHealth(int newHealth)
	{
		health = newHealth;
	}
	
    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }
    
    public int getPrice(int state)
    {
    	if(state == 0)
    		return price1;
    	else if(state == 1)
    		return price2;
    	else if(state == 2)
    		return price3;

    	
    	return 0;
    }
    
}
