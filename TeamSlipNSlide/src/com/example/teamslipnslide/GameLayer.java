package com.example.teamslipnslide;

import java.util.ArrayList;
import java.util.Random;

import org.cocos2d.actions.instant.CCCallFuncN;
import org.cocos2d.actions.interval.CCMoveTo;
import org.cocos2d.actions.interval.CCSequence;
import org.cocos2d.layers.CCLayer;
import org.cocos2d.layers.CCScene;
import org.cocos2d.nodes.CCDirector;
import org.cocos2d.nodes.CCLabel;
import org.cocos2d.nodes.CCSprite;
import org.cocos2d.types.CGPoint;
import org.cocos2d.types.CGRect;
import org.cocos2d.types.CGSize;
import org.cocos2d.types.ccColor3B;

import android.content.Intent;
import android.os.Handler;
import android.view.MotionEvent;

public class GameLayer extends CCLayer 
{
	static CCScene scene;
	static CCLayer layer;
	
	//Sprites 
	CGSize winSize;
	CCSprite leftArrow;
	CCSprite rightArrow;
	CCSprite barrel;
	CCSprite player;
	CCSprite target;
	
	//Cannon Rotation
	private Handler mHandler;
	int RotateDirection = 0; 
	int rotation = 1;  //How many degrees per 1 tick held down
	int rotateDelay = 10;
	int degree = 90;    //Initial state of cannon, 90 = straight up
	
	//Game Variables : 
	//----------------
	// Speed of enemies : 
	int minDuration = 3;
	int maxDuration = 7;
	// How often enemies spawn : 
	float spawnRate = 8;
	// Number of Enemies Spawned thus far : 
	int spawns = 0;
	// Number of Enemies that got to end of their path : 
	int enemiesFinished = 0;
	// missile speed -> less means faster
	int missileSpeed = app.getMissileSpeed();
	// projectile size -> less means bigger
	float projectileSize = app.getProjectileSize();
	// Number of enemies in this round. 
	int numEnemies = app.getLevel() + 4;
	
	int finalLevel = 5;
	
	//Game Labels : 
	CCLabel labelScore;
	CCLabel labelHealth;
	int pointsPerKill = 50;
	int enemyDamage = 30; 
	
	
	//Arraylist for collision
	protected ArrayList<CCSprite> _targets;
	protected ArrayList<CCSprite> _projectiles;
	
	
	static GameState app;
	
	
	public static CCScene scene(GameState appState)
	{
		scene = CCScene.node();
		app = appState;
		layer = new GameLayer(app);
		
		scene.addChild(layer);
		
		return scene;
	}
	
