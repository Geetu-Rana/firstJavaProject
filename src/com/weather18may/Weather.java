package com.weather18may;

public class Weather {

	
	public static void main(String[] args) {

	//Assume these can have any value:
	boolean isSnowing = false;
	boolean isRaining = true;
	double temperature = 60.0;
	//print "Let�s stay home." if its raining, snowing or
	//below 50 degrees and print "Let�s go out!" otherwise.
	if(isRaining == true | isSnowing == true | temperature<50.0) {
		System.out.println("Lets stay home");
	}else {
		System.out.println("Let's go out");
	}

}
}
