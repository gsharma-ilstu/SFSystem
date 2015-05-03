package edu.ilstu.cast.it.impl;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

import edu.ilstu.cast.it.Reader;
import edu.ilstu.cast.it.vo.CoursePreferenceVO;
import edu.ilstu.cast.it.vo.CoursesVO;
import edu.ilstu.cast.it.vo.PreferencesVO;
import edu.ilstu.cast.it.vo.ProfessorVO;
import edu.ilstu.cast.it.vo.SchedulerVO;

/**
 * @author gsharma
 *
 */
public class CSVReader implements Reader {

	private static final String extension =".csv";
	// CSV file header
	private final static String FILE_DIR="C:\\SFSystem\\Setup files";
	private static final String[] FILE_HEADERS_SCHEDULE = { "Course", "Section", "Instructor", "Days", "Time", "Classroom" };
	private static final String[] FILE_HEADERS_COURSES = { "-----------" };
	private static final String[] FILE_HEADERS_PROFESSORS = { "Professor Names" };
	private static final String[] FILE_HEADERS_FACULTY_PREF ={ "Professor Names",	"Days",	"Evening",	"IT 115",	"IT 140",
		"IT 150","IT 164","IT 165","IT 168","IT 178","IT 179","IT 191","IT 214","IT 225","IT 226","IT 254",	"IT 261",
		"IT 262","IT 275",	"IT 272",	"IT 279",	"IT 326",	"IT 327",	"IT 328",	"IT 330",	"IT 332",
		"IT 340",	"IT 341",	"IT 350",	"IT 351",	"IT 353",	"IT 354",	"IT 356",	"IT 357",	"IT 363",	"IT 367",	"IT 372",
		"IT 373",	"IT 375",	"IT 376",	"IT 377",	"IT 378",	"IT 379",	"IT 380",	"IT 381",	"IT 382",	"IT 383",	"IT 384",
		"IT 388","IT 390",	"IT 391",	"IT 392",	"IT 432",	"IT 450",	"IT 463",	"IT 467",	"IT 468",	"IT 477",	"IT 478",	"IT 495",
		"IT 496",	"IT 497"};
	
	
	
	private static final String COURSE = "Course";
	private static final String SECTION = "Section";
	private static final String INSTRUCTOR = "Instructor";
	private static final String DAYS = "Days";
	private static final String TIME = "Time";
	private static final String CLASSROOM = "Classroom";
	private static final String COURSE_HEADER = "-----------"; 
	private static final String PROFESSOR_NAMES ="Professor Names";
	private static final String EVENING = "Evening";
	private static final String IT_115 ="IT 115"; 
	private static final String IT_140 ="IT 140";
	private static final String IT_150 ="IT 150"; 
	private static final String IT_164 ="IT 164";
	private static final String IT_165 ="IT 165";
	private static final String IT_214 ="IT 214"; 
	private static final String IT_390 ="IT 390"; 
	private static final String IT_168 ="IT 168";
	private static final String IT_178 ="IT 178"; 
	private static final String IT_179 ="IT 179";
	private static final String IT_191 ="IT 191"; 
	private static final String IT_225 ="IT 225"; 
	private static final String IT_226 ="IT 226"; 
	private static final String IT_254 ="IT 254"; 
	private static final String IT_261 ="IT 261";
	private static final String IT_262 ="IT 262";
	private static final String IT_275 ="IT 275";
	
	private static final String IT_272 ="IT 272"; 
	private static final String IT_279 = "IT 279"; 
	private static final String IT_326 = "IT 326"; 
	private static final String IT_327 = "IT 327";
	private static final String IT_328 ="IT 328";
	private static final String IT_330 ="IT 330";
	private static final String IT_332 ="IT 332";
	private static final String IT_340 ="IT 340"; 
	private static final String IT_341 = "IT 341"; 
	private static final String IT_350 =  "IT 350";
	private static final String IT_351 = "IT 351";
	private static final String IT_353 = "IT 353";
	private static final String IT_354 = "IT 354";
	private static final String IT_356 =  "IT 356";
	private static final String IT_357 = "IT 357";
	private static final String IT_363 = "IT 363";
	private static final String IT_367 = "IT 367";
	private static final String IT_372 = "IT 372";
	private static final String IT_373 = "IT 373";
	
	private static final String IT_375 = "IT 375";
	private static final String IT_376 = "IT 376";
	private static final String IT_377 = "IT 377";
	private static final String IT_378 = "IT 378";
	private static final String IT_379 = "IT 379";
	private static final String IT_380 = "IT 380";
	private static final String IT_381 = "IT 381";
	private static final String IT_382 = "IT 382";
	private static final String IT_383 = "IT 383";
	private static final String IT_384 = "IT 384";
	private static final String IT_388 = "IT 388";
	private static final String IT_391 = "IT 391";
	private static final String IT_432 = "IT 432";
	private static final String IT_450 = "IT 450";
	private static final String IT_463 = "IT 463";
	private static final String IT_467 = "IT 467";
	