	protected GameLayer(GameState app)
	{
		super();
		this.setIsTouchEnabled(true);
		winSize = CCDirector.sharedDirector().displaySize();
		CCSprite background = CCSprite.sprite("background1.png");
		player = CCSprite.sprite("cannon.png");
		barrel = CCSprite.sprite("cannonbarrel.png");
		leftArrow = CCSprite.sprite("arrowleft.png");
		rightArrow = CCSprite.sprite("arrowright.png");
		
		//Show Scores : 
		String ScoreLabel = "Score : " + app.getScore();
		labelScore = CCLabel.makeLabel(ScoreLabel, "Arial", (float)20.0);
		labelScore.setScaleX(winSize.width / labelScore.getContentSize().width / 8.0f);
		labelScore.setScaleY(winSize.height / labelScore.getContentSize().height / 8.0f);
		labelScore.setPosition(CGPoint.ccp(labelScore.getScaleX()*labelScore.getContentSize().width / 1.5f, winSize.getHeight()- (labelScore.getScaleY()*labelScore.getContentSize().height / 2.0f)));
		labelScore.setColor(ccColor3B.ccBLACK);
		//Show Health
		String healthLabel = "Health : " + app.getHealth();
		labelHealth = CCLabel.makeLabel(healthLabel, "Arial", (float)20.0);
		labelHealth.setScaleX(winSize.width / labelHealth.getContentSize().width / 8.0f);
		labelHealth.setScaleY(winSize.height / labelHealth.getContentSize().height / 8.0f);
		//labelHealth.setPosition(CGPoint.ccp(labelHealth.getScaleX()*labelHealth.getContentSize().width / 2.0f, winSize.getHeight() - labelScore.getPosition().y));
		labelHealth.setPosition(CGPoint.ccp(labelHealth.getScaleX() * labelHealth.getContentSize().width / 2.0f, winSize.getHeight() - labelHealth.getScaleY()*labelHealth.getContentSize().height - (winSize.getHeight() - labelScore.getPosition().y)));
		labelHealth.setColor(ccColor3B.ccBLACK);
		
		
		//Scale images to appropriate screen size
		background.setScaleX(winSize.width / background.getContentSize().width);
		background.setScaleY(winSize.height / background.getContentSize().height);
		player.setScaleX((winSize.width / 6.0f) / player.getContentSize().width);
		player.setScaleY((winSize.height / 6.0f) / player.getContentSize().height);
		barrel.setScaleX(player.getScaleX());
		barrel.setScaleY(player.getScaleY());
		leftArrow.setScaleX(winSize.width / 5.0f / leftArrow.getContentSize().width);
		leftArrow.setScaleY(winSize.height / 5.0f / leftArrow.getContentSize().height);
		rightArrow.setScaleX(winSize.width / 5.0f / rightArrow.getContentSize().width);
		rightArrow.setScaleY(winSize.height / 5.0f / rightArrow.getContentSize().height);
		
		
		//Initialize sprite positions
		background.setPosition(CGPoint.ccp(winSize.width / 2.0f, winSize.height / 2.0f));
		player.setPosition(CGPoint.ccp(winSize.width / 2.0f, (player.getScaleY() * player.getContentSize().height / 2.0f)));
		barrel.setPosition(CGPoint.ccp(winSize.width / 2.0f, (barrel.getContentSize().height / 4.0f)));
		barrel.setAnchorPoint(CGPoint.ccp(.5f, 0));
		leftArrow.setPosition(CGPoint.ccp(leftArrow.getScaleX() * leftArrow.getContentSize().width / 2.0f, leftArrow.getScaleY() * leftArrow.getContentSize().height / 2.0f));
		rightArrow.setPosition(CGPoint.ccp(winSize.width - rightArrow.getScaleX() * rightArrow.getContentSize().width / 2.0f, rightArrow.getScaleY() * rightArrow.getContentSize().height / 2.0f));
		
		//Add items to game
		addChild(background);
		addChild(labelScore);
		addChild(labelHealth);
		addChild(barrel);
		addChild(player);
		addChild(leftArrow);
		addChild(rightArrow);
		
		//Collisions 
		_targets = new ArrayList<CCSprite>();
		_projectiles = new ArrayList<CCSprite>();
		
		this.schedule("gameLogic", 1.0f);
		this.schedule("EnemySpawns", spawnRate);
		this.schedule("update");

	}
	
	
	protected void addTarget()
	{
		Random rand = new Random();
		target = CCSprite.sprite("enemy2.png");
		target.setScaleX(winSize.width / 10.0f / target.getContentSize().width);
		target.setScaleY(winSize.height / 10.0f / target.getContentSize().height);
		
		//Random the Y position for spawning enemy targets
		//int minY = (int) (target.getScaleY() * target.getContentSize().height / 3.5f);
		int minY = (int) (winSize.height / 2.0f); 
		int maxY = (int) (winSize.height - target.getScaleY() * target.getContentSize().height / 2.0f);
		int rangeY = maxY - minY;
		int actualY = rand.nextInt(rangeY) + minY;
		
		//Determine speed of target
		int rangeDuration = maxDuration-minDuration;
		int actualDuration = rand.nextInt(rangeDuration) + minDuration;
		
		//Randomly decide if it spawns on the left or right...
		int randomSide = rand.nextInt(2);
		
		CCSequence actions;
		CCMoveTo actionMove;
		if (randomSide == 0)  //Spawn on left
		{
			//Create the target off the left side of the screen
			target.setPosition(CGPoint.ccp(target.getScaleX() * target.getContentSize().width, actualY));
			addChild(target);
			
			//Create the actions
			actionMove = CCMoveTo.action(actualDuration,  CGPoint.ccp(winSize.width + target.getScaleX() * target.getContentSize().width, actualY)); 
			//+ (target.getContentSize().width / 2.0f), actualY));
		}
		else  //Spawn on Right
		{
			//Flip image 
			target.setFlipX(true);
			
			//Create the target off the right side of the screen
			target.setPosition(CGPoint.ccp(winSize.width - target.getScaleX() * target.getContentSize().width, actualY));
			addChild(target);
			
			//Create the actions
			actionMove = CCMoveTo.action(actualDuration,  CGPoint.ccp(-target.getScaleX() * target.getContentSize().width, actualY)); 
			
		}
		
		//Add to arraylist for collisions
		target.setTag(1);
		_targets.add(target);
		
		//call the actual actions
		CCCallFuncN actionMoveDone = CCCallFuncN.action(this, "spriteMoveFinished");
		actions = CCSequence.actions(actionMove,  actionMoveDone);
		target.runAction(actions);
	}
	
	
	//Spawn a new taget every XX seconds
	public void EnemySpawns(float dt)
	{
		spawns++;
		
		//If enough have spawned to end round., go to upgrades page. 
		if (spawns >= numEnemies)
		{
			
			if (app.getLevel() == finalLevel) //At final Level
			{
				Intent intent = new Intent(CCDirector.sharedDirector().getActivity(), CreditsActivity.class);
				CCDirector.sharedDirector().getActivity().startActivity(intent);
			}
			else //Not at final Level
			{
				Intent intent = new Intent(CCDirector.sharedDirector().getActivity(), UpgradeActivity.class);
				CCDirector.sharedDirector().getActivity().startActivity(intent);
			}
			app.incLevel();
		}
		else
		{
			addTarget();

		}
	}
	
