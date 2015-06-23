import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.plaf.ColorChooserUI;
import javax.swing.table.JTableHeader;
import javax.swing.text.StyleConstants.ColorConstants;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;
import javax.swing.Box;
import javax.swing.UIManager.LookAndFeelInfo;
import javax.swing.UnsupportedLookAndFeelException;

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
import java.sql.ResultSet;

import javax.swing.JTable;

import Data.DataConnection;
import java.awt.Color;
import javax.swing.UIManager;

import org.eclipse.core.databinding.property.set.SetProperty;

import java.awt.SystemColor;

import jess.JessException;
import jess.Rete;


public class ConsultantMain extends JFrame {

	private static JPanel contentPane;
	private static String cName = null;
	private static JTable patTable;
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
				
		EventQueue.invokeLater(new Runnable() {
			
		//	private int noOfRows;

			public void run() {
				try {
					
					ConsultantMain frame = new ConsultantMain(cName);
					frame.setVisible(true);
					
					
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	
	
	public ConsultantMain(final String conName) 
	{
		
			
		setTitle("EDORA - Consultant Main ");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 900, 600);
		contentPane = new JPanel();
		contentPane.setBackground(Color.WHITE);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		loadTable(conName);	
		
		try {
				for (LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) 
				{
			        if ("Nimbus".equals(info.getName())) {
			            UIManager.setLookAndFeel(info.getClassName());
			           // break;
			        }
			    }            
			} catch (ClassNotFoundException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (InstantiationException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (IllegalAccessException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (UnsupportedLookAndFeelException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		
		JLabel lblNewLabel = new JLabel("Welcome" + " "+ conName);
		lblNewLabel.setBounds(249, 20, 386, 28);
		lblNewLabel.setFont(new Font("Tw Cen MT Condensed", Font.PLAIN, 24));
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		contentPane.add(lblNewLabel);
		
		JLabel lblPleaseSelectAn = new JLabel("Patient Details of the Consultant");
		lblPleaseSelectAn.setFont(new Font("Tw Cen MT Condensed", Font.BOLD, 18));
		lblPleaseSelectAn.setHorizontalAlignment(SwingConstants.CENTER);
		lblPleaseSelectAn.setBounds(293, 95, 304, 28);
		contentPane.add(lblPleaseSelectAn);
		
		JButton btnEnterNewPatient = new JButton("Enter New Patient");
		btnEnterNewPatient.setFont(new Font("SansSerif", Font.BOLD, 12));
		btnEnterNewPatient.setBackground(new Color(255, 0, 0));
		btnEnterNewPatient.setForeground(Color.WHITE);
		btnEnterNewPatient.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				AddPatientWindow apw = new AddPatientWindow(conName);
				apw.main(null);
			}
		});
		btnEnterNewPatient.setBounds(88, 483, 256, 30);
		contentPane.add(btnEnterNewPatient);
		
		JButton btnTreat = new JButton("Treat Patient");		
		btnTreat.setFont(new Font("SansSerif", Font.BOLD, 12));
		btnTreat.setBackground(new Color(255, 0, 0));
		btnTreat.setForeground(Color.WHITE);
		btnTreat.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) 
			{
				TreatPatient trtWindow = new TreatPatient(conName);
				trtWindow.main(null);
			}
		});
		btnTreat.setBounds(88, 428, 256, 30);
		
		contentPane.add(btnTreat);
				
				
		
		
		JLabel lblSelectAnOption = new JLabel("Select an option :-");
		lblSelectAnOption.setFont(new Font("Tw Cen MT Condensed", Font.BOLD, 15));
		lblSelectAnOption.setHorizontalAlignment(SwingConstants.LEFT);
		lblSelectAnOption.setBounds(88, 388, 282, 28);
		contentPane.add(lblSelectAnOption);
		    
		JButton btnRefresh = new JButton("Refresh");
		btnRefresh.setForeground(Color.WHITE);
		btnRefresh.setBackground(new Color(0, 0, 128));
		//btnRefresh.setBackground(new Color(0, 0, 128));
		//btnRefresh.setForeground(Color.WHITE);
		btnRefresh.setBounds(707, 377, 89, 28);
		contentPane.add(btnRefresh);
		
		JButton btnEDTest = new JButton("Use ED Test");
		btnEDTest.setFont(new Font("SansSerif", Font.BOLD, 12));
		btnEDTest.setForeground(Color.WHITE);
		btnEDTest.setBackground(Color.RED);
		btnEDTest.setBounds(394, 428, 256, 30);
		contentPane.add(btnEDTest);
		btnEDTest.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent arg0) {
				
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
		
		JButton btnChkAptmnt = new JButton("Today's Appointments");
		btnChkAptmnt.setFont(new Font("SansSerif", Font.BOLD, 12));
		btnChkAptmnt.setForeground(Color.WHITE);
		btnChkAptmnt.setBackground(Color.RED);
		btnChkAptmnt.setBounds(394, 483, 256, 30);
		contentPane.add(btnChkAptmnt);
		
		
		btnChkAptmnt.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				
				ViewAppointments va = new ViewAppointments(conName);
				va.main(null);
				
			}
		});
		
		
		
		
		btnRefresh.addActionListener(new ActionListener() {
				
		public void actionPerformed(ActionEvent arg0) {
					
					loadTable(conName);
					
			}
		});
	}
	
	public void loadTable(String docName)
	{
		DataConnection dc = new DataConnection();
		int row = 0;
		int col = 0;
		
		try {
			
			dc.createConn();
			dc.stmt = dc.con.createStatement();
			String sql = "SELECT * FROM patients WHERE Pat_Doctor ='"+docName+"'";
			ResultSet rs = dc.stmt.executeQuery(sql);
			//int id1 = 0;
			
			rs.last();
	        int noOfRows = rs.getRow();
	        rs.beforeFirst();
	         
	     	Object[][] rowData = new Object [noOfRows][6];
	 		Object columnNames[] = { "Patient ID","Patient Name","Patient Age","Patient Tel","Patient Address", "Register Date"};
	 		 
	 		patTable = new JTable(rowData,columnNames);
	 		patTable.setForeground(Color.BLACK);
	 		patTable.setBackground(SystemColor.inactiveCaption);
	 		patTable.setFont(new Font("Tahoma", Font.PLAIN, 12));
	 		patTable.setColumnSelectionAllowed(true);
	 		patTable.setCellSelectionEnabled(true);
	 		
	 		JTableHeader header = patTable.getTableHeader();
	 		header.setBackground(new Color(0, 0, 128));
	 		header.setForeground(Color.WHITE);
	 		
	 		final JScrollPane scrollPane = new JScrollPane(patTable);
 		    scrollPane.setBounds(88, 160, 708, 205);
 		    patTable.setBounds(29, 113, 397, 93);
 		    contentPane.add(scrollPane); 
 		     		   
 		    //result set
 		    while(rs.next())
 		      {
 		         int pId = rs.getInt("Pat_Id");
 		    	 String name  = rs.getString("Pat_Name");
 		    	 int pAge = rs.getInt("Pat_Age");
 		         int pTel = rs.getInt("Pat_Tel");
 		         String pAddr = rs.getString("Pat_Address");
 		         String  pReg = rs.getString("Pat_Reg_Date");
 		       
	 		 
	 			 rowData[row][col] = pId;
	         	 rowData[row][col+1] = name;
	         	 rowData[row][col+2] = pAge;
	        	 rowData[row][col+3] = pTel;
	        	 rowData[row][col+4] = pAddr;
	        	 rowData[row][col+5] = pReg;
	        	 
	        	 row++;
 		      }
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		
		
		
	}
}
