/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.ilstu.cast.it.conflict;

import edu.ilstu.cast.it.vo.CoursePreferenceVO;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import edu.ilstu.cast.it.vo.PreferencesVO;
import edu.ilstu.cast.it.vo.ProfessorVO;
import edu.ilstu.cast.it.vo.SchedulerVO;

/**
 * Class that checks conflicts for a schedule arraylist.
 * @author jabates
 */
public class ConflictChecking {

    private ArrayList<SchedulerVO> schedList;
    private static String viewList[] = { "Course-Section", "Instructor-Assignment", "Time-Table", "Classroom" };
    
    /**
     * Constructor with no parameters. Sets the array to a new list.
     */
    public ConflictChecking(){
        schedList=new ArrayList<>();
    }
            
    /**
     * Constructor that takes in a Schedule Arraylist.
     * @param schList 
     */
    public ConflictChecking(ArrayList<SchedulerVO> schList, String view) {
        schedList=schList;
        setIndex(view);
    }    

    /**
     * 
     * @param courses
     * @return 
     */
    public int checkExpected(int courses) {
        int warning;

        if (courses >= 5) {
            warning = 3;
        } else if (courses == 4) {
            warning = 1;
        } else if (courses == 3) {
            warning = 0;
        } else if (courses == 2) {
            warning = 1;
        } else if (courses == 1) {
            warning = 1;
        } else if (courses == 0) {
            warning = 3;
        } else {
            warning = 0;
        }

        return warning;
    }
    
    /**
     * 
     * @param coor1
     * @param coor2
     * @return 
     */
    public boolean compareCoordinates(String coor1, String coor2){
        boolean result = false;
        if(coor1.equalsIgnoreCase(coor2)){
            result = true;
        }
        return result;        
    }
    
    /**
     * Method to get all the sections assigned to the same classroom and the same time.
     * Returns an array of all the TimeConflict issues
     * @return 
     */
    public ArrayList<LinkedList<Integer>> getClassTimeConflict()
    {
        ArrayList<LinkedList<Integer>> conflict=new ArrayList<LinkedList<Integer>>();
        LinkedList<Integer> lConflict=new LinkedList<>();
        Collections.sort(getSchedList(), SchedulerVO.CTCompare);
        SchedulerVO prev=null;
        for(int i=0; i<getSchedList().size()-1; i++)
        {
            SchedulerVO first=getSchedList().get(i);
            SchedulerVO second=getSchedList().get(i+1);
            if(first.compareDays(second.getDays() ) && first.getClassroom().equalsIgnoreCase(second.getClassroom()) && first.checkTimeConflict(second)==0 && !isGradClass(first, second))
            {
                //Checks to make sure it wasn't previously added
                if(prev==null || !(prev.getRow()==first.getRow())) 
                {
                    lConflict.add(first.getRow());
                }             
                lConflict.add(second.getRow());
                prev=second;
            }
            else if(lConflict.size()>0)
            {
                conflict.add(lConflict);
                lConflict=new LinkedList<>();
            }
        }
        if(lConflict.size()>0)
        {
            conflict.add(lConflict);
        }
        return conflict;
    }

