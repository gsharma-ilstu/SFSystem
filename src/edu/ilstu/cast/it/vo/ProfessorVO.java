package edu.ilstu.cast.it.vo;

import java.util.ArrayList;

/**
 * 
 * Professor This class represents a professor in the School of IT.  
 * This includes the professors name, their times available to teach, a list 
 * of coursePreference objects, and a status that shows if a given professor has
 * any preferences.
 *
 * @author Group 4, Michael Whalen, Paul Schulz, Deana Qiao
 *
 *
 */
public class ProfessorVO
{
	private ArrayList<String> professorList =  new ArrayList<String>();

	public ArrayList<String> getProfessorList() {
		return professorList;
	}

	public void setProfessorList(ArrayList<String> professorList) {
		this.professorList = professorList;
	}
	
	public int findProfessor(String professor)
	{
		int index = 0;
		index = professorList.indexOf(professor);

		return index;
	}
	}
