package edu.ilstu.cast.it.vo;

import java.util.ArrayList;

public class CoursesVO {
	private ArrayList<String> courseList =  new ArrayList<String>();
	
	public ArrayList<String> getCourseList() {
		return courseList;
	}
	public void setCourseList(ArrayList<String> courseList) {
		this.courseList = courseList;
	}
	
	public int findSelectedCourse(String courseName)
	{
		int index = 0;
		index = courseList.indexOf(courseName);

		return index;
	}
	
}
