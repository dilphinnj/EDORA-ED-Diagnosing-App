package Business;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;

import Data.DataConnection;

public class UpdateAppointment {




	
	public void addAppointment(String cons,	Date date , String time, String usrName)
	{
		DataConnection dc = new DataConnection();
		dc.createConn();
		int patId = 0;
		
		String sqlPId = "SELECT User_Id FROM users WHERE User_Full_Name = '"+usrName+"'";	
		try {
			dc.stmt = dc.con.createStatement();	
			ResultSet rsPId = dc.stmt.executeQuery(sqlPId);
			rsPId.next();
			/*while (rsPId.next()) 
			{*/
				patId = rsPId.getInt("Pat_Id");
				System.out.print("ID =>"+patId);
			/*}*/
			
			
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		
		
		
		
		Appointment ap = new Appointment(cons, date, time);
		
		try {
			dc.stmt = dc.con.createStatement();		
			String sql = "INSERT INTO appointments (Usr_Id,Ap_Date,Ap_Time,Ap_Consultant) VALUES ('"+patId+"','"+ap.getAppointmentDate()+"','"+ap.getAppointmentTime()+"','"+ap.getConsultantName()+"')";
			dc.stmt.executeUpdate(sql);
			dc.con.close();
		
		} catch (SQLException e) 
		{
			e.printStackTrace();
		}
		
		
	}
	
//	@SuppressWarnings("null")
	@SuppressWarnings("null")
	public String[] getConNamesList()
	{
		DataConnection dc = new DataConnection();
		dc.createConn();
		
		String[] consNameList = null;
			
		String sqlConNames = "SELECT C_Name FROM consultants";
		try {
			dc.stmt = dc.con.createStatement();
			ResultSet rs = dc.stmt.executeQuery(sqlConNames);
			
			int i =0;
			rs.last();
			int fVal = rs.getRow();
			consNameList = new String[fVal];
			rs.beforeFirst();
			
			while (rs.next()) 
			{
				consNameList[i] = rs.getString("C_Name");
				i++;
			}
			
			
			dc.con.close();
		//	return consNameList;
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return consNameList;
	}
	
	
}
