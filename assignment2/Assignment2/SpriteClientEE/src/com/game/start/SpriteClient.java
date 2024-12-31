package com.game.start;

import com.login.*;
import com.sprite.game.SpriteGame;


public class SpriteClient {
	
	public static void main(String[] args) {
		int counter=0;
		SpriteGame spriteStart=null;
		Credential  cred = new Credential();
		Login log = new Login(cred);
		while (!cred.isGoodToGo()) {
			counter++;
			if(counter%100 == 0) {
				System.out.println("Waiting for Authentication...");
			}
			counter++;
			counter= counter>=1000?0:counter;
		}
		spriteStart = new SpriteGame(cred);
		System.out.println("Authenticated\n\nLoading...");
		spriteStart.StartGame();
	}

}