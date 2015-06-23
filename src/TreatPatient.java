import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.SpringLayout;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.UnsupportedLookAndFeelException;

import java.awt.Font;
import javax.swing.JTable;
import javax.swing.UIManager;

import Data.DataConnection;

import java.awt.SystemColor;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.UIManager.LookAndFeelInfo;
import javax.swing.table.JTableHeader;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;


public class TreatPatient {

	private JFrame frmEdoraPatient;
	Object rowData[][];
	Object columnNames[];
	private JTextField txtSearchPatient;
	private JTextField txtEDTreat;
	private JTable medTable;
	
	String trDate;
	int pId;
    String ed_status;
    String  ed_type;
    String treat;
    static String docName;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					TreatPatient window = new TreatPatient(docName);
					window.frmEdoraPatient.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public TreatPatient(String cName) 
	{
		this.docName = cName;
		initialize(cName);
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize(String cName) {
		frmEdoraPatient = new JFrame();
		frmEdoraPatient.setTitle("EDORA - Patient Search");
		frmEdoraPatient.getContentPane().setBackground(Color.WHITE);
		frmEdoraPatient.setBounds(100, 100, 900, 650);
		frmEdoraPatient.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		frmEdoraPatient.getContentPane().setLayout(null);
		//frame.getContentPane().setLayout();
		
		JLabel lblMedicalHistory = new JLabel("Patient History");
		lblMedicalHistory.setForeground(new Color(0, 0, 0));
		lblMedicalHistory.setBounds(106, 10, 309, 16);
		lblMedicalHistory.setFont(new Font("Tw Cen MT Condensed", Font.BOLD, 16));
		lblMedicalHistory.setHorizontalAlignment(SwingConstants.CENTER);
		frmEdoraPatient.getContentPane().add(lblMedicalHistory);
		
		JLabel lblPatientName = new JLabel("Patient Name");
		lblPatientName.setFont(new Font("SansSerif", Font.BOLD, 12));
		lblPatientName.setForeground(new Color(0, 0, 0));
	    lblPatientName.setBounds(24, 250, 88, 14);
	    frmEdoraPatient.getContentPane().add(lblPatientName);
	        
	    txtSearchPatient = new JTextField();
	    txtSearchPatient.setBounds(136, 244, 245, 33);
	    frmEdoraPatient.getContentPane().add(txtSearchPatient);
	    txtSearchPatient.setColumns(10);
	    
	    loadTable(cName);
		    
	    final DataConnection dc = new DataConnection();
	    
	       
	    JButton btnSearch = new JButton("Search");
	    btnSearch.setBackground(new Color(0, 0, 128));
	    btnSearch.setForeground(Color.WHITE);
	    btnSearch.setBounds(405, 246, 89, 28);
	    frmEdoraPatient.getContentPane().add(btnSearch);
	    
	    
	    
	    JLabel lblTreatment = new JLabel("Treatment");
	    lblTreatment.setBounds(36, 419, 74, 16);
	    frmEdoraPatient.getContentPane().add(lblTreatment);
	    
	    JLabel lblEdStatus = new JLabel("ED Status");
	    lblEdStatus.setBounds(36, 342, 74, 16);
	    frmEdoraPatient.getContentPane().add(lblEdStatus);
	    
	    JLabel lblEdType = new JLabel("ED Type");
	    lblEdType.setBounds(36, 381, 74, 16);
	    frmEdoraPatient.getContentPane().add(lblEdType);
	    
	    final JComboBox cmbEDSt = new JComboBox();
	    cmbEDSt.setModel(new DefaultComboBoxModel(new String[] {"Yes", "No"}));
	    cmbEDSt.setBounds(213, 340, 112, 20);
	    frmEdoraPatient.getContentPane().add(cmbEDSt);
	    
	    final JComboBox cmbEDTyp = new JComboBox();
	    cmbEDTyp.setModel(new DefaultComboBoxModel(new String[] {"Aneroxia", "Bulimia", "N/A"}));
	    cmbEDTyp.setBounds(213, 379, 112, 20);
	    frmEdoraPatient.getContentPane().add(cmbEDTyp);
	    
	    txtEDTreat = new JTextField();
	    txtEDTreat.setBounds(207, 427, 287, 51);
	    frmEdoraPatient.getContentPane().add(txtEDTreat);
	    txtEDTreat.setColumns(10);
	   
	    
	    
	        
	    
	    btnSearch.addActionListener(new ActionListener() {
		
		public void actionPerformed(ActionEvent e) {
			
		int noOfRows = 0;
		
		
	////Test :=>	System.out.println(txtSearchPatient.getText());
			
		try {
				dc.createConn();
				dc.stmt = dc.con.createStatement();
				String sql = "SELECT Pat_Id FROM patients WHERE Pat_Name ='"+txtSearchPatient.getText()+"'";
				System.out.print(sql);
				ResultSet rs = dc.stmt.executeQuery(sql);
				int id1 = 0;
				
				
				
				while(rs.next())
				{
					id1 = rs.getInt("Pat_Id"); 
				}
				
				
				rs = null;
				dc.stmt = null;
				sql = null;
				
			
		////Test:=> System.out.println(id1);
				
				dc.stmt = dc.con.createStatement();
				sql = "SELECT * FROM treatment_history WHERE Pat_Id ='"+id1+"'";
				rs = dc.stmt.executeQuery(sql);
										
		   
				int row = 0;
				int col = 0;
				
	 		
	 		    rs.last();
	            noOfRows = rs.getRow();
	            rs.beforeFirst();
	         
	            try {
	    			UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
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
	            
	     	    Object[][] rowData = new Object [noOfRows][5];
	 		    Object columnNames[] = { "Tr_Date","P_ID","ED Status", "ED Type", "Treatment"};
	 		
	 		    JTable table = new JTable(rowData, columnNames);
	 		    JTableHeader header = table.getTableHeader();
	 		    header.setBackground(new Color(0, 0, 128));
	 		    header.setForeground(Color.WHITE);
	 		 //   table.setBackground(UIManager.getColor("FormattedTextField.selectionForeground"));
	 		    JScrollPane scrollPane = new JScrollPane(table);
	 		    scrollPane.setBounds(10, 37, 500, 200);
	 	        frmEdoraPatient.getContentPane().add(scrollPane);
	 	        
	 	       for (LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) 
				{
			        if ("Nimbus".equals(info.getName())) {
			            UIManager.setLookAndFeel(info.getClassName());
			           // break;
			        }
			    }      
	 	      
	 	    
	    //result set
	    while(rs.next())
	      {
	         trDate = rs.getString("Tr_date");
	    	 pId  = rs.getInt("Pat_Id");
	         ed_status = rs.getString("Pat_ED_Status");
	         ed_type = rs.getString("Pat_ED_Type");
	         treat = rs.getString("Pat_Treat1");
	         
	           	rowData[row][col] = trDate;
	         	rowData[row][col+1] = pId;
	        	rowData[row][col+2] = ed_status;
	        	rowData[row][col+3] = ed_type;
	        	rowData[row][col+4] = treat;
	 	

	         //Display values
	         System.out.print("ID: " + pId);
	         System.out.print("name: " + ed_status);
	         System.out.print("edstat: " + ed_type);
	       //  System.out.println("doc: " + treat); 
	         
	         row++;
	      }
	      
	      rs.close();
	      
		//  dc.stmt.close();
		//  dc.con.close();
		  
	   }catch(SQLException se){
	      //Handle errors 
	      se.printStackTrace();
	   }catch(Exception e2){
	      
	      e2.printStackTrace();
	   }finally{
	      
	      if(dc.stmt!=null);
		   if(dc.con!=null);
		  //  dc.con.close();
	   }
		}
		}) ;
	    
	    
		    
	    JButton btnUpdateTreatment = new JButton("Update Treatment");
	    btnUpdateTreatment.setForeground(Color.WHITE);
	    btnUpdateTreatment.setFont(new Font("SansSerif", Font.BOLD, 12));
	    btnUpdateTreatment.setBackground(Color.RED);
	    btnUpdateTreatment.setBounds(207, 510, 256, 30);
	    frmEdoraPatient.getContentPane().add(btnUpdateTreatment);
	    
	    JLabel lblMedicine = new JLabel("Medicine Guide");
	    lblMedicine.setFont(new Font("Tw Cen MT Condensed", Font.BOLD, 16));
	    lblMedicine.setHorizontalAlignment(SwingConstants.CENTER);
	    lblMedicine.setBounds(643, 11, 122, 14);
	    frmEdoraPatient.getContentPane().add(lblMedicine);
	    
	    btnUpdateTreatment.addActionListener(new ActionListener(
	    		) {
			
			public void actionPerformed(ActionEvent arg0) {
				
				try {
					dc.stmt = dc.con.createStatement();
					
					String sqlTr = "INSERT INTO treatment_history (Pat_Id,Pat_ED_Status,Pat_ED_Type,Pat_Treat1) VALUES ('"+pId+"','"+cmbEDSt.getSelectedItem().toString()+"','"+cmbEDTyp.getSelectedItem().toString()+"','"+txtEDTreat.getText()+"');";
					
				    dc.stmt.executeUpdate(sqlTr);
					
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				
				
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
			String sqlMedType = "SELECT C_Treat FROM consultants WHERE C_Name ='"+docName+"'";
			System.out.println("Doc Name =>"+docName);
			ResultSet rsMT = dc.stmt.executeQuery(sqlMedType);
			String medType = null;
			rsMT.first();
			medType = rsMT.getString("C_Treat");
			
			System.out.println("MT =>"+medType);
			

			String sql = "SELECT * FROM medicine WHERE Med_Type ='"+medType+"'";
			ResultSet rs = dc.stmt.executeQuery(sql);
			
					
			//int id1 = 0;
			
			rs.last();
	        int noOfRows = rs.getRow();
	        rs.beforeFirst();
	         
	     	Object[][] rowData = new Object [noOfRows][2];
	 		Object columnNames[] = { "Med Name","Med Treatment"};
	 		 
	 		medTable = new JTable(rowData,columnNames);
	 		medTable.setForeground(Color.BLACK);
	 		medTable.setBackground(SystemColor.inactiveCaption);
	 		medTable.setFont(new Font("Tahoma", Font.PLAIN, 12));
	 		medTable.setColumnSelectionAllowed(true);
	 		medTable.setCellSelectionEnabled(true);
	 		
	 		JTableHeader header = medTable.getTableHeader();
	 		header.setBackground(new Color(0, 0, 128));
	 		header.setForeground(Color.BLACK);
	 		
	 		final JScrollPane scrollPane = new JScrollPane(medTable);
 		    scrollPane.setBounds(534, 35, 328, 505);
 		    medTable.setBounds(100, 113, 397, 93);
 		    frmEdoraPatient.getContentPane().add(scrollPane); 
 		     		   
 		    //result set
 		    while(rs.next())
 		      { 
 		         String mName = rs.getString("Med_Name");
 		         String  mTreat = rs.getString("Med_Treatment");
 		       
	 		 
	 			 rowData[row][col] = mName;
	         	 rowData[row][col+1] = mTreat;
	        	 
	        	 row++;
 		      }
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		
		
		
	}

	
	
	
	
	
}
