package edu.ilstu.cast.it.vo;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;


/**
 * @author gsharma
 *
 */
public class SchedulerVO {

	private String course;
	private String section;
	private String instructor;
	private String days;
	private String time;
	private String classroom;
	private boolean eveningClass=false;
    private static int StartEveningTime=3;
    private static int  EndEveningTime=8;
    private int sH, eH, sM, eM;
    private int index;
	private ArrayList<SchedulerVO> schedulerVOList;
	private ArrayList<String> sectionList;
	private ArrayList<String> courseList;
	private ArrayList<String> professorList;
	private HashMap<String,List<String>> prefMap;
        private HashMap<String, PreferencesVO> teacherPrefMap;
	//constructor
	/*public SchedulerVO(String course,String section,String instructor,String days, String time,String classroom){
		super();
		this.course=course;
		this.section=section;
		this.instructor=instructor;
		this.days=days;
		this.time=time;
		this.classroom=classroom;
	}
*/
	
	public String getCourse() {
		return course;
	}

	public void setCourse(String course) {
		this.course = course;
	}

	public String getSection() {
		return section;
	}

	public void setSection(String section) {
		this.section = section;
	}

	public String getInstructor() {
		return instructor;
	}

	public void setInstructor(String instructor) {
		this.instructor = instructor;
	}

	public String getDays() {
		return days;
	}

	public void setDays(String days) {
		this.days = days;
	}

/*	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}*/

	public String getClassroom() {
		return classroom;
	}

	public void setClassroom(String classroom) {
		this.classroom = classroom;
	}


	
		
	public ArrayList<SchedulerVO> getSchedulerVOList() {
		return schedulerVOList;
	}


	public void setSchedulerVOList(ArrayList<SchedulerVO> schedulerVOList) {
		this.schedulerVOList = schedulerVOList;
	}




	public ArrayList<String> getSectionList() {
		return sectionList;
	}


	public void setSectionList(ArrayList<String> sectionList) {
		this.sectionList = sectionList;
	}




	public ArrayList<String> getCourseList() {
		return courseList;
	}


	public void setCourseList(ArrayList<String> courseList) {
		this.courseList = courseList;
	}




	public ArrayList<String> getProfessorList() {
		return professorList;
	}


	public void setProfessorList(ArrayList<String> professorList) {
		this.professorList = professorList;
	}

        public HashMap<String, List<String>> getPrefMap() {
            return prefMap;
	}

	public void setPrefMap(HashMap<String, List<String>> prefMap) {
                setUpPreferences(prefMap);
		this.prefMap = prefMap;
	}
        
        public HashMap<String, PreferencesVO> getPreference()
        {
            return teacherPrefMap;
        }

        private void setUpPreferences(HashMap<String, List<String>> prefMap)
        {
            teacherPrefMap=new HashMap<>();
            Iterator it=prefMap.entrySet().iterator();
            while(it.hasNext())
            {
                Map.Entry pair=(Map.Entry) it.next();
                String prof=(String) pair.getKey();
                System.out.println(prof);
               List<String> pref=(List<String>) pair.getValue();
               CoursePreferenceVO courses=new CoursePreferenceVO();
               if(pref.size()>1)
               {
                   for(int i=2; i< pref.size(); i++)
                   {
                       if(!pref.get(i).trim().equalsIgnoreCase(""))
                       {
                           System.out.println("Please work:"+pref.get(i));
                           System.out.println(getCourse(i));
                           courses.addChoice(getCourse(i), Integer.parseInt(pref.get(i).trim()));
                       }
                   }
                   PreferencesVO prefVO=new PreferencesVO(prof, courses);
                   prefVO.setWeek(pref.get(0));
                   prefVO.setEvening(pref.get(1));
                   teacherPrefMap.put(prof, prefVO);
               }
            }
        }
        
