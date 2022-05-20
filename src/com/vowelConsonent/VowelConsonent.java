package com.vowelConsonent;

public class VowelConsonent {
	char charecter;
	void func() {
		int ch = (int)charecter;
		if(ch =='a' |ch =='e' |ch =='i' |ch =='o' | ch =='u'|ch =='A' |ch =='E' |ch =='I' |ch =='O' | ch =='U') {
			System.out.println(charecter + " Character is vowel");
		}else {
			if((ch>='a'&ch<='z')|(ch>='A'& ch<='Z')){
				System.out.println(charecter+" Character is Consonent");
			}else {
				System.out.println(charecter+" Invalid Character");
			}
		}
	}
	public static void main(String[] args) {
		VowelConsonent d1 = new VowelConsonent();
		VowelConsonent d2 = new VowelConsonent();
		VowelConsonent d3 = new VowelConsonent();
		d1.charecter = 'a';
		d2.charecter = 'b';
		d3.charecter = '*';
		d1.func();
		d2.func();
		d3.func();
	}
}
