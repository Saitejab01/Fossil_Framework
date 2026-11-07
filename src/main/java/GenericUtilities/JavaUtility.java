package GenericUtilities;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

public class JavaUtility {
	
	/**
	 * This Class consists of Generic methods Related to Java
	 * @param n
	 * @return
	 */
	
	public int GenerateNumber(int n) {
		
		/**
		 * This Method Generates Random Num and returns to Caller
		 */
		Random r=new Random();
		int RandomNum=r.nextInt(n);
		return RandomNum;
	}
	
	public String GenrateDate() {
		
		/**
		 * This Method Generates Random Date in Specified Format and returns to Caller
		 */
		Date d=new Date();
		SimpleDateFormat dd=new SimpleDateFormat("dd-MM-yyyy_hh-mm-ss");
		String date=dd.format(d);
		return date;
	}

}
