import java.util.Date;

public class Point {
	
	private Date date;
	private boolean overtime;
	private boolean hourly;
	
	public Point( boolean isHourly ) {
		this.date = new Date();
		this.overtime = false;
		this.hourly = isHourly;
	}
	
	
	public boolean isHourly() {
		return hourly;
	}


	public void setHourly(boolean hourly) {
		this.hourly = hourly;
	}

	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public boolean isOvertime() {
		return overtime;
	}
	public void setOvertime(boolean overtime) {
		this.overtime = overtime;
	}
	
}