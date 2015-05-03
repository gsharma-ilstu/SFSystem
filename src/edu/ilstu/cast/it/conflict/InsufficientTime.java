/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.ilstu.cast.it.conflict;

import java.util.Comparator;

import edu.ilstu.cast.it.vo.SchedulerVO;

/**
 *Comparator for looking at teachers who need time to get from one class to another.
 * @author josh
 */
public class InsufficientTime implements Comparator<SchedulerVO>{
    
    int min;
    /**
     * Takes in the amount of time allowed to get to one class to another.
     * @param minutes 
     */
    public InsufficientTime(int minutes)
    {
        min=minutes;
    }

    @Override
    public int compare(SchedulerVO aSched, SchedulerVO bSched) {
       
        if(aSched.getInstructor().equalsIgnoreCase(bSched.getInstructor()))
        {
            int classComp= aSched.getClassroom().compareToIgnoreCase(bSched.getClassroom());
           if(classComp!=0)
           {
          //     System.out.println("Entering drag");
               if( aSched.compareDays(bSched.getDays()))
            {
                int check=aSched.checkTimeConflict(bSched, min);
            //    System.out.println("Check!:"+check);
                return check;
            }else
            {
                return aSched.getDays().compareToIgnoreCase(bSched.getDays());
            }
           }else{
               return classComp;
           }
            
        }else
        {
            return aSched.getInstructor().compareToIgnoreCase(bSched.getInstructor());
        }
    }
    
}