	private static final String IT_468 = "IT 468";
	private static final String IT_477 = "IT 477";
	private static final String IT_478 = "IT 478";
	private static final String IT_495 = "IT 495";
	private static final String IT_496 = "IT 496";
	private static final String IT_497 = "IT 497";
	
	
	
	
	
			
	
	
	private ArrayList<String> courseList =  new ArrayList<String>();
	private ArrayList<String> professorList = new ArrayList<String>();
	private ArrayList<SchedulerVO> svoList = new ArrayList<SchedulerVO>();
	//private ArrayList<PreferencesVO> cPrefList = new ArrayList<PreferencesVO>();
	private ArrayList<String> sectionList = new ArrayList<String>();
	private HashMap<String,List<String>> prefMap = new HashMap<String, List<String>>();
	private ArrayList<String> cPrefList ;
	
	SchedulerVO schedulerVO = new SchedulerVO();
	CoursesVO coursesVO =new CoursesVO();
	ProfessorVO professorVO = new ProfessorVO();
	PreferencesVO preferencesVO = new PreferencesVO();
	


	/**
	 * This method reads a .csv file and saves it in a List
	 * It uses a third party jar names commons-csv-1.1.jar which can be downloaded from http://mvnrepository.com/artifact/org.apache.commons/commons-csv/1.1
	 * @return List<SchedulerVO>
	 * @param String file name that should be read
	 * @throws IOException 
	 */
	public SchedulerVO readCsvFile(String fileName) {
	
		FileReader fileReader = null;
		CSVParser csvFileParser = null;
		SchedulerVO schedulerVO = null;
		CSVFormat csvFileFormat = null;
		

		// Create the CSVFormat object with the header mapping
		
		
		
		try {
			
			if(((fileName.contains("chedule")) && (fileName.contains(extension))) || ((fileName.contains("pring")) && (fileName.contains(extension))) || ((fileName.contains("fall")) && (fileName.contains(extension))))
			{	csvFileFormat = CSVFormat.DEFAULT.withHeader(FILE_HEADERS_SCHEDULE);
		
			}
			else if(fileName.contains("ourse") && fileName.contains(extension))
			{
				 csvFileFormat = CSVFormat.DEFAULT.withHeader(FILE_HEADERS_COURSES);
				
			}
			else if(fileName.contains("ssors") && fileName.contains(extension))
			{
				 csvFileFormat = CSVFormat.DEFAULT.withHeader(FILE_HEADERS_PROFESSORS);
				
			}
			else if(fileName.contains("rences") && fileName.contains(extension))
			{
				 csvFileFormat = CSVFormat.DEFAULT.withHeader(FILE_HEADERS_FACULTY_PREF);
				
			}
			else
			{
				 csvFileFormat = null;
				
			}
			// Create a new list of schedules
			fileReader = new FileReader(fileName);
			csvFileParser = new CSVParser(fileReader, csvFileFormat);
			List csvRecords = csvFileParser.getRecords();
			
			// Read the CSV file records starting from the second record to skip
			// the header
			
			for (int i = 1; i < csvRecords.size(); i++) {
				CSVRecord record = (CSVRecord) csvRecords.get(i);
				if(((fileName.contains("chedule")) && (fileName.contains(extension))) || ((fileName.contains("pring")) && (fileName.contains(extension))) || ((fileName.contains("fall")) && (fileName.contains(extension))))
				{
				
				schedulerVO = new SchedulerVO();
				schedulerVO.setCourse(record.get(COURSE));
				schedulerVO.setSection(record.get(SECTION));
				schedulerVO.setInstructor(record.get(INSTRUCTOR));
				schedulerVO.setDays(record.get(DAYS));
				schedulerVO.setTime(record.get(TIME));
				schedulerVO.setClassroom(record.get(CLASSROOM));
				
				sectionList.add(record.get(SECTION));
				svoList.add(schedulerVO);
				}
				else if(fileName.contains("ourse") && fileName.contains(extension)){
					
					courseList.add(record.get(COURSE_HEADER));
				}
				else if(fileName.contains("ssors") && fileName.contains(extension)){
				
					professorList.add(record.get(PROFESSOR_NAMES));
					
				}
				else if(fileName.contains("rences") && fileName.contains(extension)){
					 cPrefList = new ArrayList<String>();
					cPrefList.add(0,record.get(DAYS));
					cPrefList.add(1,record.get(EVENING));
					cPrefList.add(2,record.get(IT_115));
					cPrefList.add(3,record.get(IT_140));
					cPrefList.add(4,record.get(IT_150));
					cPrefList.add(5,record.get(IT_164));
					cPrefList.add(6,record.get(IT_165));
					cPrefList.add(7,record.get(IT_168));
					cPrefList.add(8,record.get(IT_178));
					cPrefList.add(9,record.get(IT_179));
					cPrefList.add(10,record.get(IT_191));
					cPrefList.add(11,record.get(IT_214));
					cPrefList.add(12,record.get(IT_225));
					cPrefList.add(13,record.get(IT_226));
					cPrefList.add(14,record.get(IT_254));
					cPrefList.add(15,record.get(IT_261));
					cPrefList.add(16,record.get(IT_262));
					cPrefList.add(17,record.get(IT_272));
					cPrefList.add(18,record.get(IT_275));
					cPrefList.add(19,record.get(IT_279));
					cPrefList.add(20,record.get(IT_326));
					cPrefList.add(21,record.get(IT_327));
					cPrefList.add(22,record.get(IT_328));
					cPrefList.add(23,record.get(IT_330));
					cPrefList.add(24,record.get(IT_332));
					cPrefList.add(25,record.get(IT_340));
					cPrefList.add(26,record.get(IT_341));
					cPrefList.add(27,record.get(IT_350));
					cPrefList.add(28,record.get(IT_351));
					cPrefList.add(29,record.get(IT_353));
					cPrefList.add(30,record.get(IT_354));
					cPrefList.add(31,record.get(IT_356));
					cPrefList.add(32,record.get(IT_357));
					cPrefList.add(33,record.get(IT_375));
					cPrefList.add(34,record.get(IT_363));
					cPrefList.add(35,record.get(IT_367));
					cPrefList.add(36,record.get(IT_372));
					cPrefList.add(37,record.get(IT_373));
					cPrefList.add(38,record.get(IT_375));
					cPrefList.add(39,record.get(IT_376));
					cPrefList.add(40,record.get(IT_377));
					cPrefList.add(41,record.get(IT_378));
					cPrefList.add(42,record.get(IT_379));
					cPrefList.add(43,record.get(IT_380));
					cPrefList.add(44,record.get(IT_381));
					cPrefList.add(45,record.get(IT_382));
					cPrefList.add(46,record.get(IT_383));
					cPrefList.add(47,record.get(IT_384));
					cPrefList.add(48,record.get(IT_388));
					cPrefList.add(49,record.get(IT_390));
					cPrefList.add(50,record.get(IT_391));
					cPrefList.add(51,record.get(IT_432));
					cPrefList.add(52,record.get(IT_450));
					cPrefList.add(53,record.get(IT_463));
					cPrefList.add(54,record.get(IT_467));
					cPrefList.add(55,record.get(IT_468));
					cPrefList.add(56,record.get(IT_477));
					cPrefList.add(57,record.get(IT_478));
					cPrefList.add(58,record.get(IT_495));
					cPrefList.add(59,record.get(IT_496));
					cPrefList.add(60,record.get(IT_497));
					
					prefMap.put(record.get(PROFESSOR_NAMES), cPrefList);
					//cPrefList.add(preferencesVO);
				}
				else{
					schedulerVO = null;
				}
			
			}
			if(null == schedulerVO)
				schedulerVO = new SchedulerVO();
			if(null != svoList ||  null != courseList || null != professorList )
			{
				
				
				schedulerVO.setSchedulerVOList(svoList);
				schedulerVO.setSectionList(returnUniqueValues(sectionList));
				schedulerVO.setCourseList(returnUniqueValues(courseList));
				schedulerVO.setProfessorList(returnUniqueValues(professorList));
				schedulerVO.setPrefMap(prefMap);
				coursesVO.setCourseList(returnUniqueValues(courseList));
				//professorVO.setProfessorList(returnUniqueValues(professorList));
			}
		
			
			return schedulerVO;
		} catch (Exception e) {
			System.out.println("Error in CsvFileReader !!!");
			e.printStackTrace();
			return null;
		} finally {
			try {
				fileReader.close();
				csvFileParser.close();
			} catch (IOException e) {
				System.out.println("Error while closing fileReader/csvFileParser !!!");
				e.printStackTrace();
			}
		}
	}
	
	
	public ArrayList<String> returnUniqueValues(ArrayList<String> inList){
		
		ArrayList<String> al = inList;
		// add elements to al, including duplicates
		Set<String> hs = new HashSet<>();
		hs.addAll(al);
		al.clear();
		al.addAll(hs);
		return al;
	}
	public static void main(String[] args) {
		CSVReader r = new CSVReader();
		// r.initializeSetUp();
		//r.readCsvFile("O:\\work\\project\\resources\\CommonPreferences.csv");
		CSVWriter w = new CSVWriter();
		//w.writeCsvFile(r.readCsvFile("O:\\work\\project\\resources\\CommonPreferences.csv"), "O:\\work\\files\\1Preferences.csv");
		//r.setUp();
	
	}
	
	public SchedulerVO setUp() {
		
		SchedulerVO svo = new SchedulerVO();
		ClassLoader classLoader = getClass().getClassLoader();
	 	//readCsvFile(classLoader.getResource("Schedule.csv").getFile());
		/*svo=readCsvFile(classLoader.getResource("Courses.csv").getFile());
		svo=readCsvFile(classLoader.getResource("Professors.csv").getFile());
		svo=readCsvFile(classLoader.getResource("CommonPreferences.csv").getFile());*/
		svo = readCsvFile(FILE_DIR+"\\"+"Courses.csv");
		svo = readCsvFile(FILE_DIR+"\\"+"Professors.csv");
		svo = readCsvFile(FILE_DIR+"\\"+"CommonPreferences.csv");
	 	return svo;
	}
	
	}
