package com.studentDetail;

public class Student {
	int rollNo;
	String name;
	int marks;
	
	void displayStudentDetails() {
		
		System.out.println("Roll No: "+ rollNo);
		System.out.println("Name is: "+ name);
		System.out.println("Marks is: "+ marks);
	};
	public static void main(String[] args) {
		Student d1 = new Student();
		d1.rollNo = 9;
		d1.name = "Geeta Prakash";
		d1.marks = 10;
		Student d2 = new Student();
		d2.rollNo = 2000;
		d2.name = "Pankaj Rana";
		d2.marks = 500;
		
		d1.displayStudentDetails();
		d2.displayStudentDetails();
		
	}
	
	

}
