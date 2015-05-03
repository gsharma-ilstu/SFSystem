package edu.ilstu.cast.it.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;

import edu.ilstu.cast.it.Writer;
import edu.ilstu.cast.it.vo.SchedulerVO;

/**
 * @author gsharma
 *
 */
public class CSVWriter implements Writer {

	private static final String extension =".csv";
	// CSV file header
	private static final String[] FILE_HEADERS = { "Course", "Section", "Instructor", "Days", "Time", "Classroom" };
	private static final String[] FILE_HEADERS_PROFESSORS = { "Professor Names" };
	private static final String[] FILE_HEADERS_COURSES = { "-----------" };
	private static final String[] FILE_HEADERS_FACULTY_PREF ={ "Professor Names",	"Days",	"Evening",	"IT 115",	"IT 140",
		"IT 150","IT 164","IT 165","IT 168","IT 178","IT 179","IT 191","IT 214","IT 225","IT 226","IT 254",	"IT 261",
		"IT 262","IT 275",	"IT 272",	"IT 279",	"IT 326",	"IT 327",	"IT 328",	"IT 330",	"IT 332",
		"IT 340",	"IT 341",	"IT 350",	"IT 351",	"IT 353",	"IT 354",	"IT 356",	"IT 357",	"IT 363",	"IT 367",	"IT 372",
		"IT 373",	"IT 375",	"IT 376",	"IT 377",	"IT 378",	"IT 379",	"IT 380",	"IT 381",	"IT 382",	"IT 383",	"IT 384",
		"IT 388","IT 390",	"IT 391",	"IT 392",	"IT 432",	"IT 450",	"IT 463",	"IT 467",	"IT 468",	"IT 477",	"IT 478",	"IT 495",
		"IT 496",	"IT 497"};
	/**
	 * This method writes data to a .csv file
	 * It uses a third party jar names commons-csv-1.1.jar which can be downloaded from http://mvnrepository.com/artifact/org.apache.commons/commons-csv/1.1
	 * @return String
	 * @param List <SchedulerVO> containing comma separated values to write and file name as a string
	 */
	public String writeCsvFile(SchedulerVO schedulerVO, String fileName) {

		FileWriter fileWriter = null;
		CSVPrinter csvFilePrinter = null;

		try {
			// Create the CSVFormat object with "\n" as a record delimiter
			CSVFormat csvFileFormat = CSVFormat.DEFAULT.withRecordSeparator("\n");
			fileWriter = new FileWriter(fileName);
			csvFilePrinter = new CSVPrinter(fileWriter, csvFileFormat);
			if((fileName.contains("chedule") && fileName.contains(extension)) || (fileName.contains("pring") && fileName.contains(extension)) || (fileName.contains("fall") && fileName.contains(extension)))
			csvFilePrinter.printRecord(FILE_HEADERS);
			if(fileName.contains("ssors") && fileName.contains(extension))
			csvFilePrinter.printRecord(FILE_HEADERS_PROFESSORS);
			if(fileName.contains("ourse") && fileName.contains(extension))
			csvFilePrinter.printRecord(FILE_HEADERS_COURSES);
			if(fileName.contains("rences") && fileName.contains(extension))
				csvFilePrinter.printRecord(FILE_HEADERS_FACULTY_PREF);
			
			for (int i = 0; i < schedulerVO.getSchedulerVOList().size(); i++) {
			
				if((fileName.contains("chedule") && fileName.contains(extension)) || (fileName.contains("pring") && fileName.contains(extension)) || (fileName.contains("fall") && fileName.contains(extension))){
				List scheduleDataRows = new ArrayList();
				scheduleDataRows.add(schedulerVO.getSchedulerVOList().get(i).getCourse());
				scheduleDataRows.add(schedulerVO.getSchedulerVOList().get(i).getSection());
				scheduleDataRows.add(schedulerVO.getSchedulerVOList().get(i).getInstructor());
				scheduleDataRows.add(schedulerVO.getSchedulerVOList().get(i).getDays());
				scheduleDataRows.add(schedulerVO.getSchedulerVOList().get(i).getTime());
				scheduleDataRows.add(schedulerVO.getSchedulerVOList().get(i).getClassroom());
				csvFilePrinter.printRecord(scheduleDataRows);
				}
				if(fileName.contains("ssors") && fileName.contains(extension)){
					List professorList = new ArrayList();
					professorList.add(schedulerVO.getProfessorList().get(i));
					csvFilePrinter.printRecord(professorList);
				}
				if(fileName.contains("ourse") && fileName.contains(extension)){
					List courseList = new ArrayList();
					courseList.add(schedulerVO.getCourseList().get(i));
					csvFilePrinter.printRecord(courseList);
				}
				
			}
				if(fileName.contains("rences") && fileName.contains(extension)){
				List courseList = new ArrayList();
				List data = new ArrayList();
				String name ="";
				 for ( String key : schedulerVO.getPrefMap().keySet() ) {
		    		 //  System.out.println( key );
		    		    name = key;
		    		    data.add(name);
		    		  	courseList=schedulerVO.getPrefMap().get(key);
		    		  	for(int i=0;i<courseList.size();i++){
		    		  		data.add(courseList.get(i).toString());
		    		  	}
		    		  	csvFilePrinter.printRecord(data);
		    		  	data = new ArrayList();
		    		}
				  	
		    		
				 //courseList.add(schedulerVO.getCourseList().get(i));
					
				}

			return "Successfull";
		} catch (Exception e) {
			return "Error in CsvFileReader !!! \n Please report this ERROR:\n " + e.getMessage();

		} finally {
			try {
				fileWriter.flush();
				fileWriter.close();
				csvFilePrinter.close();
			} catch (IOException e) {
				return "Error while closing fileReader/csvFileParser !!! \n Please report this ERROR:\n "
						+ e.getMessage();

			}
		}
	}
	
	
}