        private String getCourse(int i)
        {
            String str="";
            switch(i)
            {
                case 2: str="IT 115";break;
                case 3:str="IT 140";break;
                case 4:str="IT 150";break;
                case 5:str="IT 164";break;
                case 6:str="IT 165";break;
                case 7:str="IT 168";break;
                case 8:str="IT 178";break;
                case 9:str="IT 179";break;
                case 10:str="IT 191";break;
                case 11:str="IT 214";break;
                case 12:str="IT 225";break;
                case 13:str="IT 226";break;
                case 14:str="IT 254";break;
                case 15:str="IT 261";break;
                case 16:str="IT 262";break;
                case 17:str="IT 272";break;
                case 18:str="IT 275";break;
                case 19:str="IT 279";break;
                case 20:str="IT 326";break;
                case 21:str="IT 327";break;
                case 22:str="IT 328";break;
                case 23:str="IT 330";break;
                case 24:str="IT 332";break;
                case 25:str="IT 340";break;
                case 26:str="IT 341";break;
                case 27:str="IT 350";break;
                case 28:str="IT 351";break;
                case 29:str="IT 353";break;
                case 30:str="IT 354";break;
                case 31:str="IT 356";break;
                case 32:str="IT 357";break;
                case 33:str="IT 375";break;
                case 34:str="IT 363";break;
                case 35:str="IT 367";break;
                case 36:str="IT 372";break;
                case 37:str="IT 373";break;
                case 38:str="IT 375";break;
                case 39:str="IT 376";break;
                case 40:str="IT 377";break;
                case 41:str="IT 378";break;
                case 42:str="IT 379";break;
                case 43:str="IT 380";break;
                case 44:str="IT 381";break;
                case 45:str="IT 382";break;
                case 46:str="IT 383";break;
                case 47:str="IT 384";break;
                case 48:str="IT 388";break;
                case 49:str="IT 390";break;
                case 50:str="IT 391";break;
                case 51:str="IT 432";break;
                case 52:str="IT 450";break;
                case 53:str="IT 463";break;
                case 54:str="IT 467";break;
                case 55:str="IT 468";break;
                case 56:str="IT 477";break;
                case 57:str="IT 478";break;
                case 58:str="IT 495";break;
                case 59:str="IT 496";break;
                case 60:str="IT 497";break;
                default:
            }
            return str;
		
        }

	public String getTime() {
        return formatZero(sH)+":"+formatZero(sM)+" - "+formatZero(eH)+":"+formatZero(eM);
    }

	  private String formatZero(int num)
	    {
	        if(num==0)
	        {
	            return "00";
	        }else
	        {
	            String aStr=num+"";
	            return aStr;
	        }
	    }

	    /**
	     * @param time the time to set
	     */
	    public void setTime(String time) {
	    	this.time = time;
	        int fIndex=time.indexOf(":");
	        if(!(time.equals("") ||  fIndex == -1 ))
	        {
	           
	           String h1, h2; 
	           //int m, m2;
	           h1=time.substring(0, fIndex);
	           
	           fIndex=time.indexOf("-")+1;
	           int lIndex=time.indexOf(":", fIndex);
	           h2=time.substring(fIndex, lIndex);
	           eM=Integer.parseInt(time.substring(lIndex+1).trim());
	         
	          fIndex=time.indexOf(":");
	           lIndex=time.indexOf("-");
	           sM=Integer.parseInt(time.substring(fIndex+1, lIndex).trim());
	           
	          // System.out.println(time.substring(bFIndex+1, bLIndex).trim() +);
	        
	           sH=Integer.parseInt(h1.trim());
	           eH=Integer.parseInt(h2.trim());
	        }else
	        {
	            sH=0;
	            eH=0;
	            sM=0;
	            eM=0;
	        }
	           
	    }

	    /**
	     * @return the eveningClass
	     */
	    public boolean isEveningClass() {
	        return eveningClass;
	    }

	    /**
	     * @param eveningClass the eveningClass to set
	     */
	    public void setEveningClass(boolean eveningClass) {
	        this.eveningClass = eveningClass;
	    }
	    
	    public boolean isProperTime()
	    {
	        if((sH>=StartEveningTime && sH<=EndEveningTime && isEveningClass())||
	                ((sH<StartEveningTime || sH>EndEveningTime) && !isEveningClass()) )
	        {
	        //    System.out.println("Hour: "+sH+" class "+course + " section:"+section+ "is?:"+isEveningClass());
	            return true;
	        }
	        else
	        {
	            return false;
	        }
	    }

