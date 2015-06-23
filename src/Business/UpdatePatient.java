package Business;

import java.sql.SQLException;

import Data.DataConnection;

public class UpdatePatient 
{

	
	
	public void addNewPatient(String pTit, String pN, int pTel, String pAdd, int pAge, String consName) throws SQLException
	{
		DataConnection dc = new DataConnection();
		dc.createConn();
		
		Patient pat = new Patient(pTit, pN, pAge, pTel, pAdd);
		
		dc.stmt = dc.con.createStatement();
		String sql = "INSERT INTO patients (Pat_Name,Pat_Age,Pat_Tel,Pat_Address,Pat_Doctor) VALUES ('"+pat.getPatTitle()+" "+pat.getPatName()+"','"+pat.getPatAge()+"','"+pat.getPatTel()+"','"+pat.getPatAddress()+"','"+consName+"')";
		dc.stmt.executeUpdate(sql);
		dc.con.close();
		
	}
	
	
	
}