    /**
     * Checks to see if there is an Instructor and time conflict.
     * Returns an array of the conflicts.
     * @return 
     */
    public ArrayList<LinkedList<Integer>> getInstrTimeConflict()
    {
     ArrayList<LinkedList<Integer>> conflict=new ArrayList<LinkedList<Integer>>();
        LinkedList<Integer> lConflict=new LinkedList<>();

        Collections.sort(getSchedList(), SchedulerVO.ITCompare);
        SchedulerVO prev=null;
        for(int i=0; i<getSchedList().size()-1; i++)
        {
            SchedulerVO first=getSchedList().get(i);
            SchedulerVO second=getSchedList().get(i+1);
            if( first.getInstructor().equalsIgnoreCase(second.getInstructor()) && first.compareDays(second.getDays() ) && first.checkTimeConflict(second)==0 && !isGradClass(first, second))
            {
                //Checks to make sure it wasn't previously added
                 if(prev==null || !(prev.getRow()==first.getRow())) 
                {
                    lConflict.add(first.getRow());
                }             
                lConflict.add(second.getRow());
                prev=second;
            }
            else if(lConflict.size()>0)
            {
                conflict.add(lConflict);
                lConflict=new LinkedList<>();
            }
        }
        if(lConflict.size()>0)
        {
            conflict.add(lConflict);
        }
        return conflict;
    }

    
     /**
     * Checks to see if an instructor has insufficient time to get from one class to another
     * if the instructor is not in the same classroom. 
     * Returns an array of the conflicts.
     * @return 
     */
    public ArrayList<LinkedList<Integer>> getInstrPassingTimeConflict(int minutes)
    {
    	ArrayList<LinkedList<Integer>> conflict=new ArrayList<LinkedList<Integer>>();
        LinkedList<Integer> lConflict=new LinkedList<>();

        InsufficientTime insuf=new InsufficientTime(minutes);
        Collections.sort(getSchedList(), insuf);
        SchedulerVO prev=null;
        for(int i=0; i<getSchedList().size()-1; i++)
        {
            SchedulerVO first=getSchedList().get(i);
            SchedulerVO second=getSchedList().get(i+1);
            if( first.getInstructor().equalsIgnoreCase(second.getInstructor()) && !first.getClassroom().equalsIgnoreCase(second.getClassroom())&&
                    first.compareDays(second.getDays())  && first.checkTimeConflict(second, minutes)==0
                    )
            {
                //Checks to make sure it wasn't previously added
                 if(prev==null || !(prev.getRow()==first.getRow())) 
                {
                    lConflict.add(first.getRow());
                }             
                lConflict.add(second.getRow());
                prev=second;
            }
            else if(lConflict.size()>0)
            {
                conflict.add(lConflict);
                lConflict=new LinkedList<>();
            }
        }
        if(lConflict.size()>0)
        {
            conflict.add(lConflict);
        }
        return conflict;
    }

