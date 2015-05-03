package edu.ilstu.cast.it.vo;

import java.util.HashMap;

/**
 * CoursePreference This class represents the preferences of a certain professor.  
 * This includes the course number and preference rank associated with that course.
 * A preference rank goes from 1-5 (1 is the most desirable, 5 is the least desirable).
 *
 * @author Group 4, Michael Whalen, Paul Schulz, Deana Qiao
 *
 */
public class CoursePreferenceVO //implements Comparable<CoursePreferenceVO>
{
	private String preferenceNum;
        private HashMap<String, Integer> courses;
	private String courseNum; 

	/**
	 * Constructor that takes in the preference rank and the course associated with a professor.
	 * @param prefNum - the preference rank of the course
	 * @param course - the course number
	 */
	public CoursePreferenceVO()
	{
            courses=new HashMap<String, Integer>();
        }
        
            /**
     * add the class preference with an number
     * that has to be 1 to 5.
     * @param className
     * @param num 
     */
    public void addChoice(String className, int num)
    {
        if(num<=5 && num>0)
        {
            courses.put(className, num);
        }
    }
    
        /**
     * @return the choice
     */
    public int getChoice(String className) {
        if(getCourses().containsKey(className))
        {
            return  getCourses().get(className);
        }else
        {
            return -1;
        }
    }
    




	/**
	 * compareTo(CoursePreference preference) method that uses the comparable interface in
	 * order to sort CoursePreference objects by their alphanumeric order. This orders
	 * CoursePreference objects in ascending order by their preference rank.
	 * 
	 * @return status the integer that tells whether a section comes before or
	 *         after another.
	 *//*
	public int compareTo(CoursePreferenceVO preference)
	{
		int status = 0;
		if (this.preferenceNum.compareTo(preference.getPreferenceNum()) < 0)
		{
			status = -1;
		}
		else if (this.courseNum.compareTo(preference.getPreferenceNum()) == 0)
		{
			status = 0;

		}

		else
		{
			status = 1;
		}

		return status;
	}*/

	/**
	 * toString() method that returns the preference rank and the course number for a given CoursePreference object.
	 * 
	 * 
	 * @return preference number and course number 
	 */
	public String toString()
	{
		return preferenceNum + " \t\t" + courseNum + "\n";
	}

    /**
     * @return the courses
     */
    public HashMap<String, Integer> getCourses() {
        return courses;
    }

    /**
     * @param courses the courses to set
     */
    public void setCourses(HashMap<String, Integer> courses) {
        this.courses = courses;
    }
}
