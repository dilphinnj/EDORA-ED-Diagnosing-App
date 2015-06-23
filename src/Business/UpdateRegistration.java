package Business;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.JOptionPane;

import Data.DataConnection;

public class UpdateRegistration {

	DataConnection dc = new DataConnection();
		
	String fn,ln,email,pass,ut,title,profs;
	String qlfcs = "None";
	int age;
	int tel = 000;
	
	//Statement dc.stmt;

	
	
	public UpdateRegistration(String utitle,String ufn, String uln, String uemail, String upass, String uut, int uage, String uprofs, int tel)
	{
		this.fn = ufn;
		this.ln = uln;
		this.email = uemail;
		this.pass = upass;
		this.age = uage;
		this.title = utitle;
		this.profs = uprofs;
		this.tel = tel;
		this.qlfcs = qlfcs;
		this.ut = uut;
	}
	
	
	
	public void updateData()
	{
		Registration reg = new Registration(title,fn,ln,email,pass,ut,age, profs);
		//reg.setQualifics(qlfcs);
		reg.setTel(tel);
		
		dc.createConn();
		
		try {
			dc.stmt = dc.con.createStatement();
			
			String sqlCID = "SELECT C_ID FROM consultants";
		    ResultSet rsCId = dc.stmt.executeQuery(sqlCID);
		    rsCId.last();
		    int curCID = rsCId.getRow();
		    curCID = curCID + 1;
		//	ResultSet rs = dc.stmt.executeQuery(sql);
		//	System.out.println(reg.getUsrFullName());
		    String sql = "INSERT INTO users (User_Title,User_Full_Name,User_Email,User_Age,User_Pass,User_Type,User_Prof,User_Tel,Consul_Id) VALUES ('"+reg.getUsrTitle()+"','"+reg.getUsrFullName()+"','"+reg.getUsrEmail()+"','"+reg.getUsrAge()+"','"+reg.getUsrPass()+"','"+reg.getUsrType()+"','"+reg.getUsrProfs()+"','"+reg.getUsrTel()+"','"+curCID+"');";
		    dc.stmt.executeUpdate(sql);
		    
		   // dc.stmt = null;
		   // dc.stmt = dc.con.createStatement();
		    
		    if(reg.getUsrType().equals("Consultant"))
		    {
				
				String treatType;
				
				System.out.print("Con");
			    
			    if(reg.getUsrProfs().equals("Ayurveda Doctor"))
			    {
			    	treatType = "Ayurveda";
			    }
			    
			    else 
			    {
			    	treatType = "Western";
			    }
			    
			    
			    
			   /* String sql2 = "INSERT INTO users Consul_Id VALUES ('"+curCID+"');";
			    dc.stmt.executeUpdate(sql2);*/
			    
			    String sql3 = "INSERT INTO consultants (C_ID,C_Name,C_Treat,C_Age,C_Type,C_Tel) VALUES ('"+curCID+"','"+reg.getUsrTitle()+" "+reg.getUsrFullName()+"','"+treatType+"','"+reg.getUsrAge()+"','"+reg.getUsrProfs()+"','"+reg.getUsrTel()+"');";
			    dc.stmt.executeUpdate(sql3);
		    }
	   
	        dc.stmt.close();
		    dc.con.close();
		    
		    JOptionPane.showMessageDialog(null,"Registration Successful!");
		    
	   }catch(SQLException se){
	      //Handle errors 
	      se.printStackTrace();
	   }catch(Exception e){
	      
	      e.printStackTrace();
	   }finally{
	      
	      try{
	         if(dc.stmt!=null)
	            dc.stmt.close();
	      }catch(SQLException se2){
	      }
	      try{
	         if(dc.con!=null)
	            dc.con.close();
	      }catch(SQLException se){
	         se.printStackTrace();
	      }
	   }
	}
	
}