	//Cleans up sprite when done animating
	public void spriteMoveFinished(Object sender)
	{
		//inc counter 
		enemiesFinished++;
				
		CCSprite sprite = (CCSprite)sender;
				
		if (sprite.getTag() == 1)
		{
			_targets.remove(sprite);
		}
		else if (sprite.getTag() == 2)
		{
			_projectiles.remove(sprite);
		}
		
		//Decrease Health : 
		app.setHealth(app.getHealth() - enemyDamage);
		String healthLabel = "Health : " + app.getHealth();
		labelHealth.setString(healthLabel);
				
		if(app.getHealth() <= 0)
		{
			Intent intent = new Intent(CCDirector.sharedDirector().getActivity(), MainActivity.class);
			CCDirector.sharedDirector().getActivity().startActivity(intent);
		}
				
		this.removeChild(sprite, true);
	}
	
	public void bulletMoveFinished(Object sender)
	{
		CCSprite sprite = (CCSprite)sender;
		
		if (sprite.getTag() == 1)
		{
			_targets.remove(sprite);
		}
		else if (sprite.getTag() == 2)
		{
			_projectiles.remove(sprite);
		}
		
		this.removeChild(sprite, true);
	}
	
	
	
	//Rotation Code 
	@Override
	public boolean ccTouchesBegan(MotionEvent event)
	{
		CGPoint location = CCDirector.sharedDirector().convertToGL(CGPoint.ccp(event.getX(), event.getY()));
		
		//Is location in the leftArrow sprite?
		if(CGRect.containsPoint(leftArrow.getBoundingBox(), CGPoint.ccp(location.x, location.y)) )
		{
			RotateDirection = 0;
			if (mHandler != null) 
		       	return true;
		    mHandler = new Handler();
		    mHandler.postDelayed(mAction, rotateDelay);
		}
		//Is location in the rightArrow sprite?
		else if(CGRect.containsPoint(rightArrow.getBoundingBox(), CGPoint.ccp(location.x, location.y)) )
		{
			RotateDirection = 1;
			if (mHandler != null) 
		       	return true;
		    mHandler = new Handler();
		    mHandler.postDelayed(mAction, rotateDelay);
		}
	    
		//Is the location the cannon (fire)
		else if(CGRect.containsPoint(player.getBoundingBox(), CGPoint.ccp(location.x, location.y)) )
		{
		    //If not at projectile cap 
			if (_projectiles.size() <= 5)
			{
				// Set up initial location of projectile
			    CGSize winSize = CCDirector.sharedDirector().displaySize();
			    CCSprite projectile = CCSprite.sprite("missile.png");
			    projectile.setScaleX(barrel.getScaleX() * barrel.getContentSize().width / projectile.getContentSize().width / (projectileSize));
			    projectile.setScaleY(barrel.getScaleY() * barrel.getContentSize().width / projectile.getContentSize().height / (projectileSize));
			    float projectileStartX = (float) ((barrel.getScaleY() * barrel.getContentSize().height) * Math.cos(CC_DEGREES_TO_RADIANS(degree)) + barrel.getPosition().x);
			    float projectileStartY = (float) ((barrel.getScaleY() * barrel.getContentSize().height) * Math.sin(CC_DEGREES_TO_RADIANS(degree)) + barrel.getPosition().y);
	
			    projectile.setPosition(CGPoint.ccp(projectileStartX, projectileStartY));
			    projectile.setTag(2);
			    _projectiles.add(projectile);
			    addChild(projectile); //Add to scene
		
			    // Determine where we wish to shoot the projectile to
			    float realX = (float) ((winSize.width * Math.cos(CC_DEGREES_TO_RADIANS(degree)) + (winSize.width / 2.0f)));
			    float realY = (float) ((winSize.width * Math.sin(CC_DEGREES_TO_RADIANS(degree)) + ((float)20)));
	
			    CGPoint realDest = CGPoint.ccp(realX, realY);
			    
			    // Move projectile to actual endpoint
			    projectile.runAction(CCSequence.actions(CCMoveTo.action(missileSpeed, realDest),CCCallFuncN.action(this, "bulletMoveFinished")));
			    
			    return true;
			}
		}
	    return false;
	}
	
	
	private double CC_DEGREES_TO_RADIANS(int degree2) 
	{
		return (degree2 * (Math.PI / 180));
	}

