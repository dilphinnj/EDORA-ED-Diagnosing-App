package Business;

import java.sql.Date;
import java.sql.Time;

public class Appointment {
	
	private String conName;
	private String time;
	private Date date;
	
	public Appointment(String cons,Date dt, String ti)
	{
		this.conName = cons;
		this.time = ti;
		this.date = dt;
	}

	public String getConsultantName()
	{
		return conName;
	}
	
	public String getAppointmentTime()
	{
		return time;
	}
	
	public Date getAppointmentDate()
	{
		return date;
	}
}
