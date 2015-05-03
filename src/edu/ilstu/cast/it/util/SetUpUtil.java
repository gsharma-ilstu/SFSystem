package edu.ilstu.cast.it.util;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;

import edu.ilstu.cast.it.ui.CreateUI;

public class SetUpUtil {

	private final static String FILE_DIR="C:\\SFSystem\\Setup files";
	ClassLoader classLoader = this.getClass().getClassLoader();
	
	File files = new File(FILE_DIR);
	File CourseFile = new File(FILE_DIR+"\\"+"Courses.csv");
	File ProfessorFile = new File(FILE_DIR+"\\"+"Professors.csv");
	File PreferenceFile = new File(FILE_DIR+"\\"+"CommonPreferences.csv");
	File ScheduleFile = new File(FILE_DIR+"\\"+"Schedule.csv");
	public String initializeSetUp(){
		
		if (!files.exists()) {
		    boolean result = false;

		    try{
		    	files.mkdirs();
		        result = true;
		        copyFiles();
		    } 
		    catch(SecurityException se){
		        //handle it
		    }        
		    if(result) {    
		        return "Set up files copied at \""+FILE_DIR+"\" successfully";  
		    }
		}else if (files.exists()){
			
			if(!CourseFile.exists())
			{
				try {
					copy(this.getClass().getClassLoader().getResourceAsStream("Courses.csv"), FILE_DIR+"\\"+"Courses.csv");
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			if(!ProfessorFile.exists())
			{
				try {
					copy(this.getClass().getClassLoader().getResourceAsStream("Professors.csv"), FILE_DIR+"\\"+"Professors.csv");
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			if(!PreferenceFile.exists())
			{
				try {
					copy(this.getClass().getClassLoader().getResourceAsStream("CommonPreferences.csv"), FILE_DIR+"\\"+"CommonPreferences.csv");
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			if(!ScheduleFile.exists())
			{
				try {
					copy(this.getClass().getClassLoader().getResourceAsStream("Schedule.csv"), FILE_DIR+"\\"+"Schedule.csv");
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			return "Set up files loaded";
		}
		return "";
	}

	public void copy(InputStream source,String dest)   throws IOException {
		//File sourceF = new File(source);
		File destF = new File(dest);
	    //Files.copy(sourceF.toPath(), destF.toPath(),StandardCopyOption.REPLACE_EXISTING);
		Files.copy(source, destF.toPath(),StandardCopyOption.REPLACE_EXISTING);
	  
	}
	
	public void copyFiles(){
		SetUpUtil setUpUtil = new SetUpUtil();
		try {
			
			setUpUtil.copy(this.getClass().getClassLoader().getResourceAsStream("Courses.csv"), FILE_DIR+"\\"+"Courses.csv");
			setUpUtil.copy(this.getClass().getClassLoader().getResourceAsStream("Professors.csv"), FILE_DIR+"\\"+"Professors.csv");
			setUpUtil.copy(this.getClass().getClassLoader().getResourceAsStream("CommonPreferences.csv"), FILE_DIR+"\\"+"CommonPreferences.csv");
			setUpUtil.copy(this.getClass().getClassLoader().getResourceAsStream("Schedule.csv"), FILE_DIR+"\\"+"Schedule.csv");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}
