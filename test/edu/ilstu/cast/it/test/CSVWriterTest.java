package edu.ilstu.cast.it.test;

import static org.junit.Assert.*;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import edu.ilstu.cast.it.impl.CSVWriter;
import edu.ilstu.cast.it.vo.SchedulerVO;


/**
 * @author gsharma
 *
 */
public class CSVWriterTest
{
	CSVWriter csvWriter;
	ArrayList<SchedulerVO> dummyList;
	SchedulerVO svo;
	String fileName;
	
	@Before
	public void setup() throws IOException
	{
		dummyList = new ArrayList<SchedulerVO>();
		svo = new SchedulerVO();
		svo.setCourse("Test Course");
		svo.setSection( "Test section");
		svo.setInstructor( "Test instructor");
		svo.setDays( "Test days");
		svo.setTime("Test time");
		svo.setClassroom( "Test classroom");
		ArrayList<SchedulerVO> sVOList = new ArrayList<SchedulerVO>();
		sVOList.add(svo);
		svo.setSchedulerVOList(sVOList);
		//dummyList.add(svo);
		fileName= "H:\\work\\project\\resources\\Test.csv";
	}
	
	@Test
	public void testWriteCSV() throws FileNotFoundException
	{
		String result="";
		csvWriter = new CSVWriter();
		result = csvWriter.writeCsvFile(svo, fileName);
		assertTrue(result.equalsIgnoreCase("Successfull"));
	}
	
	@After
	public void cleanup()
	{
		csvWriter = null;
	}

}
