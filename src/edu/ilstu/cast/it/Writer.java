package edu.ilstu.cast.it;
import java.util.List;

import edu.ilstu.cast.it.vo.SchedulerVO;


/**
 * @author gsharma
 *
 */
public interface Writer {

	public String writeCsvFile(SchedulerVO schedulerVO, String fileName);
}
