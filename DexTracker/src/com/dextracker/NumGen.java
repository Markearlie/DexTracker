package com.dextracker;

import java.util.ArrayList;
import java.util.Random;

public class NumGen {
	
	public ArrayList<Integer> getNumberArray(int i){
		//Static number of integers for now
		return genRandomNums(i);
	}
	
	private ArrayList<Integer> genRandomNums(int amount){
		ArrayList<Integer> numArray = new ArrayList<Integer>();
		Random rand = new Random();
		for(int i = 0; i<amount; i++)
		{
			//Random numbers between 1 and 12
			numArray.add(rand.nextInt(11)+1);
		}
		return numArray;	
	}

}