    /**
     * Checks for the Professor's time preference.
     * @param pList
     * @return 
     */
    public ArrayList<LinkedList<Integer>> profTimePrefConflict(SchedulerVO sched)
    {
            ArrayList<LinkedList<Integer>> conflict=new ArrayList<LinkedList<Integer>>();
            ArrayList<String> pList= sched.getProfessorList();
         //   ArrayList<SchedulerVO> schedl=sched.getSchedulerVOList();
            Collections.sort(pList);
            if(pList.size()>0)
            {
                LinkedList<Integer> lConflict=new LinkedList<>();
                Collections.sort(getSchedList(), SchedulerVO.COMPARE_BY_INSTRUCTOR);
                int profIndex=0;
                String curr=pList.get(profIndex);
                HashMap<String, PreferencesVO> prefMap= sched.getPreference();
                
                for(int i=0; i<getSchedList().size()-1; i++)
                {
                    SchedulerVO first=getSchedList().get(i);
                    PreferencesVO pref;
                    if(prefMap.containsKey(first.getInstructor()))
                    {
                        pref=prefMap.get(first.getInstructor());
                        while( first.getInstructor().compareTo(curr) > 0  && profIndex<pList.size()-1)
                        {
                            profIndex++;
                            curr=pList.get(profIndex);
            
                        }
                    if( !first.getInstructor().equalsIgnoreCase("") && first.getInstructor().equalsIgnoreCase(curr) && !checkProfWeekPref(pref, first))//!(first.compareDays(curr.getWeeks()) || curr.getWeeks().equals(""))  )
                    {
                        lConflict.add(first.getRow());
                    }
                    if(((i==getSchedList().size()) || (i < getSchedList().size()-2 && !first.getInstructor().equalsIgnoreCase(getSchedList().get(i+1).getInstructor())) 
                        )    && !lConflict.isEmpty())
                        {
                            conflict.add(lConflict);
                            lConflict=new LinkedList<>();
                        }
                    }
                }
                if(lConflict.size()>0)
                {
                    conflict.add(lConflict);
                }
            }
        return conflict;
    }
    
    
      public ArrayList<LinkedList<Integer>> profCoursefConflict(SchedulerVO sched)
    {
            ArrayList<LinkedList<Integer>> conflict=new ArrayList<LinkedList<Integer>>();
            ArrayList<String> pList=sched.getProfessorList();
            if(pList.size()>0)
            {
                Collections.sort(pList);
                HashMap<String, PreferencesVO> prefMap= sched.getPreference();
                LinkedList<Integer> lConflict=new LinkedList<>();
                Collections.sort(getSchedList(), SchedulerVO.COMPARE_BY_INSTRUCTOR);
                int profIndex=0;
                String curr=pList.get(profIndex);
                for(int i=0; i<getSchedList().size(); i++)
                {
                    SchedulerVO first=getSchedList().get(i);
                    CoursePreferenceVO pref;
                    if(prefMap.containsKey(first.getInstructor().trim()))
                    {
                        pref=prefMap.get(first.getInstructor()).getCourses();
                        while( first.getInstructor().compareTo(curr) > 0  && profIndex<pList.size()-1)
                        {
                        //   System.out.println("CLass: "+first.getInstr()+"Professor: "+curr.getName());
                            profIndex++;
                            curr=pList.get(profIndex);

                        }
                        
                        int prefValue=pref.getChoice(first.getCourse());
                        if( !first.getInstructor().equalsIgnoreCase("") && first.getInstructor().equalsIgnoreCase(curr) && (prefValue==4 || prefValue==5))
                        {
                            lConflict.add(first.getRow());
                        }
                    }
                        if(((i==getSchedList().size()) || (i < getSchedList().size()-2 && !first.getInstructor().equalsIgnoreCase(getSchedList().get(i+1).getInstructor())) 
                            )    && !lConflict.isEmpty())
                        {
                            conflict.add(lConflict);
                            lConflict=new LinkedList<>();
                        }
                    
                }
                if(lConflict.size()>0)
                {
                    conflict.add(lConflict);
                }
            }
        return conflict;
    }
            

      public ArrayList<LinkedList<Integer>> sectionWrongTimeConflict(){
        ArrayList<LinkedList<Integer>> conflict=new ArrayList<>();
        for(int i=0; i<getSchedList().size()-1; i++)
        {
            SchedulerVO issue=getSchedList().get(i);
            LinkedList<Integer> lConflict=new LinkedList<>();
            if(!issue.isProperTime())
            {
                lConflict.add(issue.getRow());
                conflict.add(lConflict);
            }
        }
        return conflict;
          
      } 
      
    /**
     * Looks at an and sees if there are two courses have the same time being offered.
     * 
     * @param course1 first course being offered
     * @param course2 second course being offered
     * @return 
     */
    public ArrayList<LinkedList<Integer>> getOverlapConflict(String course1,String course2)
    {
        ArrayList<LinkedList<Integer>> conflict=new ArrayList<LinkedList<Integer>>();
        LinkedList<Integer> lConflict=new LinkedList<>();

        OverlapCourseSections overlap=new OverlapCourseSections(course1, course2);
        Collections.sort(getSchedList(), overlap);
        SchedulerVO prev=null;
        for(int i=0; i<getSchedList().size()-1; i++)
        {
            SchedulerVO first=getSchedList().get(i);
            SchedulerVO second=getSchedList().get(i+1);
            if( first.checkSections(second, course1, course2) && first.compareDays(second.getDays() ) && first.checkTimeConflict(second)==0)
            {
               //Checks to make sure it wasn't previously added
                 if(prev==null || !(prev.getRow()==first.getRow())) 
                {
                    lConflict.add(first.getRow());
                }             
                lConflict.add(second.getRow());
                prev=second;
            }
            else if(lConflict.size()>0)
            {
                conflict.add(lConflict);
                lConflict=new LinkedList<>();
            }
        }
        if(lConflict.size()>0)
        {
            conflict.add(lConflict);
        }
        return conflict;
    }
    
