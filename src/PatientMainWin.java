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
import java.sql.ResultSet;

import javax.swing.JTable;

import Data.DataConnection;
import java.awt.Color;
import javax.swing.UIManager;
import java.awt.SystemColor;

import jess.*;


public class PatientMainWin extends JFrame {

	private static JPanel contentPane;
	private static String name = null;
	private static JTable patTable;
	
	/*test t = new test();
	static Rete rt = new Rete();
	static String com = "(batch \"EDalgorithm.clp\")";*/
	//static Rete rete = new Rete();
	//static String edFile = "EDalgorithm.clp";
	
	public PatientMainWin(final String patName) 
	{
		setTitle("EDORA - Patient Main");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 900, 600);
		contentPane = new JPanel();
		contentPane.setBackground(Color.WHITE);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Welcome" + " "+ patName);
		lblNewLabel.setBounds(249, 20, 386, 28);
		lblNewLabel.setFont(new Font("Tw Cen MT Condensed", Font.PLAIN, 24));
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		contentPane.add(lblNewLabel);
		
		JLabel lblPleaseSelectAn = new JLabel("Available Consultant Details");
		lblPleaseSelectAn.setFont(new Font("Tw Cen MT Condensed", Font.BOLD, 16));
		lblPleaseSelectAn.setHorizontalAlignment(SwingConstants.CENTER);
		lblPleaseSelectAn.setBounds(310, 223, 263, 28);
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
		
		JButton btnEnterNew = new JButton("Take an ED Test!");
		btnEnterNew.setFont(new Font("Tahoma", Font.BOLD, 11));
		btnEnterNew.setForeground(Color.WHITE);
		btnEnterNew.setBackground(Color.RED);
		btnEnterNew.setBounds(317, 138, 250, 30);
		contentPane.add(btnEnterNew);
		btnEnterNew.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				Runnable r = new Runnable(){

					public void run() {
						Rete ret = new Rete();
						try {
							//ret.watchAll();
							ret.batch("EDalgorithm.clp");				
							
						} catch (JessException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						
					}};
					
					new Thread(r).start();								
			}
		});
		
		JButton btnCreateAppointment = new JButton("Create Appointment");
		btnCreateAppointment.setFont(new Font("Tahoma", Font.BOLD, 11));
		btnCreateAppointment.setForeground(Color.WHITE);
		btnCreateAppointment.setBackground(Color.RED);
		btnCreateAppointment.setBounds(310, 478, 250, 30);
		contentPane.add(btnCreateAppointment);
		
		btnCreateAppointment.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent arg0) {
				
				CreateAppointment newAp = new CreateAppointment(patName);
				newAp.main(null);
				
			}
		});
		
				
		loadTable(patName);
		
		
	}
	
	
	public static void main(String[] args) {
		
					
		EventQueue.invokeLater(new Runnable() {
		//	private int noOfRows;
			
			public void run() {
				try {
					
					
					PatientMainWin frame = new PatientMainWin(name);
					frame.setVisible(true);
			//		PatientMainWin.runTest();
				
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		
		
	}

	private String[] args;
/*
	public void runTest()
	{
		//testFile t1 = new testFile();
		
		try {
			testFile.main(args);
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}*/
	
	
	
	
	public void loadTable(String docName)
	{
		DataConnection dc = new DataConnection();
		int row = 0;
		int col = 0;
		
		try {
			
			dc.createConn();
			dc.stmt = dc.con.createStatement();
			String sql = "SELECT `C_Name`, `C_Treat`, `C_Type`, `C_Reg_Date`,`C_Tel` FROM CONSULTANTS";
			ResultSet rs = dc.stmt.executeQuery(sql);
			
			UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
	    				
			rs.last();
	        int noOfRows = rs.getRow();
	        rs.beforeFirst();
	         
	     	Object[][] rowData = new Object [noOfRows][5];
	 		Object columnNames[] = { "Consultant Name","Consultant Treatment","Consultant Type","Consult Tel", "Register Date"};
	 		 
	 		patTable = new JTable(rowData,columnNames);
	 		patTable.setForeground(Color.BLACK);
	 		patTable.setBackground(SystemColor.inactiveCaption);
	 		patTable.setFont(new Font("Tahoma", Font.PLAIN, 12));
	 		patTable.setColumnSelectionAllowed(true);
	 		patTable.setCellSelectionEnabled(true);
	 		
	 		JTableHeader header = patTable.getTableHeader();
	 		header.setBackground(new Color(0, 0, 128));
	 		header.setForeground(Color.WHITE);
	 		
	 		JScrollPane scrollPane = new JScrollPane(patTable);
 		    scrollPane.setBounds(88, 262, 708, 205);
 		    patTable.setBounds(29, 113, 397, 93);
 		    contentPane.add(scrollPane); 
 		    
 		    JLabel lblCheckYourRisk = new JLabel("Check your risk of having an Eating Disorder by Taking the Test ==>");
 		    lblCheckYourRisk.setFont(new Font("Tw Cen MT Condensed", Font.BOLD, 16));
 		    lblCheckYourRisk.setHorizontalAlignment(SwingConstants.CENTER);
 		    lblCheckYourRisk.setBounds(216, 99, 451, 28);
 		    contentPane.add(lblCheckYourRisk);
 		    
 		    
 		   
 		    //result set
 		    while(rs.next())
 		      {
 		      //   int pId = rs.getInt("Pat_Id");
 		    	 String name  = rs.getString("C_Name");
 		    	 String cTreat = rs.getString("C_Treat");
 		         String cDoc = rs.getString("C_Type");
 		         String  cReg = rs.getString("C_Reg_Date");
 		         int cTel = rs.getInt("C_Tel");
 		       
	 		 
	 			// rowData[row][col] = pId;
	         	 rowData[row][col] = name;
	         	 rowData[row][col+1] = cTreat;
	        	 rowData[row][col+2] = cDoc;
	        	 rowData[row][col+3] = cTel;
	        	 rowData[row][col+4] = cReg;
	        	 
	        	 row++;
 		      }
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