	    public boolean checkSections(SchedulerVO schedulerVO, String course1, String course2) {
	        if((course.equalsIgnoreCase(course1)||course.equalsIgnoreCase(course2)) &&
	                (schedulerVO.course.equalsIgnoreCase(course1)||schedulerVO.course.equalsIgnoreCase(course2))) 
	        {
	            return true;
	        }else
	        {
	            return false;
	        }
	    }

	   public void setRow(int numRows) {
	        index=numRows; //To change body of generated methods, choose Tools | Templates.
	    }
	    
	    /**
	     * Gets the row number.
	     * @return 
	     */
	    public int getRow() {
	        return index; //To change body of generated methods, choose Tools | Templates.
	    }
	    

	//Sort options
	public static Comparator<SchedulerVO> COMPARE_BY_COURSE = new Comparator<SchedulerVO>() {
        public int compare(SchedulerVO one, SchedulerVO other) {
            return one.course.compareTo(other.course);
        }
    };
    public static Comparator<SchedulerVO> COMPARE_BY_SECTION = new Comparator<SchedulerVO>() {
        public int compare(SchedulerVO one, SchedulerVO other) {
            return one.section.compareTo(other.section);
        }
    };
    public static Comparator<SchedulerVO> COMPARE_BY_INSTRUCTOR = new Comparator<SchedulerVO>() {
        public int compare(SchedulerVO one, SchedulerVO other) {
            return one.instructor.compareTo(other.instructor);
        }
    };
    public static Comparator<SchedulerVO> COMPARE_BY_DAYS = new Comparator<SchedulerVO>() {
        public int compare(SchedulerVO one, SchedulerVO other) {
            return one.days.compareTo(other.days);
        }
    };
    public static Comparator<SchedulerVO> COMPARE_BY_TIME = new Comparator<SchedulerVO>() {
        public int compare(SchedulerVO one, SchedulerVO other) {
            return one.time.compareTo(other.time);
        }
    };
    public static Comparator<SchedulerVO> COMPARE_BY_CLASSROOM = new Comparator<SchedulerVO>() {
        public int compare(SchedulerVO one, SchedulerVO other) {
            return one.classroom.compareTo(other.classroom);
        }
    };
    
    //Private helper class to check for each day
    private boolean checkDay(String c, String bSched)
    {
        if(days.contains(c) && bSched.contains(c)){
            return true;
        }else
        {
            return false;
        }
          
    }
    
    public boolean compareDays(String bSched)
    {
        if(checkDay("M", bSched)||checkDay("T", bSched)||checkDay("W", bSched)||checkDay("R", bSched)||checkDay("F", bSched))
          {
             //   System.out.println("b"+);
                 return true;
           }
        else{
     //       System.out.println("Dragon Roars");
            return false;//room.compareTo(bSched.getRoom());
        }
        
    }
    
      @Override
    public String toString()
    {
       return course+"~"+section+"~"+instructor+"~"+days+"~"+getTime()+"~"+classroom;
    }
    
    public int checkTimeConflict(SchedulerVO bSched, int bpTime)
    {    
      //  int fIndex=time.indexOf(":");
       // int bFIndex=bSched.time.indexOf(":");
        
        if(!(sH==0 && bSched.sH==0))
        {
            int sh1=sH;
            int sh2=bSched.sH;
            int eh1=eH;
            int eh2=bSched.eH;
            int sm1=sM;
            int sm2=bSched.sM;
            int em1=eM+bpTime;
            int em2=bSched.eM+bpTime;
       //     System.out.println(em1+"2"+em2);
            if(bpTime>0){
            //    System.out.println("bp entered");
                while(em1>=60)
                {
                    eh1=eh1+1;
                    em1=em1-60;
              //      System.out.println("entered: "+em1+" "+eh1+" shour"+sh2);
                }
                while(em2>=60)
                {
                    eh2=eh2+1;
                    em2=em2-60;
                }
                
            }
           if(sh1==sh2||eh1==eh2)
           {
               return 0;
           }
           else if(sh1==eh2 && sm1<=em2)
           {
               
                   return 0;
               
           }else if(eh1==sh2 && em1>=sm2)
           {
                   return 0;
           }else{
             //  System.out.print("fourth");
                return getTime().compareTo(bSched.getTime());
           }
        }else
        {
           // System.out.print("end");
            return getTime().compareTo(bSched.getTime());
        }
    }
    
