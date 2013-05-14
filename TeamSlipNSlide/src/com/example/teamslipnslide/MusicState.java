package com.example.teamslipnslide;

import android.app.Activity;
import android.media.MediaPlayer;

public class MusicState extends Activity{
private MediaPlayer mp;
private int counter =0;
	


	public void stopLoop(){
		mp.setLooping(false);
		
	}
	
	public void startPlay(){
		mp = MediaPlayer.create(this, R.raw.cutsound);
		mp.start();
		mp.setLooping(true);
		counter++;
	}
	public void pause(){
		mp.pause();
		mp.setVolume(0, 0);
		//stopLoop();
	}
	public void resume(){
		//mp = MediaPlayer.create(this, R.raw.cutsound);
		mp.start();
		mp.setVolume(1, 1);
		//mp.setLooping(true);
		
	}
	public void addCounter(){
		counter++;
	}
	public MediaPlayer getMedia(){
		return this.mp;
	}
	public void setMedia(MediaPlayer mp){
		this.mp = mp;
	}
	public int verify(){
		
		if(counter ==0){
			return 0;
		}else{
			return 1;
		}
	}
}
