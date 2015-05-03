package edu.ilstu.cast.it.test;
import static org.junit.Assert.*;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import edu.ilstu.cast.it.impl.CSVReader;
import edu.ilstu.cast.it.vo.SchedulerVO;


/**
 * @author gsharma
 *
 */
public class CSVReaderTest {
	
	CSVReader csvReader;
	String fileName;
	SchedulerVO svo;
	FileReader fileReader = null;
	
	@Before
	public void setup(){
		csvReader = new CSVReader();
		fileName= "H:\\work\\project\\resources\\Test.csv";
	
	}
 
	@Test
	public void testReadCSV() throws FileNotFoundException {
	
		fileReader=new FileReader(fileName);
		//check whether file is available or not
		assertNotNull(fileReader);
		svo=csvReader.readCsvFile(fileName);
		//check extension of file
		assertTrue(fileName.endsWith(".csv"));
		//check returned list is not null
		assertNotNull(svo);
		//check all the rows have been read
		assertTrue(svo.getSchedulerVOList().size() > 0);
		//check contents 
		assertTrue(svo.getSchedulerVOList().get(0).getClassroom().equals("Test classroom"));
	
	}
	
	@After
	public void cleanUp(){
		csvReader=null;
	}
	
	
	
	
	
	
}
