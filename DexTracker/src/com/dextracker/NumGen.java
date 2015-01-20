package com.dextracker;

import java.util.ArrayList;
import java.util.Random;

public class NumGen {
	
	public ArrayList<Integer> getNumberArray(int size){
		//Static number of integers for now
		return genRandomNums(size);
	}
	
	private ArrayList<Integer> genRandomNums(int amount){
		ArrayList<Integer> numArray = new ArrayList<Integer>();
		Random rand = new Random();
		for(int i = 0; i<amount; i++)
		{
			//Random numbers between 1 and 12
			numArray.add(rand.nextInt(12)+1);
		}
		return numArray;	
	}
	
	
	public int[] getRandomUniqueNumbers(int size)
	{
		int[] array = {1,2,3,4,5,6,7,8,9,10,11,12};
		
		return shuffleArray(array);
	}
	
	
	//Fisher-Yates Shuffle... Credit:"http://stackoverflow.com/questions/1519736/random-shuffling-of-an-array"
	private int[] shuffleArray(int[] array) {
		Random rand = new Random();
	    for (int i = array.length - 1; i > 0; i--)
	    {
	      int index = rand.nextInt(i + 1);
	      int a = array[index];
	      array[index] = array[i];
	      array[i] = a;
	    }
		return array;
	}

	public Integer getRandomNum(){
		
		Random rand = new Random();
		
			//Random numbers between 1 and 12
			return rand.nextInt(12)+1;
			
	}
	
	//Random Num of any limit
	public Integer getRandomTypeNum(int limit){
		Random rand = new Random();
		
		//Random numbers between 1 and 12
		return rand.nextInt(limit);
	}

}
