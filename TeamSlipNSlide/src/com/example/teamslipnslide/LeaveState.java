package com.example.teamslipnslide;

import android.content.Intent;

public class LeaveState {

	private Intent goToMain;
	
	/*
	public void leaveGame(){
		//this.goToMain;
		startActivity(goToMain);
	}
	*/
	public void setLeave(Intent mp){
		
		this.goToMain=mp;
	}
	
}
