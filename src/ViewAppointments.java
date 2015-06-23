import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.Frame;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.UIManager.LookAndFeelInfo;
import javax.swing.border.EmptyBorder;
import javax.swing.table.JTableHeader;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;
import javax.swing.Box;
import java.awt.Component;
import java.awt.Dimension;
import javax.swing.SpringLayout;
import java.awt.Font;
import javax.swing.JButton;
import javax.swing.JTextField;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.sql.Date;
import java.sql.ResultSet;
import java.util.Calendar;

import javax.swing.JTable;

import Data.DataConnection;
import java.awt.Color;
import javax.swing.UIManager;
import java.awt.SystemColor;

import jess.*;


public class ViewAppointments extends JFrame {

	private static JPanel contentPane;
	private static String name = null;
	private static JTable appTable;
	
	
	
	public ViewAppointments(final String cName) 
	{
		this.name = cName;
		
		setTitle("EDORA - Appointments");
		setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		setBounds(100, 100, 762, 398);
		contentPane = new JPanel();
		contentPane.setBackground(Color.WHITE);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblPleaseSelectAn = new JLabel("Available Appointment Details");
		lblPleaseSelectAn.setFont(new Font("Tw Cen MT Condensed", Font.BOLD, 16));
		lblPleaseSelectAn.setHorizontalAlignment(SwingConstants.CENTER);
		lblPleaseSelectAn.setBounds(218, 26, 263, 28);
		contentPane.add(lblPleaseSelectAn);
		
		try {
				for (LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) 
				{
			        if ("Nimbus".equals(info.getName())) 
			        {
			            UIManager.setLookAndFeel(info.getClassName());
			           // break;
			        }
			    }   
				
		}catch(Exception e)
		{
			e.printStackTrace();
		}
		
				
		loadTable(cName);
		
		
	}
	
	
	public static void main(String[] args) {
		
					
		EventQueue.invokeLater(new Runnable() {
		//	private int noOfRows;
			
			public void run() {
				try {
					
					ViewAppointments frame = new ViewAppointments(name);
					frame.setVisible(true);
			//		PatientMainWin.runTest();
				
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		
		
	}

	private String[] args;
	
	
	
	public void loadTable(String docName)
	{
		DataConnection dc = new DataConnection();
		int row = 0;
		int col = 0;
		
		Date dtToday = new Date(Calendar.getInstance().getTime().getTime());
		
		try {
			
			dc.createConn();
			dc.stmt = dc.con.createStatement();	
			
			String sql = "SELECT * FROM appointments WHERE Ap_Date ='"+dtToday.toString()+"' AND Ap_Consultant ='"+docName+"';";
			ResultSet rs = dc.stmt.executeQuery(sql);
			
			rs.last();
	        int noOfRows = rs.getRow();
	        rs.beforeFirst();
	        
	        Object[][] rowData = new Object [noOfRows][3];
	 		Object columnNames[] = { "Appointment Date ","Appointment Time","Patient Name"};
	 		
	 		//rs.wait();
	        
	        //result set
 		    while(rs.next())
 		      {
 		      //   int pId = rs.getInt("Pat_Id");
 		    	 String date  = rs.getString("Ap_Date");
 		    	 String time = rs.getString("Ap_Time"); 		       
	 		 
	 			// rowData[row][col] = pId;
	         	 rowData[row][col] = date;
	         	 rowData[row][col+1] = time;
	        	// rowData[row][col+2] = patName;
	        	
	        	 
	        	 row++;
 		      }
			
		//	int uid = rs.getInt("Usr_Id");
			int uid = 1;
		//	String sqlConName = "SELECT User_Title,User_Full_Name FROM users WHERE Usr_Id ='"+dtToday.toString()+"';
			
			String sqlPatName = "SELECT User_Title,User_Full_Name FROM users WHERE User_Id ='"+uid+"';";
			ResultSet rsUID = dc.stmt.executeQuery(sqlPatName);
			
			String patName = null;
			String patTi;
			
			while(rsUID.next())
			{
				patTi = rsUID.getString("User_Title");
				patName = rsUID.getString("User_Full_Name");
				patName = patTi + " "+ patName;
				
			}
			
			
			
			/*while(rs.next())
			{
				rowData[row][col+2] = patName;
				row++;
			}*/
			
			System.out.print(patName);
			
			UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
			
	    					 		 
	 		appTable = new JTable(rowData,columnNames);
	 		appTable.setForeground(Color.BLACK);
	 		appTable.setBackground(SystemColor.inactiveCaption);
	 		appTable.setFont(new Font("Tahoma", Font.PLAIN, 12));
	 		appTable.setColumnSelectionAllowed(true);
	 		appTable.setCellSelectionEnabled(true);
	 		
	 		JTableHeader header = appTable.getTableHeader();
	 		header.setBackground(new Color(0, 0, 128));
	 		header.setForeground(Color.WHITE);
	 		
	 		JScrollPane scrollPane = new JScrollPane(appTable);
 		    scrollPane.setBounds(27, 84, 658, 205);
 		    appTable.setBounds(29, 113, 397, 93);
 		    contentPane.add(scrollPane);
 		    
 		    
 		   
 		   
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		
		
		try {
			for (LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) 
			{
		        if ("Nimbus".equals(info.getName())) 
		        {
		            UIManager.setLookAndFeel(info.getClassName());
		           // break;
		        }
		    }   
			
	}catch(Exception e)
	{
		e.printStackTrace();
	}
		
		
	}


}