    public int checkTimeConflict(SchedulerVO bSched)
    {
        return checkTimeConflict(bSched, 0);
    }
    
    /**
     * Static Comparator to compare Instructor and time issues.
     */
    public static Comparator<SchedulerVO> ITCompare= new Comparator<SchedulerVO>(){
        @Override
    public int compare(SchedulerVO aSched, SchedulerVO bSched){
        int intrComp= aSched.getInstructor().compareToIgnoreCase(bSched.getInstructor());
        if(intrComp==0)
        {
            if( aSched.compareDays(bSched.getDays()))
            {
                return aSched.checkTimeConflict(bSched);
            }else
            {
                return aSched.getDays().compareToIgnoreCase(bSched.days);
            }
        }else
        {
            return intrComp;
        }
    }
    };
    
    public ArrayList<LinkedList<SchedulerVO>> getClassroomList(String days){
        ArrayList<LinkedList<SchedulerVO>> classList= new ArrayList<>();
        ArrayList<SchedulerVO> courselist=getSchedulerVOList();
        if(courselist.size()>0)
        {
            LinkedList<SchedulerVO> timeList=new LinkedList<>();
       //     Collections.sort(courselist, SchedulerVO.CLASSROOM_VIEW_COMPARE);
            SchedulerVO prev=null;
            boolean previousAdded=false;
            for(int i=0; i<courselist.size(); i++)
            {
                SchedulerVO first=courselist.get(i);
                if(first.compareDays(days))
                {
                    //Checks to make sure it wasn't previously added
                    
                    previousAdded=true;
                    if(prev==null || !previousAdded) 
                    {
                        timeList.add(first);
                    }else 
                    {
                        if(prev.getTime().equalsIgnoreCase(first.getTime()))
                        {
                          timeList.add(first);  
                        }else
                        {
                              classList.add(timeList);
                              timeList=new LinkedList<>();
                              timeList.add(first);
                        }
                    }
                }
                else{
                    previousAdded=false;
                    if(timeList.size()>0)
                    {
                        classList.add(timeList);
                        timeList=new LinkedList<>();
                    }
                }
                prev=first;
            }
            if(timeList.size()>0)
            {
                classList.add(timeList);
            }
        }
        return classList;
    }
    
    public static Comparator<SchedulerVO> CLASSROOM_VIEW_COMPARE = new Comparator<SchedulerVO>()
    {
        @Override
        public int compare(SchedulerVO aSched, SchedulerVO bSched){
            if(aSched.compareDays(bSched.getDays()))
            {
                int timeComp=aSched.getTime().compareToIgnoreCase(bSched.getTime());
               if(timeComp==0)
               {
                
                    int classComp=aSched.getClassroom().compareToIgnoreCase(bSched.getClassroom());
                    return classComp;
               }else
               {
                   return timeComp;
               }
            }
            else
            {
                return aSched.getDays().compareToIgnoreCase(bSched.days);
            }
        }
    
    };
    /**
     * Static Comparator to check for Classroom and time issues.
     */
    public static Comparator<SchedulerVO> CTCompare= new Comparator<SchedulerVO>(){
        @Override
    public int compare(SchedulerVO aSched, SchedulerVO bSched){
        int classComp= aSched.getClassroom().compareToIgnoreCase(bSched.getClassroom());
        if(classComp==0)
        {
            if( aSched.compareDays(bSched.getDays()))
            {
                return aSched.checkTimeConflict(bSched);
            }else
            {
                return aSched.getDays().compareToIgnoreCase(bSched.days);
            }
        }else
        {
            return classComp;
        }
    }
    };
    
    
}