	public boolean ccTouchesEnded(MotionEvent event)
	{
		if(mHandler == null)
			return true;
		mHandler.removeCallbacks(mAction);
		mHandler = null;
		return false;
	}
	
	
	Runnable mAction = new Runnable() 
	{
	    @Override public void run() 
	    {
			if (RotateDirection == 0)
			{
	        	if (degree < 180)
	        	{
	        		barrel.setRotation(barrel.getRotation() - (float)rotation);		
	        		degree += rotation;
	        	}
			}
			if (RotateDirection == 1)
			{
	        	if (degree > 0)
	        	{
	        		barrel.setRotation(barrel.getRotation() + (float)rotation);		
	        		degree -= rotation;
	        	}
			}
			
	        mHandler.postDelayed(this, rotateDelay);
	    }
	};
	
	public void update(float dt)
	{
	    ArrayList<CCSprite> projectilesToDelete = new ArrayList<CCSprite>();
	 
	    for (CCSprite projectile : _projectiles)
	    {
	        ArrayList<CCSprite> targetsToDelete = new ArrayList<CCSprite>();
	 
	        for (CCSprite target : _targets)
	        {
	            CGRect targetRect = CGRect.make(target.getPosition().x - (target.getScaleX()*target.getContentSize().width / 2.0f),
	            								target.getPosition().y - (target.getScaleY()*target.getContentSize().height / 2.0f),
	            								target.getScaleX()*target.getContentSize().width, 
	            								target.getScaleY()*target.getContentSize().height);
	 
	            CGRect projectileRect = CGRect.make(projectile.getPosition().x - projectile.getScaleX()*projectile.getContentSize().width / 2.0f,
	            									projectile.getPosition().y - projectile.getScaleY()*projectile.getContentSize().height / 2.0f,
	            									projectile.getScaleX()*projectile.getContentSize().width,
	            									projectile.getScaleY()*projectile.getContentSize().height);
	            
	           // if (CGRect.containsPoint(targetRect, CGPoint.ccp(projectile.getPosition().x, projectile.getPosition().y)))
	           if(CGRect.containsRect(targetRect, projectileRect))
	            	targetsToDelete.add(target);
	        }
	 
	        for (CCSprite target : targetsToDelete)
	        {
	            _targets.remove(target);
	            removeChild(target, true);
	            
	          //Increase Score
	            app.setScore(app.getScore() + pointsPerKill);
	            String ScoreLabel = "Score : " + app.getScore();
	            labelScore.setString(ScoreLabel);
	            
	        }
	 
	        if (targetsToDelete.size() > 0)
	            projectilesToDelete.add(projectile);
	    }
	 
	    for (CCSprite projectile : projectilesToDelete)
	    {
	        _projectiles.remove(projectile);
	        removeChild(projectile, true);
	    }
	}
	
}
