import java.util.Calendar;
import java.util.Date;

public class PaymentSchedule {

	private TypeSched type;
	private int dayOfWeek;
	private int dayOfMonth;
	
	private static final int MILLISECONDS_PER_DAY = 86400000;
	
	public boolean setPaymentSchedule(String pay) {
		
		pay = pay.toLowerCase();
		boolean returner = false;
		
		if(pay.charAt(0) == 'm' && pay.length() >= 8 ) {
			
			if(pay.charAt(6) == '$') {
				setLastDay(); 
				returner = true;
			
			} else {
				
				int day = convert( pay.substring(6));
				if( day != -1 ) {
					
					if( day > 28 ) {
						setLastDay();
						returner = true;
					} else { 
						setDayOfMonth( day );
						returner = true;
					}
					
				}
				
			}
			
		}
		
		else if(pay.charAt(0) == 's' && pay.length() >= 15) {
			
			int result = convertDayWeek( pay.substring( 9 ) );
			
			if(result != -1 ) {
				
				if(pay.charAt(7) == '1') {
					setWeekly(1, result);
					returner = true;
				}
				else if(pay.charAt(7) == '2') {
					setWeekly(2, result);
					returner = true;
				}
			}
		}
		
		return returner;
		
	}
	
	public boolean isDayToPay(Date today, Date lastPayment) {
		
		Calendar cal = Calendar.getInstance();
		cal.setTime(today);
		
		boolean returner = false;
		long daysDistance = ( today.getTime() - lastPayment.getTime() ) / MILLISECONDS_PER_DAY;
		
		if( type == TypeSched.MONTHLY && dayOfMonth == cal.get( Calendar.DAY_OF_MONTH ) && daysDistance > 1 )
			returner = true;
		else if( type == TypeSched.LAST_DAY_MONTH && cal.getActualMaximum( Calendar.DAY_OF_MONTH ) == cal.get( Calendar.DAY_OF_MONTH ) && daysDistance > 1)
			returner = true;
		else if( type == TypeSched.WEEKLY1 && dayOfWeek == cal.get( Calendar.DAY_OF_WEEK ) && daysDistance > 1 )
			returner = true;
		else if( type == TypeSched.WEEKLY2 && dayOfWeek == cal.get( Calendar.DAY_OF_WEEK ) && daysDistance > 7 )
			returner = true;
		
		return returner;
		
	}
	
	private void setWeekly(int times, int dayWeek) {
		if(times == 1)
			type = TypeSched.WEEKLY1;
		else
			type = TypeSched.WEEKLY2;
		dayOfMonth = -1;
		dayOfWeek = dayWeek;
	}
	
	private void setLastDay() {
		type = TypeSched.LAST_DAY_MONTH;
		dayOfMonth = -1;
		dayOfWeek = -1;
	}
	
	private void setDayOfMonth( int day ) {
		type = TypeSched.MONTHLY;
		dayOfMonth = day;
		dayOfWeek = -1;
	}
	
	private int convertDayWeek( String day ) {
		
		int dayW = -1;
		if( day.equals("segunda") ) 
			dayW = Calendar.MONDAY;
		else if( day.equals("terca") ) 
			dayW = Calendar.TUESDAY;
		else if( day.equals("quarta") ) 
			dayW = Calendar.WEDNESDAY;
		else if( day.equals("quinta") ) 
			dayW = Calendar.THURSDAY;
		else if( day.equals("sexta") ) 
			dayW = Calendar.FRIDAY;
		
		return dayW;
		
		
	}
	
	private int convert(String day) {
		
		for(int i=0; i<day.length(); i++) {
			if( day.charAt(i) < 48 || day.charAt(i) > 57 )
				return -1;
		}
		
		int dayInt = Integer.valueOf(day);
		if( dayInt < 1 || dayInt > 31 )
			return -1;
		
		return dayInt;
		
	}
	
	
}
