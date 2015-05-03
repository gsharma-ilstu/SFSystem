package edu.ilstu.cast.it;
import java.util.List;

import edu.ilstu.cast.it.vo.SchedulerVO;


/**
 * @author gsharma
 *
 */
public interface Reader {

	 public  SchedulerVO readCsvFile(String fileName); 
}