    /**
     * Takes in the minimum number and maximum number for an Instructor and returns an array
     * of all conflicts
     * @param min  minimum number
     * @param max maximum number
     * @return 
     */
    public ArrayList<LinkedList<Integer>>  checkInstLoad(int min, int max)
    {
        ArrayList<LinkedList<Integer>> conflict=new ArrayList<>();
        LinkedList<Integer> lConflict=new LinkedList<>();
        Collections.sort(getSchedList(), SchedulerVO.COMPARE_BY_INSTRUCTOR);
        int load=0;
        int fIndex=0;
        SchedulerVO prev=null;

        for(int i=0; i<getSchedList().size(); i++)
        {
            SchedulerVO curr=getSchedList().get(i);
            if((prev==null || prev.getInstructor().equalsIgnoreCase(curr.getInstructor())) && i<getSchedList().size()-1 )
            {
                load++;
            }else if(i==getSchedList().size()-1)
            {
                load++;
                if(load<min || load>max)
                {
                    for(int j=load-1; j>=0; j--)
                    {
                        lConflict.add(getSchedList().get(i-j).getRow());
                    }
                    conflict.add(lConflict);
                }
            }else
            {
                
                if(load<min || load>max)
                {
                    for(int j=load; j>0; j--)
                    {
                        lConflict.add(getSchedList().get(i-j).getRow());
                    }
                    conflict.add(lConflict);
                    lConflict=new LinkedList<>();
                }
                load=1;
            }
            prev=curr;
        }
        return conflict;
    }
    
    /**
     * Gets the Schedule
     * @return the schedList
     */
    public ArrayList<SchedulerVO> getSchedList() {
        return schedList;
    }

    /**
     * Sets the schedule.
     * 
     * @param schedList 
     */
    public void setSchedList(ArrayList<SchedulerVO> schedList) {
        this.schedList = schedList;
    }

    private boolean isGradClass(SchedulerVO first, SchedulerVO second) {
        String firstCourse=first.getCourse().substring(0, 6);
        String secondCourse=second.getCourse().substring(0, 6);
        String fSection=removeGrad(first.getSection());
        String sSection=removeGrad(second.getSection());
        
        if(firstCourse.equalsIgnoreCase(secondCourse)&& fSection.equalsIgnoreCase(sSection))
        {
            return true;
        }else
        {
            return false;
        }
    }
    
    private String removeGrad(String str)
    {
        if(str.contains("Grad"))
        {
            int index=str.indexOf("Grad");
            return str.substring(0, index).trim();
        }
        else
        {
            return str;
        }
    }

    private boolean checkProfWeekPref(PreferencesVO curr, SchedulerVO first) {
        for(int i=0; i< first.getDays().length(); i++)
        {
            String week=first.getDays().substring(i, i+1);

            if(!curr.getWeek().contains(week))
            {
                return false;
            }
        }
        return true;
    }

    private String getCourseNum(String course) {
        int index=course.indexOf(".");
        if(index!=-1)
        {
            return course.substring(0, index);
        }else
        {
            return course;
        }
    }

    private void setIndex(String view) {
        if(view.equalsIgnoreCase(viewList[1]))
        {
            
        }
        else if(view.equalsIgnoreCase(viewList[2]))
        {
            
        }else
        {
            Collections.sort(schedList, SchedulerVO.COMPARE_BY_SECTION);
            Collections.sort(schedList, SchedulerVO.COMPARE_BY_COURSE);
        }
        for(int i=0; i< schedList.size(); i++)
        {
            schedList.get(i).setRow(i);
        }
    }
    
}
