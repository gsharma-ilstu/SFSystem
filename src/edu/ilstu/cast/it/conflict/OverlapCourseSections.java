/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.ilstu.cast.it.conflict;

import java.util.Comparator;

import edu.ilstu.cast.it.vo.SchedulerVO;

/**
 *
 * @author josh
 */
public class OverlapCourseSections implements Comparator<SchedulerVO>{
    
    String course1;
    String course2;
    public OverlapCourseSections(String aString, String bString)
    {
        course1=aString;
        course2=bString;
    }

    @Override
    public int compare(SchedulerVO aSched, SchedulerVO bSched) {
         int classComp= aSched.getClassroom().compareToIgnoreCase(bSched.getClassroom());
        if(aSched.checkSections(bSched, course1, course2))
        {
            if( aSched.compareDays(bSched.getDays()))
            {
                return aSched.checkTimeConflict(bSched);
            }else
            {
                return aSched.getDays().compareToIgnoreCase(bSched.getDays());
            }
        }else
        {
            return aSched.getCourse().compareToIgnoreCase(bSched.getCourse());
        } //To change body of generated methods, choose Tools | Templates.
    }
    
}
